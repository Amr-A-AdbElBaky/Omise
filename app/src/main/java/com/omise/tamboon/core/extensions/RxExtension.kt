package  com.omise.tamboon.core.extensions


import android.util.Log
import com.omise.tamboon.base.domain.executors.PostExecutionThread
import com.omise.tamboon.core.data.JobExecutor
import com.omise.tamboon.core.data.ThreadExecutor
import com.omise.tamboon.core.data.UIThread
import com.omise.tamboon.core.data.exception.CustomException
import com.omise.tamboon.core.presentation.ObservableResource
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

fun <T> Single<T>.applyAsyncSchedulers(executionScheduler: Scheduler = Schedulers.from(JobExecutor()), postExecutionScheduler: PostExecutionThread = UIThread()): Single<T> =
    this.compose {
        it.subscribeOn(executionScheduler).observeOn(postExecutionScheduler.scheduler)
    }

fun <T, R> Single<List<T>>.publishListToObservableResource(res: ObservableResource<List<R>>,
                                                           onSuccess: ((data: List<R>) -> Unit)? = null,
                                                           onError: ((data: Throwable) -> Unit)? = null,
                                                           mapper: (T) -> R = noMapper(),
                                                           executor: ThreadExecutor = JobExecutor(),
                                                           postExecutor: PostExecutionThread = UIThread()): Disposable {
    res.loading.value = true
    return this.flattenAsFlowable { it }.map(mapper).toList()
        .applyAsyncSchedulers(Schedulers.from(executor), postExecutor).subscribe({
            onSuccess(res, it)
            onSuccess?.invoke(it)
        }, {
            Log.d("Error", "onError() called wit $it")
            onError(it, res, onError)
        })
}

fun <T, R> Single<T>.publishToObservableResource(res: ObservableResource<R>,
                                                           onSuccess: ((data: R) -> Unit)? = null,
                                                           onError: ((data: Throwable) -> Unit)? = null,
                                                           mapper: (T) -> R = noMapper(),
                                                           executor: ThreadExecutor = JobExecutor(),
                                                           postExecutor: PostExecutionThread = UIThread()): Disposable {
    res.loading.value = true
    return this.map(mapper).applyAsyncSchedulers(Schedulers.from(executor), postExecutor).subscribe({
            onSuccess(res, it)
            onSuccess?.invoke(it)
        }, {
            Log.d("Error", "onError() called wit $it")
            onError(it, res, onError)
        })
}


fun Disposable?.addTo(composite: CompositeDisposable): Unit {
    if (this != null)
        composite.add(this)
}


private fun <R> onSuccess(res: ObservableResource<R>, it: R) {
    res.loading.value = false
    res.value = it
}


private fun <R> onError(err: Throwable, res: ObservableResource<R>, onError: ((data: Throwable) -> Unit)? = null ,doAfterError: ((data: Throwable) -> Unit)? = null) {
    res.loading.value = false

    if (onError != null) {
        onError.invoke(err)
    } else {
        when (err) {
            is CustomException -> res.error.value = err
            else -> res.error.value = CustomException(kind = CustomException.Kind.UNEXPECTED, message = "Mapping non NAException to UNEXPECTED")
        }
        doAfterError?.invoke(err)
    }
}

private fun <T, R> noMapper(): (T) -> R {
    return mapper@{
        try {
            with(it as R) {
                return@mapper this
            }
        } catch (e: Exception) {
            throw ClassCastException("Please provide mapper to publishToObservableResource method or make sure that ObservableResource of the same type as the Source e.g Signle,Observable,Maybe,Flowable")
        }
    }
}

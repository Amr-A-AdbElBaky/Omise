package com.omise.tamboon.core.presentation

import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

/**
 * A lifecycle-aware observable that sends only new updates after subscription, used for events like
 * navigation and Snackbar messages.
 *
 *
 * This avoids a common problem with events: on configuration change (like rotation) an update
 * can be emitted if the observer is active. This LiveData only calls the observable if there's an
 * explicit call to setValue() or call().
 *
 *
 * Note that only one observer is going to be notified of changes.
 */
open class ReplayLiveEvent<T> : MutableLiveData<T>() {

    private val mPending = AtomicBoolean(false)
    // handle content by default
    private var mHandleContent = true

    private var content: T? = null

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {

        if (hasActiveObservers()) {
            Log.w(TAG, "Multiple observers registered but only one will be notified of changes.")
        }

        // Observe the internal MutableLiveData
        super.observe(owner, Observer { t ->
            if (mPending.compareAndSet(true, mHandleContent)) {
                content = t
                observer.onChanged(t)
            }
        })
    }

    @MainThread
    override fun setValue(t: T?) {
        mPending.set(true)
        super.setValue(t)
    }

    /**
     * Used to re-push the content to the observer of this mutableLiveData.
     */
    @MainThread
    fun replay() {
        value = content
    }

    /**
     * Used to toggle the state of observation.
     * @param handleContent True to start observation, False otherwise.
     */
    @MainThread
    fun toggleObservationState(handleContent: Boolean) {
        mHandleContent = handleContent
        mPending.set(handleContent)
    }

    /**
     * Used for cases where T is Void, to make calls cleaner.
     */
    @MainThread
    fun call() {
        value = null
    }

    companion object {
        private val TAG = "ReplayLiveData"
    }
}

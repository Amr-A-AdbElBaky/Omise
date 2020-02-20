package com.omise.tamboon.features.charities.presentation.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import co.omise.android.models.Token
import co.omise.android.ui.CreditCardActivity
import co.omise.android.ui.OmiseActivity
import co.omise.android.ui.OmiseActivity.Companion.EXTRA_TOKEN_OBJECT
import com.omise.tamboon.BuildConfig.OMISE_PUBLIC_KEY
import com.omise.tamboon.R
import com.omise.tamboon.core.di.module.ViewModelFactory
import com.omise.tamboon.features.charities.domain.entity.CharityEntity
import com.omise.tamboon.features.charities.presentation.view.adapter.CharitiesAdapter
import com.omise.tamboon.features.charities.presentation.viewmodel.CharitiesViewModel
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_charities.*
import javax.inject.Inject

class CharitiesActivity :DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val mCharitiesViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)
            .get(CharitiesViewModel::class.java)
    }
    private val mCharitiesAdapter by lazy { CharitiesAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_charities)

        initToolBar()
        initViews()
        requestCharitiesList()
        initViewModelObservations()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_CANCELED) {
            // handle the cancellation
            return
        }

        if (requestCode == REQUEST_CC) {
            val token = data?.getParcelableExtra<Token>(EXTRA_TOKEN_OBJECT)
            // process your token here
        }
    }

    private fun initViews() {
        initToolBar()
        with(rvCharities){
            layoutManager = LinearLayoutManager(this@CharitiesActivity)
            adapter = mCharitiesAdapter
        }
        with(mCharitiesAdapter){
            onDonateClick ={
                startOmiseCreditCardActivity()

            }
        }
    }

    private fun startOmiseCreditCardActivity() {
        val intent = Intent(this, CreditCardActivity::class.java)
        intent.putExtra(OmiseActivity.EXTRA_PKEY, OMISE_PUBLIC_KEY)
        startActivityForResult(intent, REQUEST_CC)
    }

    private fun initToolBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)
    }

    private fun initViewModelObservations() {
        mCharitiesViewModel.charitiesResource.observe(this ,
            doOnSuccess = {setSuccessLayout(it)},
            doOnLoading = {svCharities.setLoading()},
            doOnNetworkError = {svCharities.setNetworkError(retryAction = {
                requestCharitiesList()
            })},
            doOnUnExpectedError = {svCharities.setUnexpectedError(retryAction = {
                requestCharitiesList()
            })})
    }

    private fun requestCharitiesList()  = mCharitiesViewModel.requestCharities()

    private fun setSuccessLayout(charitiesList :List<CharityEntity>){
        svCharities.setContent()
        if (charitiesList.isEmpty())
            svCharities.setEmpty(getString(R.string.label_charities_empty_msg))
        else
            mCharitiesAdapter.addItems(charitiesList as MutableList<CharityEntity>)


    }


    companion object{
        private const val REQUEST_CC = 100
        fun startMe(activity :Activity){
            activity.startActivity(Intent(activity , CharitiesActivity::class.java))
            activity.finish()
        }
    }
}
package com.omise.tamboon.features.donations.presentation.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.omise.tamboon.R
import com.omise.tamboon.core.di.module.ViewModelFactory
import com.omise.tamboon.core.extensions.*
import com.omise.tamboon.features.donations.domain.entity.DonationEntity
import com.omise.tamboon.features.donations.presentation.param.DonationNavigationParam
import com.omise.tamboon.features.donations.domain.entity.param.DonationRequestParam
import com.omise.tamboon.features.donations.presentation.viewmodel.DonationViewModel
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_donation.*
import javax.inject.Inject


class DonationActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val mDonationViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)
            .get(DonationViewModel::class.java)
    }

    private val mRootView by lazy {
        (this.findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0) as ViewGroup
    }

    private val donationNavigationParam by lazy {
        intent.getParcelableExtra<DonationNavigationParam>(DONATION_KEY)!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_donation)

        initViews()
        showSuccessSnackForCardInfo()
        initActions()
        initViewModelObservations()
    }

    private fun initViewModelObservations() {
        mDonationViewModel.donatioRes.observe(this,
            doOnSuccess = {
                /*        here should handel only success status ,but the api return error messages in success
                        and this is totally wrong.
                        so i have to deal with this to show the actual status to end user
                        */
                bindSuccessOrFailedLayout(it)
            },
            doOnLoading = {
                pbPay.startProgress()
                edtDonationAmount.disable()
            },
            doOnError = {
                /*
                * here should handle error with messages from api
                * but api error data object is not found in documentation */
                //   mRootView.getErrorSnack(messageRes = it.message!!).show()
            },
            doOnNetworkError = {
                mRootView.getSnackNetworkError().show()
            },
            doOnUnExpectedError = {
                mRootView.getSnackUnExpectedError().show()
            },
            doOnTerminate = {
                pbPay.stopProgress()
                edtDonationAmount.enable()
            })

    }

    private fun bindSuccessOrFailedLayout(donationEntity: DonationEntity) {
        with(donationEntity) {
            if (errorMessage.isNullOrEmpty())
                showSuccessDialog()
             else
                mRootView.getErrorSnack(message = errorMessage).show()
        }
    }

    private fun showSuccessDialog() {
        createAlertDialog(
            title = getString(R.string.label_success),
            message = getString(R.string.msg_donation_success , edtDonationAmount.getStringText()),
            icon = R.drawable.ic_logo,
            positiveButtonListener = {
                it.dismiss()
                finish()
            }
        )
    }

    private fun showSuccessSnackForCardInfo() {
        mRootView.getSuccessSnack(message = getString(R.string.msg_card_info_success)).show()
    }

    private fun initActions() {

        edtDonationAmount.onChange {
            if (isValidDonationValue()) {
                pbPay.enable()
                pbPay.label = getString(R.string.label_pay) + " $it THB"
            } else {
                pbPay.disable()
                pbPay.label = getString(R.string.label_pay)
            }
        }

        edtDonationAmount.onActionDone {
            closeKeyboard()
        }

        pbPay.onClick {
            donate()
        }
    }

    private fun donate() {
        closeKeyboard()
        if (isValidDonationValue() && ! pbPay.isLoading() ) {
            mDonationViewModel.donate(
                DonationRequestParam(
                    name = donationNavigationParam.userName,
                    token = donationNavigationParam.token,
                    amount = edtDonationAmount.getStringText()
                )
            )
        }

    }

    private fun initViews() {
        closeKeyboard()
        pbPay.disable()

        with(donationNavigationParam) {
            this@DonationActivity.initToolBar(charityName, true)
            imgCharity.loadImage(charityLogo)
            tvUserName.text = userName
        }

    }

    private fun isValidDonationValue() =
        edtDonationAmount.getStringText().isNotEmpty() && edtDonationAmount.getStringText().toDouble() > 0


    companion object {
        private const val DONATION_KEY = "DonationParam"

        fun startMe(activity: Activity, donationNavigationParam: DonationNavigationParam) {
            val intent = Intent(activity, DonationActivity::class.java)
            intent.putExtra(DONATION_KEY, donationNavigationParam)
            activity.startActivity(intent)
        }

    }
}
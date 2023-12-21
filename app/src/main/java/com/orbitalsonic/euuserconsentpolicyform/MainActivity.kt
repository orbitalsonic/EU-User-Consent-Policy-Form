package com.orbitalsonic.euuserconsentpolicyform

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.orbitalsonic.euuserconsentpolicyform.callbacks.ConsentCallback
import com.orbitalsonic.euuserconsentpolicyform.controller.ConsentController
import com.orbitalsonic.euuserconsentpolicyform.enums.CMPStatus

class MainActivity : AppCompatActivity() {

    val ADS_TAG = "adsTestingTAG"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * Search "addTestDeviceHashedId" in logcat after running the application
         * and past that device id for debug
         */

        ConsentController(this).apply {
            initConsent("My-Device-ID-For-Debug",object: ConsentCallback {
                override fun onReadyForInitialization() {
                    Log.d(ADS_TAG, "onReadyForInitialization, canRequestAds: $canRequestAds")
                }
                override fun onInitializationSuccess() {
                    Log.d(ADS_TAG, "onInitializationSuccess, canRequestAds: $canRequestAds")
                }
                override fun onInitializationError(error: String) {
                    Log.d(ADS_TAG, "onInitializationError, canRequestAds: $canRequestAds")
                }
                override fun onConsentFormAvailability(available: Boolean) {
                    Log.d(ADS_TAG, "onConsentFormAvailability, canRequestAds: $canRequestAds")
                }
                override fun onConsentFormLoadSuccess() {
                    Log.d(ADS_TAG, "onConsentFormLoadSuccess, canRequestAds: $canRequestAds")
                }
                override fun onConsentFormLoadFailure(error: String) {
                    Log.d(ADS_TAG, "onConsentFormLoadFailure, canRequestAds: $canRequestAds")
                }
                override fun onRequestShowConsentForm() {
                    Log.d(ADS_TAG, "onRequestShowConsentForm, canRequestAds: $canRequestAds")
                }
                override fun onConsentFormShowFailure(error: String) {
                    Log.d(ADS_TAG, "onConsentFormShowFailure, canRequestAds: $canRequestAds")
                }
                override fun onConsentFormDismissed() {
                    Log.d(ADS_TAG, "onConsentFormDismissed, canRequestAds: $canRequestAds")
                }

                override fun onConsentStatus(status: CMPStatus) {
                    Log.d(ADS_TAG, "onConsentStatus: $status, canRequestAds: $canRequestAds")
                    when(status){
                        CMPStatus.REQUIRED -> {
                            // Do Your Work Here
                        }
                        CMPStatus.NOT_REQUIRED -> {
                            // Do Your Work Here
                        }
                        CMPStatus.OBTAINED -> {
                            // Do Your Work Here
                        }
                        CMPStatus.UNKNOWN -> {
                            // Do Your Work Here
                        }
                    }

                }

                override fun onPolicyStatus(status: CMPStatus) {
                    Log.d(ADS_TAG, "onPolicyStatus: $status, canRequestAds: $canRequestAds")
                    when(status){
                        CMPStatus.REQUIRED -> {
                            // Do Your Work Here
                        }
                        CMPStatus.NOT_REQUIRED -> {
                            // Do Your Work Here
                        }
                        CMPStatus.UNKNOWN -> {
                            // Do Your Work Here
                        }
                        else -> {
                            // Do Your Work Here
                        }
                    }
                }
            })
        }
    }
}
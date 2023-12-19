package com.orbitalsonic.euuserconsentpolicyform.controller

import android.app.Activity
import android.util.Log
import com.google.android.ump.ConsentDebugSettings
import com.google.android.ump.ConsentForm
import com.google.android.ump.ConsentInformation
import com.google.android.ump.ConsentRequestParameters
import com.google.android.ump.UserMessagingPlatform
import com.orbitalsonic.euuserconsentpolicyform.BuildConfig
import com.orbitalsonic.euuserconsentpolicyform.callbacks.ConsentCallback

class ConsentController(private val activity: Activity) {

    val TAG = "consentControllerTAG"

    private var consentInformation: ConsentInformation? = null
    private var consentCallback: ConsentCallback? = null

    val canRequestAds: Boolean get() = consentInformation?.canRequestAds() ?: false

    fun initConsent(
        @Debug("Device Id is only use for DEBUG") deviceId: String,
        callback: ConsentCallback
    ) {
        this.consentCallback = callback

        val isDebug = BuildConfig.DEBUG

        val debugSettings = ConsentDebugSettings.Builder(activity)
            .setDebugGeography(ConsentDebugSettings.DebugGeography.DEBUG_GEOGRAPHY_EEA)
            .addTestDeviceHashedId(deviceId)
            .build()

        val params =
            if (isDebug) ConsentRequestParameters.Builder().setConsentDebugSettings(debugSettings)
                .build() else ConsentRequestParameters.Builder().setTagForUnderAgeOfConsent(false)
                .build()

        consentInformation = UserMessagingPlatform.getConsentInformation(activity).also {
            if (isDebug) {
                it.reset()
            }

            consentCallback?.onReadyForInitialization()
            Log.d(TAG, "onReadyForInitialization")
            it.requestConsentInfoUpdate(activity, params, {
                consentCallback?.onInitializationSuccess()
                consentCallback?.onConsentFormAvailability(it.isConsentFormAvailable)
                Log.d(TAG, "onInitializationSuccess")
                Log.d(TAG, "onConsentFormAvailability, available: ${it.isConsentFormAvailable}")
                if (it.isConsentFormAvailable) {
                    loadConsentForm()
                }
            }, { error ->
                consentCallback?.onInitializationError(error.message)
                Log.e(TAG, "onInitializationError: ${error.message}")
            })
        }
    }

    private fun loadConsentForm() {
        UserMessagingPlatform.loadConsentForm(activity, { consentForm ->
            consentCallback?.onConsentFormLoadSuccess()
            Log.d(TAG, "onConsentFormLoadSuccess")
            showConsentForm(consentForm)
        }) { formError ->
            consentCallback?.onConsentFormLoadFailure(formError.message)
            Log.e(TAG, "onConsentFormLoadFailure: ${formError.message}")
        }
    }

    private fun showConsentForm(consentForm: ConsentForm) {
        consentCallback?.onRequestShowConsentForm()
        Log.d(TAG, "onRequestShowConsentForm")
        consentForm.show(activity) { formError ->
            consentCallback?.onConsentFormDismissed()
            Log.d(TAG, "onConsentFormDismissed")
            formError?.let {
                consentCallback?.onConsentFormShowFailure(it.message)
                Log.e(TAG, "onConsentFormShowFailure: ${it.message}")
            } ?: run {
                checkForPrivacyOptions()
            }
        }
    }

    private fun checkForPrivacyOptions() {
        val isRequired =
            consentInformation?.privacyOptionsRequirementStatus == ConsentInformation.PrivacyOptionsRequirementStatus.REQUIRED
        consentCallback?.onPolicyRequired(isRequired)
        Log.d(TAG, "onPolicyRequired, isRequired: $isRequired")
    }

    annotation class Debug(val message: String = "For Debug Feature")
}
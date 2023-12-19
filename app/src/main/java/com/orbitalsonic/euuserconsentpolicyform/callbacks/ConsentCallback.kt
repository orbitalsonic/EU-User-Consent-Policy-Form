package com.orbitalsonic.euuserconsentpolicyform.callbacks

interface ConsentCallback {
    fun onReadyForInitialization()
    fun onInitializationSuccess()
    fun onInitializationError(error: String)
    fun onConsentFormAvailability(available:Boolean)
    fun onConsentFormLoadSuccess()
    fun onConsentFormLoadFailure(error: String)
    fun onRequestShowConsentForm()
    fun onConsentFormShowFailure(error: String)
    fun onConsentFormDismissed()
    fun onPolicyRequired(required: Boolean)
}
package com.orbitalsonic.euuserconsentpolicyform

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.orbitalsonic.euuserconsentpolicyform.callbacks.ConsentCallback
import com.orbitalsonic.euuserconsentpolicyform.controller.ConsentController

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ConsentController(this).initConsent("My-Device-ID-For-Debug",object:ConsentCallback{
            override fun onReadyForInitialization() {}
            override fun onInitializationSuccess() {}
            override fun onInitializationError(error: String) {}
            override fun onConsentFormAvailability(available: Boolean) {}
            override fun onConsentFormLoadSuccess() {}
            override fun onConsentFormLoadFailure(error: String) {}
            override fun onRequestShowConsentForm() {}
            override fun onConsentFormShowFailure(error: String) {}
            override fun onConsentFormDismissed() {}
            override fun onPolicyRequired(required: Boolean) {}
        })
    }
}
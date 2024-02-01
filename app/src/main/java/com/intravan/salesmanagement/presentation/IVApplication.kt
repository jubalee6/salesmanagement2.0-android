package com.intravan.salesmanagement.presentation

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class IVApplication :Application(){

    override fun onCreate() {
        super.onCreate()
    }
}
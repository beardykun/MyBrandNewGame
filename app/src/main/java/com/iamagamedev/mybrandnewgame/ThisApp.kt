package com.iamagamedev.mybrandnewgame

import android.app.Application

class ThisApp : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        var instance: ThisApp? = null
            private set
    }
}
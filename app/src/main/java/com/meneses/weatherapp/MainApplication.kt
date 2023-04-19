package com.meneses.weatherapp

import android.app.Application
import com.meneses.weatherapp.common.AppAPI

class MainApplication: Application() {

    companion object{
        lateinit var API: AppAPI
    }

    override fun onCreate() {
        super.onCreate()

        //Init Volley Singleton
        API = AppAPI.getInstance(this)
    }
}
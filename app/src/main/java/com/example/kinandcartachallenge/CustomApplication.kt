package com.example.kinandcartachallenge

import android.app.Application
import com.example.kinandcartachallenge.di.component.ApplicationComponent
import com.example.kinandcartachallenge.di.component.DaggerApplicationComponent

class CustomApplication: Application() {

    companion object{
        lateinit var applicationComponent: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent
            .builder()
            .application(this)
            .build()
        applicationComponent.inject(this)
    }

}
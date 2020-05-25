package com.example.kinandcartachallenge.di.component

import android.app.Application
import com.example.kinandcartachallenge.CustomApplication
import com.example.kinandcartachallenge.di.module.PersonNetworkingModule
import com.example.kinandcartachallenge.di.module.SubComponentsModule
import com.example.kinandcartachallenge.repository.PersonRepository
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = arrayOf(PersonNetworkingModule::class, SubComponentsModule::class))
@Singleton
interface ApplicationComponent {

    fun providePersonRepository(): PersonRepository


    fun inject(application: Application)

    fun mainComponent(): MainActivityComponent.Builder

    //fun addPersonComponent(): AddPostActivityComponent.Builder


    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: CustomApplication):Builder

        fun build(): ApplicationComponent
    }

}
package com.example.kinandcartachallenge.di.module

import com.example.kinandcartachallenge.network.PersonService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = arrayOf(ApplicationModule::class))
class PersonNetworkingModule {

    @Provides
    @Singleton
    fun providePersonRemoteSource(retrofit: Retrofit): PersonService{
        return retrofit.create(PersonService::class.java)
    }
}
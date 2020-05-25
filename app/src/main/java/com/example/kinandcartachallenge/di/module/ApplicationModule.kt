package com.example.kinandcartachallenge.di.module

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApplicationModule() {
    private  val BASE_URL = "https://s3.amazonaws.com/technical-challenge/v3/"

    @Provides
    @Singleton
    fun provideHTTPClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
            //.addInterceptor(BaseInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {

        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build();
    }

    /*@Provides
    @Singleton
    fun provideDatabase(application: CustomApplication): RSNDatabase{
        return Room.databaseBuilder(application, RSNDatabase::class.java, "RNSDatabase").build()
    }

    @Provides
    @Singleton
    fun providePostDao(database: RSNDatabase): PostDao{
        return database.postDao()
    }*/




}
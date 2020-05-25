package com.example.kinandcartachallenge.network

import com.example.kinandcartachallenge.domain.model.Person
import retrofit2.Call
import retrofit2.http.GET

interface PersonService {

    @GET("contacts.json")
    fun getPeople(): Call<List<Person>>

}
package com.example.kinandcartachallenge.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kinandcartachallenge.domain.model.Person
import com.example.kinandcartachallenge.network.PersonService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class PersonRepository @Inject constructor(
    val remoteSource: PersonService
) {

    fun getPeople(success: (List<Person>) ->Unit, error: (String) -> Unit){


        remoteSource.getPeople().enqueue(object : Callback<List<Person>> {

            override fun onResponse(call: Call<List<Person>>, response: Response<List<Person>>) {
                response.body()?.apply {
                    success(this)
                }
            }

            override fun onFailure(call: Call<List<Person>>, t: Throwable) {
                error("")
            }

        })
    }
}
package com.example.kinandcartachallenge.activity.main

import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.kinandcartachallenge.CustomApplication
import com.example.kinandcartachallenge.domain.model.Person
import com.example.kinandcartachallenge.repository.PersonRepository
import javax.inject.Inject

@BindingAdapter("bind:refreshing")
fun refresherHandler(srLayout: SwipeRefreshLayout, isRefreshing: LiveData<Boolean>) {
    isRefreshing.apply {
        if (!this.value!!) srLayout.isRefreshing = this.value!!
    }
}


class MainViewModel: ViewModel() {

    private val isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    private  val people: MutableLiveData<List<Person>> by lazy {
        MutableLiveData<List<Person>>()
    }
    private val conectionError: MutableLiveData<Boolean> = MutableLiveData(false)
    private val swipeLoading: MutableLiveData<Boolean> = MutableLiveData(false)

    @Inject
    lateinit var repository: PersonRepository

    init {
        CustomApplication
            .applicationComponent
            .mainComponent()
            .build()
            .inject(this)
            showLoading(true)
            getPeopleFromRepo()
    }

    fun getIsLoading():LiveData<Boolean>{
        return isLoading
    }

    fun getConectionError():LiveData<Boolean>{
        return conectionError
    }

    fun getSwipeLoading():LiveData<Boolean>{
        return swipeLoading
    }

    fun getPeople(): LiveData<List<Person>>{
        return people
    }


    fun getPeopleFromRepo(){
        val success: (List<Person>) -> Unit = {
            showLoading(false)
            showConectionError(false)
            showSwipeLoading(false)
            people.value = it
        }

        val error: (String) -> Unit = {
            showLoading(false)
            showConectionError(true)
            showSwipeLoading(false)
        }

        repository.getPeople(success,error)
    }

    private fun showLoading(show: Boolean){
        isLoading.value = show
    }

    private fun showSwipeLoading(show: Boolean){
        swipeLoading.value = show
    }

    private fun showConectionError(show: Boolean){
        conectionError.value = show

    }
}
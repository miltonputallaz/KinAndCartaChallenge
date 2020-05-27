package com.example.kinandcartachallenge.activity.detail

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kinandcartachallenge.R
import com.example.kinandcartachallenge.domain.model.Person
import com.example.kinandcartachallenge.utls.ImageUtil


@BindingAdapter("bind:imageBitmap")
fun loadImage(iv: ImageView, url: String?) {
    url?.also {
        ImageUtil.setImageFromUrl(iv.context, url, iv, R.drawable.user_large)
    }
}

class DetailViewModel: ViewModel() {
    private var person: MutableLiveData<Person> = MutableLiveData()

    fun getPerson(): LiveData<Person> = person

    fun setPerson(person: Person) {
        this.person.value = person
    }

    fun switchFavourite() {
        person.value!!.isFavorite = ! person.value!!.isFavorite
    }
}
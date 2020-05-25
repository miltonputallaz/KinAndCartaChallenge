package com.example.kinandcartachallenge.domain.model

import java.io.Serializable

class Person(
    val name: String,
    val id: Int,
    val companyName: String,
    var isFavorite: Boolean,
    val smallImageURL: String?,
    val largeImageURL: String?,
    val emailAddress: String,
    val birthdate: String,
    val phone: Phone,
    val address: Address
) : RecyclerViewItem, Serializable {
    override fun isItem() = true
}
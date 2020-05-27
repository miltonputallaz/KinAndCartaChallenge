package com.example.kinandcartachallenge.activity.detail

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.kinandcartachallenge.R
import com.example.kinandcartachallenge.databinding.ActivityDetailBinding
import com.example.kinandcartachallenge.domain.model.Person

class DetailActivity : AppCompatActivity() {

    companion object {
        const val SELECTED_CONTACT = "SELECTED_CONTACT"
        const val REQUEST_CODE = 200
        const val PERSON_ID = "PERSON_ID"
        const val FAV_STATUS = "FAV_STATUS"
    }

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel
    private var initialFavValue: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_detail)
        setUpToolbar()
        setUpBinding()
        viewModel.setPerson(intent.extras?.get(SELECTED_CONTACT) as Person)
        initialFavValue = viewModel.getPerson().value!!.isFavorite
    }

    private fun setUpToolbar(){
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu?.apply {
            val menuItem = findItem(R.id.favorite_menu)
            menuItem.isChecked = viewModel.getPerson().value!!.isFavorite
            setCorrectIcon(menuItem)
        }
        super.onPrepareOptionsMenu(menu)
        return true
    }


    private fun setUpBinding(){
        viewModel =  ViewModelProvider(this).get(DetailViewModel::class.java)
        binding.viewmodel = viewModel
        binding.handler = this
        binding.lifecycleOwner = this
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.favorite_menu -> {
            item.isChecked = !item.isChecked
            viewModel.switchFavourite()
            setCorrectIcon(item)
            true
        }

        android.R.id.home -> {
            onBackPressed()
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun setFavResult() {
        if (initialFavValue != viewModel.getPerson().value!!.isFavorite) {
            var data = Intent()
            data.putExtra(PERSON_ID, viewModel.getPerson().value!!.id)
            data.putExtra(FAV_STATUS, viewModel.getPerson().value!!.isFavorite)
            setResult(Activity.RESULT_OK,data)
        }
        else
            setResult(Activity.RESULT_CANCELED,null)
    }

    override fun onBackPressed() {
        setFavResult()
        super.onBackPressed()
    }

    private fun setCorrectIcon(item: MenuItem){
        if (item.isChecked){
            item.setIcon(R.drawable.ic_star_enabled)
        } else {
            item.setIcon(R.drawable.ic_star_disabled)
        }
    }

}

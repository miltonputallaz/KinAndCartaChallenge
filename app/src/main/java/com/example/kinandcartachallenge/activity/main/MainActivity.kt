package com.example.kinandcartachallenge.activity.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kinandcartachallenge.R
import com.example.kinandcartachallenge.activity.detail.DetailActivity
import com.example.kinandcartachallenge.adapter.PeopleAdapter
import com.example.kinandcartachallenge.databinding.ActivityMainBinding
import com.example.kinandcartachallenge.domain.model.Person
import com.example.kinandcartachallenge.utls.ImageUtil
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.person_item.view.*


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel

    private val selectedHandler: (view: View, person: Person) -> Unit = { view, person ->
        goToDetail(view, person)
    }

    private val getImageHandler: (imageUri: String, imageView: ImageView) -> Unit = { uri, view ->
        ImageUtil.setImageFromUrl(this, uri, view, R.drawable.user_small)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        setUpToolbar()
        setUpBinding()
        setUpRecyclerView()
        progress_bar.bringToFront()
    }

    private fun goToDetail(view: View, person: Person){
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.SELECTED_CONTACT, person)
        val p1 = Pair(view.person_image as View, "person_image")
        val p2 = Pair(view.person_name as View, "person_name")
        val p3 = Pair(view.person_company_name as View, "person_company_name")
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this,p1,p2,p3)
        startActivityForResult(intent, DetailActivity.REQUEST_CODE,options.toBundle())
    }

    private fun setUpToolbar(){
        setSupportActionBar(binding.toolbar)
    }

    private fun setUpBinding(){
        viewModel =  ViewModelProvider(this).get(MainViewModel::class.java)
        binding.viewmodel = viewModel
        binding.handler = this
        binding.lifecycleOwner = this
    }

    private fun setUpRecyclerView(){
        viewModel.people.observe(this, Observer {
            it
        })
        val viewManager = LinearLayoutManager(this)
        binding.peopleRecyclerview.apply {
            layoutManager = viewManager
            addItemDecoration(
                DividerItemDecoration(
                    getContext(),
                    DividerItemDecoration.VERTICAL
                )
            )

            adapter = PeopleAdapter(ArrayList(), selectedHandler, getImageHandler)
            viewModel.people.observe(binding.handler as MainActivity, Observer {
                it?.apply { (adapter as PeopleAdapter).setPeople(it) }
            })
        }

        binding.refresh.setOnRefreshListener{
            viewModel.getPeopleFromRepo()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == DetailActivity.REQUEST_CODE && resultCode == Activity.RESULT_OK){
            val personId = data!!.extras!!.getInt(DetailActivity.PERSON_ID)
            val favStatus = data!!.extras!!.getBoolean(DetailActivity.FAV_STATUS)
            (binding.peopleRecyclerview.adapter as PeopleAdapter).updatePerson(personId, favStatus)
        }
    }

}

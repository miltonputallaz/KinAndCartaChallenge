package com.example.kinandcartachallenge.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import com.example.kinandcartachallenge.R
import com.example.kinandcartachallenge.domain.model.Person
import com.example.kinandcartachallenge.domain.model.RecyclerViewHeader
import com.example.kinandcartachallenge.domain.model.RecyclerViewItem
import kotlinx.android.synthetic.main.person_item.view.*
import kotlinx.android.synthetic.main.separator_item.view.*

class PeopleAdapter(
    private var items: List<RecyclerViewItem>,
    val selectedHandler: (view: View, post: Person) -> Unit,
    val getImageHandler: (imageUri: String, imageView: ImageView) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    companion object LayoutType{
        val ITEM = 1
        val HEADER = 2
    }

    private lateinit var rawItems: List<Person>

    private lateinit var favoritePeople: List<Person>
    private lateinit var notFavoritePeople: List<Person>

    private var notFavoriteHeaderPosition: Int? = null


    private fun filterPeople(people: List<Person>): List<RecyclerViewItem>{
        var filteredPeople = people.partition {
            it.isFavorite
        }

        val unorderedFavoritePeople = filteredPeople.first
        val unorderedNotFavoritePeople = filteredPeople.second

        favoritePeople = orderListByName(unorderedFavoritePeople)
        notFavoritePeople = orderListByName(unorderedNotFavoritePeople)

        /* Tomo el tamaño de la lista de favoritos y le agrego uno teniendo en cuenta el header de estos,
        para colocar el header de no favoritos.*/
        notFavoriteHeaderPosition = favoritePeople.size + 1

        val arrayList : ArrayList<RecyclerViewItem> = ArrayList()
        arrayList.add(RecyclerViewHeader(R.string.favorite_contacts))
        arrayList.addAll(favoritePeople)
        arrayList.add(RecyclerViewHeader(R.string.other_contacts))
        arrayList.addAll(notFavoritePeople)
        return arrayList
    }

    private fun orderListByName(people: List<Person>): List<Person> {
        return people.sortedBy {
            it.name
        }
    }


    class ItemViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val name = view.person_name as TextView
        val company  = view.person_company_name as TextView
        val image = view.person_image as ImageView
        val favorite = view.person_favorite as ImageView

    }

    class SeparatorViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val separatorName = view.separator_text as TextView
    }





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        if (viewType == LayoutType.ITEM){
            return ItemViewHolder(layoutInflater.inflate(R.layout.person_item, parent, false))
        }
        return SeparatorViewHolder(layoutInflater.inflate(R.layout.separator_item, parent, false))
    }

    override fun getItemViewType(position: Int): Int {
        if (position == notFavoriteHeaderPosition || position == 0)
            return HEADER
        return ITEM
    }


    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val rvItem = items[position]
        if (rvItem.isItem()) {
            rvItem as Person
            holder as ItemViewHolder
            with(rvItem.isFavorite){
                if (this){
                    holder.favorite.visibility = View.VISIBLE
                } else {
                    holder.favorite.visibility = View.GONE
                }
            }


            rvItem.smallImageURL?.let {
                getImageHandler(it, holder.image)
            }


            holder.itemView.setOnClickListener { selectedHandler(holder.itemView, rvItem) }
            holder.name.text = rvItem.name
            rvItem.companyName?.let {
                holder.company.text = it
            }
        } else {
            rvItem as RecyclerViewHeader
            holder as SeparatorViewHolder
            holder.separatorName.text = holder.itemView.context.getString(rvItem.resourceId)
        }


    }



    // Se devuelve el tamaño de la lista y se suma dos de headers.
    override fun getItemCount() = items.size

    fun setPeople(people: List<Person>) {
        this.rawItems = people
        this.items = filterPeople(people)
        notifyDataSetChanged()
    }

    fun updatePerson(personId: Int, favorite: Boolean){
        val person = rawItems.find {
            it.id == personId
        }

        (person as Person).isFavorite = favorite
        setPeople(rawItems)
    }

}
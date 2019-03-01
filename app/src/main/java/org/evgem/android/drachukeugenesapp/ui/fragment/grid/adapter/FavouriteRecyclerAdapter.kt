package org.evgem.android.drachukeugenesapp.ui.fragment.grid.adapter

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import org.evgem.android.drachukeugenesapp.R
import org.evgem.android.drachukeugenesapp.data.FavouriteRepository
import org.evgem.android.drachukeugenesapp.data.entity.AppEntity
import org.evgem.android.drachukeugenesapp.ui.fragment.grid.viewholder.FavouriteViewHolder
import java.lang.IllegalStateException

class FavouriteRecyclerAdapter(private val count: Int, private val activity: AppCompatActivity) :
    RecyclerView.Adapter<FavouriteViewHolder>() {
    var pendingActivityResultHolder: FavouriteViewHolder? = null
    var currentAddingFavourite: AppEntity? = null
    var newFavouriteAdding = false
        set(value) {
            if (field == value) {
                return
            }
            field = value
            if (field) {
                if (currentAddingFavourite == null) {
                    throw IllegalStateException("currentAddingFavourite isn't specified")
                }
            } else {
                currentAddingFavourite = null
            }
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(container: ViewGroup, type: Int): FavouriteViewHolder {
        val view = LayoutInflater.from(container.context).inflate(R.layout.item_launcher_grid, container, false)
        return FavouriteViewHolder(view, this, activity)
    }

    override fun getItemCount(): Int = count

    override fun onBindViewHolder(holder: FavouriteViewHolder, pos: Int) {
        holder.newFavouriteAdding = newFavouriteAdding
        holder.bind(FavouriteRepository[pos])
    }
}
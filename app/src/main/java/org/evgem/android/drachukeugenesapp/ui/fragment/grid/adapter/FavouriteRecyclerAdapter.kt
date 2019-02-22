package org.evgem.android.drachukeugenesapp.ui.fragment.grid.adapter

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import org.evgem.android.drachukeugenesapp.R
import org.evgem.android.drachukeugenesapp.data.FavouriteRepository
import org.evgem.android.drachukeugenesapp.data.entity.AppEntity
import org.evgem.android.drachukeugenesapp.ui.fragment.grid.viewholder.FavouriteRecyclerViewHolder
import java.lang.IllegalStateException

class FavouriteRecyclerAdapter(private val count: Int, private val activity: AppCompatActivity) : RecyclerView.Adapter<FavouriteRecyclerViewHolder>() {
    var pendingActivityResultHolder: FavouriteRecyclerViewHolder? = null
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

    override fun onCreateViewHolder(container: ViewGroup, type: Int): FavouriteRecyclerViewHolder {
        val view = LayoutInflater.from(container.context).inflate(R.layout.item_launcher_grid, container, false)
        return FavouriteRecyclerViewHolder(view, this, activity)
    }

    override fun getItemCount(): Int = count

    override fun onBindViewHolder(holder: FavouriteRecyclerViewHolder, pos: Int) {
        holder.newFavouriteAdding = newFavouriteAdding
        holder.bind(FavouriteRepository[pos])
    }
}
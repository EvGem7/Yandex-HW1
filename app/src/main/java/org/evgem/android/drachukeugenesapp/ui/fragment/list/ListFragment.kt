package org.evgem.android.drachukeugenesapp.ui.fragment.list

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.evgem.android.drachukeugenesapp.R

class ListFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var fab: FloatingActionButton

    private lateinit var adapter: ListRecyclerAdapter
    private var itemCount = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        itemCount = savedInstanceState?.getInt(KEY_ITEM_COUNT) ?: itemCount
        adapter = object : ListRecyclerAdapter(itemCount) {
            override fun insert(pos: Int, color: Int?) {
                super.insert(pos, color)
                this@ListFragment.itemCount = itemCount
            }

            override fun delete(pos: Int) {
                super.delete(pos)
                this@ListFragment.itemCount = itemCount
            }
        }

        recyclerView = view.findViewById(R.id.recycler)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        fab = view.findViewById(R.id.list_fab)
        fab.setOnClickListener {
            adapter.insert(0)
            recyclerView.smoothScrollToPosition(0)
        }

        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_ITEM_COUNT, itemCount)
    }

    companion object {
        private const val KEY_ITEM_COUNT = "key item count"
    }
}
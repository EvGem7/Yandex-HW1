package org.evgem.android.drachukeugenesapp.ui.activity.list

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import org.evgem.android.drachukeugenesapp.R
import org.evgem.android.drachukeugenesapp.util.lastIndex

class ListActivity : AppCompatActivity() {
    companion object {
        private const val KEY_ITEM_COUNT = "key item count"
    }
    private lateinit var recyclerView: RecyclerView
    private lateinit var fab: FloatingActionButton

    private lateinit var listRecyclerAdapter: ListRecyclerAdapter
    private var itemCount = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        TODO("transform it to fragment")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        itemCount = savedInstanceState?.getInt(KEY_ITEM_COUNT) ?: itemCount
        listRecyclerAdapter = ListRecyclerAdapter(itemCount)

        recyclerView = findViewById(R.id.recycler)
        recyclerView.adapter = listRecyclerAdapter
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true)
        recyclerView.scrollToPosition(listRecyclerAdapter.lastIndex)

        fab = findViewById(R.id.fab)
        fab.setOnClickListener {
            listRecyclerAdapter.itemCount = ++itemCount
            listRecyclerAdapter.notifyDataSetChanged()
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt(KEY_ITEM_COUNT, itemCount)
    }
}
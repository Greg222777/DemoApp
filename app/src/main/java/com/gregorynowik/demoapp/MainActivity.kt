package com.gregorynowik.demoapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gregorynowik.demoapp.service.ShakeService


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startService(Intent(this, ShakeService::class.java))

        setContentView(R.layout.activity_main)
    }
}
package com.gregorynowik.demoapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gregorynowik.demoapp.databinding.ActivityMainBinding
import com.gregorynowik.demoapp.service.ShakeService


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        startService(Intent(this, ShakeService::class.java))

        setContentView(binding.root)
    }


}
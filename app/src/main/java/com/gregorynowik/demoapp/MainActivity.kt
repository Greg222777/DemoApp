package com.gregorynowik.demoapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import com.gregorynowik.demoapp.databinding.ActivityMainBinding
import com.gregorynowik.demoapp.service.ShakeService


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setSupportActionBar(binding.toolbar)

        startService(Intent(this, ShakeService::class.java))

        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        Log.d("GREGAPP", "extra ${intent.extras?.getBoolean(ShakeService.EXTRA_OPENED_FROM_SHAKE_NOTIFICATION)}")
        Log.d("GREGAPP", "countains ${intent.extras?.containsKey(ShakeService.EXTRA_OPENED_FROM_SHAKE_NOTIFICATION)}")
        setupActionBarWithNavController(this, findNavController(R.id.nav_host_fragment))
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp()
    }

}
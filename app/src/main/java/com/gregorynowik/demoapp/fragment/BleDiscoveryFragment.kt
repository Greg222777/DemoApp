package com.gregorynowik.demoapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.gregorynowik.demoapp.MainActivity
import com.gregorynowik.demoapp.databinding.FragmentBleDiscoveryBinding

class BleDiscoveryFragment : Fragment() {

    lateinit var binding: FragmentBleDiscoveryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentBleDiscoveryBinding.inflate(layoutInflater)


        return binding.root
    }

}
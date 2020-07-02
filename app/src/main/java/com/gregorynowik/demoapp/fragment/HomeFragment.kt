package com.gregorynowik.demoapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.gregorynowik.demoapp.R
import com.gregorynowik.demoapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    lateinit var binding : FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentHomeBinding.inflate(layoutInflater)



        binding.bleDiscoveryButton.setOnClickListener { navigateToBleDiscoveryFragment() }




        return binding.root
    }

    private fun navigateToBleDiscoveryFragment(){
        findNavController().navigate(R.id.action_homeFragment_to_bleDiscoveryFragment)
    }


}
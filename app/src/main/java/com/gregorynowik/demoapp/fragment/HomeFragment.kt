package com.gregorynowik.demoapp.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.gregorynowik.demoapp.R
import com.gregorynowik.demoapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    lateinit var binding : FragmentHomeBinding
    lateinit var blueToothDeviceViewModel : BlueToothDeviceViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        blueToothDeviceViewModel = ViewModelProviders.of(requireActivity()).get(BlueToothDeviceViewModel::class.java)

        binding = FragmentHomeBinding.inflate(layoutInflater)



        binding.bleDiscoveryButton.setOnClickListener { navigateToBleDiscoveryFragment() }




        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeValues()

    }

    private fun observeValues() {
        blueToothDeviceViewModel.blueToothDeviceLiveData.observe(requireActivity(), Observer {
            bluetoothDevice ->
            Log.d("GREGAPP", "bluetooth device name ${bluetoothDevice.name}")
            Log.d("GREGAPP", "bluetooth device adress ${bluetoothDevice.address}")
            Log.d("GREGAPP", "bluetooth device alias ${bluetoothDevice.alias}")
            Log.d("GREGAPP", "bluetooth device bt class  ${bluetoothDevice.bluetoothClass}")
            Log.d("GREGAPP", "bluetooth device bond state ${bluetoothDevice.bondState}")
            Log.d("GREGAPP", "bluetooth device type ${bluetoothDevice.type}")

        })
    }

    private fun navigateToBleDiscoveryFragment(){
        findNavController().navigate(R.id.action_homeFragment_to_bleDiscoveryFragment)
    }




}
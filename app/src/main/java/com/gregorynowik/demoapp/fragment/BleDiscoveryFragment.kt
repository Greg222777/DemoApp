package com.gregorynowik.demoapp.fragment

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.gregorynowik.demoapp.R
import com.gregorynowik.demoapp.databinding.FragmentBleDiscoveryBinding


class BleDiscoveryFragment : Fragment() {

    lateinit var binding: FragmentBleDiscoveryBinding
    lateinit var viewModel: BleDiscoveryFragmentViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProviders.of(this).get(BleDiscoveryFragmentViewModel::class.java)
        binding = FragmentBleDiscoveryBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkLocationPermission()
    }

    private fun checkLocationPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                enableBluetooth()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                context?.let {
                    MaterialDialog(it).show {
                        title(R.string.localisation_permission_rationale_title)
                        message(R.string.localisation_permission_rationale_text)
                        positiveButton { findNavController().popBackStack() }
                    }
                }
            }
            else -> {
                requireActivity().registerForActivityResult(
                    ActivityResultContracts.RequestPermission()
                ) { isGranted: Boolean ->
                    if (isGranted) {
                        enableBluetooth()
                    } else {
                        findNavController().popBackStack()
                    }
                }.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    private fun enableBluetooth() {
        when {
            viewModel.bluetoothAdapter == null -> {
            }
            !viewModel.bluetoothAdapter!!.isEnabled -> {
                val enableBluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                requireActivity().registerForActivityResult(
                    ActivityResultContracts.StartActivityForResult()
                ) { result ->
                    when (result.resultCode) {
                        Activity.RESULT_OK -> {
                            viewModel.startScanning()
                        }
                        Activity.RESULT_CANCELED -> {
                            findNavController().popBackStack()
                        }
                    }
                }.launch(enableBluetoothIntent)
            }
            else -> {
                viewModel.startScanning()
            }

        }
    }

}
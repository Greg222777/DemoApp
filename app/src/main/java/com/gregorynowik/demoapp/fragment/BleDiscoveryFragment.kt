package com.gregorynowik.demoapp.fragment

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.le.ScanResult
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.gregorynowik.demoapp.R
import com.gregorynowik.demoapp.adapter.ScanDeviceAdapter
import com.gregorynowik.demoapp.databinding.FragmentBleDiscoveryBinding


class BleDiscoveryFragment : Fragment(), ScanDeviceAdapter.ScanResultAdapterInterface {

    companion object {
        // error codes
        const val LOCALISATION_PERMISSION_NOT_GRANTED = 0
        const val BLUETOOTH_NOT_ENABLED = 1
        const val BLE_SCAN_ERROR = 2
    }

    lateinit var binding: FragmentBleDiscoveryBinding
    lateinit var viewModel: BleDiscoveryFragmentViewModel
    lateinit var blueToothDeviceViewModel : BlueToothDeviceViewModel

    val bleScanAdapter = ScanDeviceAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProviders.of(this).get(BleDiscoveryFragmentViewModel::class.java)
        blueToothDeviceViewModel = ViewModelProviders.of(requireActivity()).get(BlueToothDeviceViewModel::class.java)

        binding = FragmentBleDiscoveryBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // set up scan result recycler view
        binding.bleScanRecyclerView.adapter = bleScanAdapter
        binding.bleScanRecyclerView.layoutManager = LinearLayoutManager(context)
        bleScanAdapter.scanResultAdapterInterface = this

        observeValues()

        checkLocationPermission()
    }

    private fun observeValues() {
        viewModel.bleDeviceListLiveData.observe(viewLifecycleOwner, Observer { scanResultList ->
            bleScanAdapter.scanResultList = scanResultList
                bleScanAdapter.notifyDataSetChanged()
        })
        viewModel.bleScanError.observe(viewLifecycleOwner, Observer { aborted ->
            if (aborted) displayErrorMessage(BLE_SCAN_ERROR)
        })
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
                        positiveButton {
                            displayErrorMessage(LOCALISATION_PERMISSION_NOT_GRANTED)
                        }
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
                        displayErrorMessage(LOCALISATION_PERMISSION_NOT_GRANTED)
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
                            displayErrorMessage(BLUETOOTH_NOT_ENABLED)
                        }
                    }
                }.launch(enableBluetoothIntent)
            }
            else -> {
                viewModel.startScanning()
            }

        }
    }

    private fun displayErrorMessage(errorCode: Int) {
        context?.let {
            MaterialDialog(it).show {
                title(R.string.scan_error_dialog_title)
                message(
                    when (errorCode) {
                        LOCALISATION_PERMISSION_NOT_GRANTED -> R.string.scan_error_dialog_need_localisation_permission
                        BLUETOOTH_NOT_ENABLED -> R.string.scan_error_dialog_bluetooth_not_enabled
                        BLE_SCAN_ERROR -> R.string.scan_error_unknown_scan_error
                        else -> R.string.scan_error_unknown_scan_error
                    }
                )
                negativeButton {
                    // return to home screen when the error is acknowledged
                    findNavController().popBackStack()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.stopScanning()
    }

    override fun onScanResultClicked(scanResult: ScanResult) {
        blueToothDeviceViewModel.blueToothDeviceLiveData.value = scanResult.device
    }

}
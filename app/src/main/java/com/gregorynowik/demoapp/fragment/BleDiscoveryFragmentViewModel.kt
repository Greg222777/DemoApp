package com.gregorynowik.demoapp.fragment

import android.bluetooth.BluetoothAdapter
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class BleDiscoveryFragmentViewModel : ViewModel() {

    private val bleDeviceList = mutableListOf<ScanResult>()
    val bleDeviceListLiveData = MutableLiveData<MutableList<ScanResult>>()

    var bleScanError = MutableLiveData(false)

    var bluetoothAdapter: BluetoothAdapter? = null
    private var bleScanner: BluetoothLeScanner? = null

    private var isScanning = false

    private val bleScanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult?) {
            super.onScanResult(callbackType, result)
            addScanResult(result)
        }

        override fun onScanFailed(errorCode: Int) {
            super.onScanFailed(errorCode)
            bleScanError.postValue(true)
            stopScanning()
        }
    }

    init {
        // init bluetooth scan and adapter
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        bleScanner = bluetoothAdapter?.bluetoothLeScanner
    }

    fun startScanning() {
        if (!isScanning) {
            bleScanner?.startScan(bleScanCallback)
            isScanning = true
        }
    }

    fun stopScanning() {
        if (isScanning) {
            bleScanner?.stopScan(bleScanCallback)
            isScanning = false
        }
    }

    private fun addScanResult(scanResult: ScanResult?) {
        // do not add devices without a bluetooth name
        if (scanResult != null && !scanResult.device.name.isNullOrEmpty()) {
            // check that the result is not already in the list (adress is used to compare)
            if (!bleDeviceList.any { scanResultFromList -> scanResultFromList.device.address == scanResult.device.address }) {
                // add new discovered device to scan result list
                bleDeviceList.add(scanResult)
                bleDeviceListLiveData.value = bleDeviceList
            }
        }
    }


}
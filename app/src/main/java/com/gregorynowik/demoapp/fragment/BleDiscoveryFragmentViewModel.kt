package com.gregorynowik.demoapp.fragment

import android.bluetooth.BluetoothAdapter
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.util.Log
import androidx.lifecycle.ViewModel


class BleDiscoveryFragmentViewModel : ViewModel() {

    var bluetoothAdapter: BluetoothAdapter? = null
    private var bleScanner: BluetoothLeScanner? = null

    init {
        bluetoothAdapter= BluetoothAdapter.getDefaultAdapter()
        bleScanner = bluetoothAdapter?.bluetoothLeScanner
    }

    fun startScanning(){
        Log.d("GREGAPP", "start scan scanner null ? ${bleScanner == null}")
        bleScanner?.startScan(object : ScanCallback(){
            override fun onScanResult(callbackType: Int, result: ScanResult?) {
                super.onScanResult(callbackType, result)
                Log.d("GREGAPP", "on scan result ${result?.device?.name}")
            }

            override fun onScanFailed(errorCode: Int) {
                super.onScanFailed(errorCode)
                Log.e("GREGAPP", "on scan failed")
            }
        })
    }
}
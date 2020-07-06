package com.gregorynowik.demoapp.fragment

import android.bluetooth.BluetoothDevice
import android.bluetooth.le.ScanResult
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BlueToothDeviceViewModel : ViewModel() {

    val blueToothDeviceLiveData = MutableLiveData<BluetoothDevice>()

}
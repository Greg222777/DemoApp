package com.gregorynowik.demoapp.fragment

import android.bluetooth.BluetoothDevice
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BlueToothDeviceViewModel : ViewModel() {

    val blueToothDeviceLiveData = MutableLiveData<BluetoothDevice>()

}
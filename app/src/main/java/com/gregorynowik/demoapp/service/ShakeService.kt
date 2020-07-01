package com.gregorynowik.demoapp.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.IBinder
import android.util.Log
import com.gregorynowik.demoapp.service.ShakeDetector.OnShakeListener


class ShakeService : Service(), OnShakeListener {

    // Shake detection
    private var sensorManager: SensorManager? = null
    private var accelerometer: Sensor? = null
    private var shakeDetector: ShakeDetector? = null

    override fun onCreate() {
        super.onCreate()

        // ShakeDetector initialization
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        shakeDetector = ShakeDetector()
        shakeDetector?.setOnShakeListener(this)
        sensorManager?.registerListener(shakeDetector, accelerometer, SensorManager.SENSOR_DELAY_UI)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }


    override fun onShake(count: Int) {
        Log.d("GREGAPP", "ON SHAKE")
    }

}
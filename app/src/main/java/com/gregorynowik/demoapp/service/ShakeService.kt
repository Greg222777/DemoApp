package com.gregorynowik.demoapp.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Build
import android.os.IBinder
import com.gregorynowik.demoapp.MainActivity
import com.gregorynowik.demoapp.R
import com.gregorynowik.demoapp.service.ShakeDetector.OnShakeListener
import kotlin.random.Random


class ShakeService : Service(), OnShakeListener {

    companion object {
        const val CHANNEL_ID = "demo_app_channel_id"
        const val MIN_DELAY_BETWEEN_NOTIFICATIONS = 5*1000 //ms
    }

    // Shake detection
    private var sensorManager: SensorManager? = null
    private var accelerometer: Sensor? = null
    private var shakeDetector: ShakeDetector? = null
    private var lastSentNotification = 0.toLong()

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
        // check 5s min spam between notifications
        if (System.currentTimeMillis() > lastSentNotification + MIN_DELAY_BETWEEN_NOTIFICATIONS) {
            lastSentNotification = System.currentTimeMillis()
            showNotification()
        }
    }

    private fun showNotification() {

        // instanciate notification manager
        val notificationManager: NotificationManager =
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        // add notification channel if >= Oreo
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager)
        }

        // make intenent pointing to MainActivity
        val notificationIntent = Intent(baseContext, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }

        val pendingIntent = PendingIntent.getActivity(
            baseContext, 0,
            notificationIntent, 0
        )

        val notification =
            //channel ID is mandatory since Android O
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Notification.Builder(baseContext, CHANNEL_ID)
            } else {
                @Suppress("DEPRECATION")
                Notification.Builder(baseContext)
            }
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Your phone was shaken")
                .setContentText("Open this notification to connect to bluetooth")
                .build()

        notificationManager.notify(Random.nextInt(), notification)

    }

    /**
     * Creates the mandatory notification channel for devices >= Oreo
     * @param notificationManager notification manager used to send the shake notifications
     */
    private fun createNotificationChannel(notificationManager: NotificationManager) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "demo app notifications"
            val description = "Notifications for demo app"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(
                CHANNEL_ID, name,
                importance
            )
            channel.description = description
            // Register the channel with the system; you can't change the importance
            // or other notification behaviours after this
            notificationManager.createNotificationChannel(channel)
        }
    }

}
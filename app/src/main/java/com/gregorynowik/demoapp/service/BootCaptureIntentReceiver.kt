package com.gregorynowik.demoapp.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent


// used to launch ShakeService when phone is rebooted
class BootCaptureIntentReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action.equals(Intent.ACTION_BOOT_COMPLETED, ignoreCase = true)) {
            val serviceIntent = Intent(context, ShakeService::class.java)
            context.startService(serviceIntent)
        }
    }
}
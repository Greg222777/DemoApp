package com.gregorynowik.demoapp.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent


// here is the OnRevieve methode which will be called when boot completed
class BootCaptureIntentReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        //we double check here for only boot complete event
        if (intent.action.equals(Intent.ACTION_BOOT_COMPLETED, ignoreCase = true)) {
            //here we start the service  again.
            val serviceIntent = Intent(context, ShakeService::class.java)
            context.startService(serviceIntent)
        }
    }
}
package com.powerbutton.monitor;

import android.util.Log;
import android.app.KeyguardManager;

import android.content.Intent;
import android.content.Context;
import android.content.BroadcastReceiver;

public class PowerButtonMonitorReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        KeyguardManager keyguardManager = (KeyguardManager)context.getSystemService(Context.KEYGUARD_SERVICE);
        //if (keyguardManager.isKeyguardSecure()) {
        if(!keyguardManager.inKeyguardRestrictedInputMode()) {
          //Toast.makeText(getApplicationContext(), "Unlocked", Toast.LENGTH_LONG).show();
          Log.d("PowerButtonMonitorReceiver","Unlocked");
        }
    }
}

package com.powerbutton.monitor;

import android.util.Log;
import android.app.KeyguardManager;

import android.content.Intent;
import android.content.Context;
import android.content.BroadcastReceiver;

public class PowerButtonMonitorReceiver extends BroadcastReceiver {

    private static Intent powerButtonMonitorIntent;

    @Override
    public void onReceive(Context context, Intent intent) {

        KeyguardManager keyguardManager = (KeyguardManager)context.getSystemService(Context.KEYGUARD_SERVICE);
        if(!keyguardManager.inKeyguardRestrictedInputMode()) {
          Log.d("PowerButtonMonitorReceiver","Unlocked");
          powerButtonMonitorIntent = new Intent(context, PowerButtonMonitorService.class);
          powerButtonMonitorIntent.putExtra("state", "on");
          context.startService(powerButtonMonitorIntent);
        }
        else {
          Log.d("PowerButtonMonitorReceiver","locked");
          powerButtonMonitorIntent = new Intent(context, PowerButtonMonitorService.class);
          powerButtonMonitorIntent.putExtra("state", "off");
          context.startService(powerButtonMonitorIntent);
        }
    }
}

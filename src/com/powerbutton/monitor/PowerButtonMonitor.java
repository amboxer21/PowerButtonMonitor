package com.powerbutton.monitor;

import android.util.Log;
import android.widget.Toast;

import android.app.Activity;

import android.content.Intent;
import android.content.Context;
import android.content.IntentFilter;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.content.BroadcastReceiver;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Messenger;

public class PowerButtonMonitor extends Activity {

  private boolean mBound;
  private Messenger mService = null;
  private static long backPressedTime = 0;
  private static Intent powerButtonMonitorIntent;

  BroadcastReceiver PowerButtonMonitorReceiver = new BroadcastReceiver() {    
    @Override
    public void onReceive(Context context, Intent intent) {

      Log.i("[PowerButtonMonitorReceiver]", "MyReceiver");
      if(intent.getAction().equals(Intent.ACTION_SCREEN_ON)){
        Log.i("[PowerButtonMonitorReceiver]", "Screen ON");
        powerButtonMonitorIntent = new Intent(context, PowerButtonMonitorService.class);
        powerButtonMonitorIntent.putExtra("state", "on");
        context.startService(powerButtonMonitorIntent);
      }
      else if(intent.getAction().equals(Intent.ACTION_SCREEN_OFF)){
        Log.i("[PowerButtonMonitorReceiver]", "Screen OFF");
        powerButtonMonitorIntent = new Intent(context, PowerButtonMonitorService.class);
        powerButtonMonitorIntent.putExtra("state", "off");
        context.startService(powerButtonMonitorIntent);
      }

    }
  };

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    Intent serviceIntent = new Intent(this, PowerButtonMonitorService.class);
    startService(serviceIntent);

    registerReceiver(PowerButtonMonitorReceiver, new IntentFilter(Intent.ACTION_SCREEN_ON)); 
    registerReceiver(PowerButtonMonitorReceiver, new IntentFilter(Intent.ACTION_SCREEN_OFF));

  }

  @Override
  public void onBackPressed() {
    long mTime = System.currentTimeMillis();
    if(mTime - backPressedTime > 2000) {
      backPressedTime = mTime;
      Toast.makeText(this, "Press back again to close app.", Toast.LENGTH_SHORT).show();
    }
    else {
      super.onBackPressed();
    }
  }

  /*private ServiceConnection mConnection = new ServiceConnection() {

    @Override
    public void onServiceDisconnected(ComponentName name) {
      mService = null;
      mBound   = false;
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
      mService = new Messenger(service);
      mBound   = true;
    }
  };*/ 

}

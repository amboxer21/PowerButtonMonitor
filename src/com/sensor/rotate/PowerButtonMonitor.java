package com.powerbutton.monitor;

import android.util.Log;
import android.widget.Toast;
import android.app.Activity;

import android.content.Intent;
import android.content.Context;
import android.content.IntentFilter;
import android.content.ComponentName;
import android.content.ServiceConnection;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Messenger;

public class PowerButtonMonitor extends Activity {

  private boolean mBound;
  private Messenger mService = null;
  private static long backPressedTime = 0;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    /*bindService(new Intent(this, PowerButtonMonitorService.class), mConnection,
      Context.BIND_AUTO_CREATE);*/

    registerReceiver(new PowerButtonMonitorReceiver(), new IntentFilter("android.intent.action.USER_PRESENT"));
    registerReceiver(new PowerButtonMonitorReceiver(), new IntentFilter("android.intent.action.ACTION_SCREEN_ON"));
    registerReceiver(new PowerButtonMonitorReceiver(), new IntentFilter("android.intent.action.ACTION_SCREEN_OFF"));

    Intent serviceIntent = new Intent(this, PowerButtonMonitorService.class);
    startService(serviceIntent);

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

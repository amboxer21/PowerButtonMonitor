package com.powerbutton.monitor;

import android.util.Log;
import android.app.Service;
import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Vibrator;
import android.os.Messenger;

import android.telephony.SmsManager;
import android.telephony.TelephonyManager;

public class PowerButtonMonitorService extends Service {

  @Override
  public IBinder onBind(Intent intent) {
    return mMessenger.getBinder();
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    Log.d("PowerButtonMonitorService", "Service started.");
    return Service.START_STICKY;
  }

  class IncomingHandler extends Handler {
    @Override
    public void handleMessage(Message msg) {
      switch (msg.what) {
        case 0:
          if(msg.obj.toString().equals("chosen_string")) {
            //Toast.makeText(getApplicationContext(), msg.obj.toString(), Toast.LENGTH_SHORT).show();
          }
          else if(msg.obj.toString().equals("not_chosen_string")) {
            //sms("String goes here.", msg.obj.toString());
          }
        break;
        default:
          super.handleMessage(msg);
      }
    }
  }
 
  final Messenger mMessenger = new Messenger(new IncomingHandler());

  public void sms(String txt, String tn) {
    try {
      SmsManager smsManager = SmsManager.getDefault();
      smsManager.sendTextMessage(tn, null, txt, null, null);
    }
    catch (Exception e) {
      Log.e("PowerButtonMonitorService ERROR: Messsage not sent.", e.getMessage(), e);
    }
  }

}

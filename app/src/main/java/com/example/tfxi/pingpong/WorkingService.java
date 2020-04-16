package com.example.tfxi.pingpong;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import SharedPref.UserPref;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class WorkingService extends Service {
    public static final String CHANNEL_ID = "exampleServiceChannel";
    UserPref userPref;


    private static String LOG_TAG = "TimerService";
    private IBinder mBinder = new MyBinder();

    Handler onlineTimeHandler = new Handler();
    long startTime = 0L, timeInMilliseconds = 0L, timeSwapBuff = 0L, updatedTime = 0L;
    int mins, secs, hours;
    String time = "";
    private SoundPool soundPool;
    private   MediaPlayer mediaPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
        startTime = SystemClock.uptimeMillis();
        onlineTimeHandler.post(updateTimerThread);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        Intent notificationIntent = new Intent(this, UserDashBoard.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Start Working ...")
                .setContentText(time)
                .setSmallIcon(R.drawable.ic_build_black_24dp)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);

        //do heavy work on a background thread
        //stopSelf();
       // PlaySound();

        return START_STICKY;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onRebind(Intent intent) {
        Log.v(LOG_TAG, "in onRebind");
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.v(LOG_TAG, "in onUnbind");
        return true;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v(LOG_TAG, "in onDestroy");
        if(onlineTimeHandler!=null){
            onlineTimeHandler.removeCallbacks(updateTimerThread);
        }
       // mediaPlayer.stop();

    }


    public class MyBinder extends Binder {
        WorkingService getService() {
            return WorkingService.this;
        }
    }

    private Runnable updateTimerThread = new Runnable() {

        public void run() {

            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;

            updatedTime = timeSwapBuff + timeInMilliseconds;

            secs = (int) (updatedTime / 1000);
            hours = secs / (60 * 60);
            mins = secs / 60;
           // secs = secs % 60;
            if (mins >= 60) {
                mins = 0;
            }

            time = String.format("%02d", hours) + ":" + String.format("%02d", mins) + ":"
                    + String.format("%02d", secs);

            userPref = new UserPref(getApplicationContext());
            userPref.CreateTime(secs);

            int SaveTime =userPref.getSaveTime();//set time



            //check tast time exrtend the set time

            if(SaveTime >= secs){


            }

            else {
              //  PlaySound();
                /*Intent intent =  new Intent(getApplicationContext(),UserDashBoard.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);*/


            }




            onlineTimeHandler.postDelayed(this, 1 * 1000);

        }

    };

    public  void PlaySound(){
         mediaPlayer = MediaPlayer.create(this,R.raw.one);
        mediaPlayer.start();





    }
}
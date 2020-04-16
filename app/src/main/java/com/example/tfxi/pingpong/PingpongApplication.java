package com.example.tfxi.pingpong;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.onesignal.OneSignal;

import OTP.OTPReceivedHandler;
import OTP.OTPopenHandler;

public class PingpongApplication extends Application {

    private static PingpongApplication application;
    public static final String CHANNEL_ID = "exampleServiceChannel";

    public PingpongApplication() {
        application = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;

        createNotificationChannel();

        // OneSignal Initialization
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .filterOtherGCMReceivers(true)
                .setNotificationReceivedHandler(new OTPReceivedHandler())
                .setNotificationOpenedHandler(new OTPopenHandler())
                .init();
    }

    public static synchronized  PingpongApplication getInstance(){
        return application;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Example Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

}

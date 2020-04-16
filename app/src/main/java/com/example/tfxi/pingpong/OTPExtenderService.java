package com.example.tfxi.pingpong;

import android.content.Intent;
import android.util.Log;
import android.util.Printer;
import android.view.View;

import com.onesignal.NotificationExtenderService;
import com.onesignal.OSNotificationDisplayedResult;
import com.onesignal.OSNotificationReceivedResult;

import org.json.JSONObject;

import java.math.BigInteger;

import Config.RunningApp;
import SharedPref.UserPref;
import androidx.core.app.NotificationCompat;

public class OTPExtenderService extends NotificationExtenderService {

    private UserPref userPref ;
    @Override
    protected boolean onNotificationProcessing(OSNotificationReceivedResult osNotificationReceivedResult) {

        userPref =new UserPref((getApplicationContext()));


        JSONObject data = osNotificationReceivedResult.payload.additionalData;
        String type,id;

        type = data.optString("Reaction", null);
        Log.e("chaathhh",type);

        switch (type) {
            case "Active":
                Log.e("fdldjf","active0");
                break;

            case "ActiveUser":
                Log.e("fdldjf","actidve0");
                break;

            case "UnActive":
                Log.e("fdldjf","active0");
            userPref.Logout();

                break;
            case "Done":

                break;


            case "Approve":
            case "Reject":
                Log.e("fdldjf","aproce");
                Intent user = new Intent(getApplicationContext(), UserDashBoard.class);
                user.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(user);
                break;

            case "New Request":
            case "Receive New User Request":
                Intent admin = new Intent(getApplicationContext(), AdminDashboard.class);
                admin.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(admin);
                break;




            default:
                Log.e("fdldjf","activedeeee0");
                Intent intent = new Intent(getApplicationContext(), UserDashBoard.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
                break;
        }



        /*if (RunningApp.isAppRunning(getApplicationContext(), "com.example.tfxi.pingpong")) {
            Log.e(" running"," running");

        } else {
            // App is not running
            Log.e("not running","not running");

        }
*/


        OverrideSettings overrideSettings = new OverrideSettings();
        overrideSettings.extender = new NotificationCompat.Extender() {
            @Override
            public NotificationCompat.Builder extend(NotificationCompat.Builder builder) {
                return builder.setColor(new BigInteger("FF00FF00", 16).intValue());
            }
        };
        OSNotificationDisplayedResult displayedResult = displayNotification(overrideSettings);
        Log.d("OneSignalExample", "Notification displayed with id: " + displayedResult.androidNotificationId);



        return true;
    }
}

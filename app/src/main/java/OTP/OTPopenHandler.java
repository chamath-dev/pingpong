package OTP;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.tfxi.pingpong.AdminDashboard;
import com.example.tfxi.pingpong.MainActivity;
import com.example.tfxi.pingpong.PingpongApplication;
import com.example.tfxi.pingpong.SplashActivity;
import com.example.tfxi.pingpong.UserDashBoard;
import com.onesignal.OSNotificationAction;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;

import org.json.JSONObject;

import SharedPref.UserPref;

public class OTPopenHandler implements OneSignal.NotificationOpenedHandler {
    public Context context;
    private UserPref userPref ;
    @Override
    public void notificationOpened(OSNotificationOpenResult osNotificationOpenResult) {

        userPref =new UserPref (PingpongApplication.getInstance());


        Log.i("OneSignalExample", "customkey set with value: ");
        OSNotificationAction.ActionType actionType = osNotificationOpenResult.action.type;
        JSONObject data = osNotificationOpenResult.notification.payload.additionalData;
        String type,Otp;

        if(userPref.CheckToken() != null){
            if (data != null) {
                type = data.optString("Reaction", null);
                Log.e("ckdkdd",type);


                switch (type) {
                    case "Reject": {

                        Intent intent = new Intent(PingpongApplication.getInstance(), UserDashBoard.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("Reject", type);
                        PingpongApplication.getInstance().startActivity(intent);

                        break;
                    }
                    case "Approve": {

                        Intent intent = new Intent(PingpongApplication.getInstance(), UserDashBoard.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("Approve", type);
                        PingpongApplication.getInstance().startActivity(intent);

                        break;
                    }
                    case "New Request":
                    case "Receive New User Request":{

                        Log.e("ckdkdd","Request");
                        Intent intent = new Intent(PingpongApplication.getInstance(), AdminDashboard.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        PingpongApplication.getInstance().startActivity(intent);

                        break;
                    }


                    default: {
                        Intent intent = new Intent(PingpongApplication.getInstance(), UserDashBoard.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        PingpongApplication.getInstance().startActivity(intent);
                        break;
                    }
                }
            }
            else{
                Log.e("errpr","error");
            }
        }
        else {
            Intent intent = new Intent(PingpongApplication.getInstance(), SplashActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            PingpongApplication.getInstance().startActivity(intent);
        }



        if (actionType == OSNotificationAction.ActionType.ActionTaken)
            Log.i("OneSignalExample", "Button pressed with id: " + osNotificationOpenResult.action.actionID);

    }
}

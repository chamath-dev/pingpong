package OTP;

import android.util.Log;

import com.onesignal.OSNotification;
import com.onesignal.OneSignal;

import org.json.JSONObject;

public class OTPReceivedHandler implements OneSignal.NotificationReceivedHandler {
    @Override
    public void notificationReceived(OSNotification osNotification) {
        JSONObject data = osNotification.payload.additionalData;
        String customKey;

        if (data != null) {
            //While sending a Push notification from OneSignal dashboard
            // you can send an addtional data named "customkey" and retrieve the value of it and do necessary operation
            customKey = data.optString("customkey chcmyy", null);
            if (customKey != null)
                Log.e("OneSignalExa chadfdfmmy", "customkey set with value: " + customKey);
        }
    }
}

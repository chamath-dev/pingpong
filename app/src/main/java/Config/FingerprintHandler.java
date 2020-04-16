package Config;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.tfxi.pingpong.R;

import androidx.core.content.ContextCompat;

@TargetApi(Build.VERSION_CODES.M)
public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {

    private Context context;

    public FingerprintHandler(Context context){

        this.context = context;

    }

    public void startAuth(FingerprintManager fingerprintManager, FingerprintManager.CryptoObject cryptoObject){

        CancellationSignal cancellationSignal = new CancellationSignal();
        fingerprintManager.authenticate(cryptoObject, cancellationSignal, 0, this, null);

    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {

        this.update("There was an Auth Error. " + errString, false);

    }

    @Override
    public void onAuthenticationFailed() {

        this.update("Auth Failed. ", false);

    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {

        this.update("Error: " + helpString, false);

    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {

        this.update("You can now access the app.", true);

    }

    private void update(String s, boolean b) {

        TextView paraLabel = (TextView) ((Activity)context).findViewById(R.id.authtxt);
        Button button = (Button) ((Activity)context).findViewById(R.id.gobtn);


        LottieAnimationView lottieAnimationView = (LottieAnimationView) ((Activity)context).findViewById(R.id.animation);


        paraLabel.setAlpha(1);





        paraLabel.setText(s);

        if(b == false){

           /* paraLabel.setAlpha(1);
            Requestresone.setEnabled(false);
            paraLabel.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
            paraLabel.startAnimation(fromsmall);*/


        } else {
            button.setEnabled(true);
          //  lottieAnimationView.setVisibility(View.INVISIBLE);
            lottieAnimationView.setVisibility(View.VISIBLE);
            lottieAnimationView.setAnimation("finished.json");
            lottieAnimationView.playAnimation();

            paraLabel.setText("Done");


        }

    }
}
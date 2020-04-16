package com.example.tfxi.pingpong;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

public class AfterReg extends AppCompatActivity {
    TextView counttxt;
    private CountDownTimer countDownTimer;
    private long timeleftsecond = 10000;
   public static final String Shared_Preference = "UserPre";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_reg);

        counttxt = (TextView) findViewById(R.id.counttxt);
        CountDown();
    }

    private void CountDown(){

        countDownTimer = new CountDownTimer(timeleftsecond,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                long str = millisUntilFinished / 1000;
                String TimeFinished = String.valueOf(str);
                counttxt.setText(TimeFinished);
            }

            @Override
            public void onFinish() {
                Intent intent =  new Intent(AfterReg.this,MainActivity.class);
                startActivity(intent);
                finish();

            }
        }.start();


    }


}

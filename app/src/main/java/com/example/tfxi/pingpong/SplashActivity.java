package com.example.tfxi.pingpong;

import ApiInterface.UserInterface;
import Config.Config;
import Model.User;
import SharedPref.UserPref;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class SplashActivity extends AppCompatActivity {
    UserPref userPref;
    PingpongApplication pingpongApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        pingpongApplication = PingpongApplication.getInstance();
        userPref = new UserPref(getApplicationContext());

        Handler handler= new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                String token = userPref.CheckToken();

                if(userPref.CheckToken() != null){
                    GotoActivity(token);
                }
                else {
                    Intent intent =  new Intent(SplashActivity.this,MainActivity.class);
                    startActivity(intent);
                }

            }
        },2500);
    }

    public void GotoActivity(String Token){

        UserInterface userInterface = Config.getRetrofitInstance().create(UserInterface.class);
        Call<User> call = userInterface.GetUserDetails("Bearer" +Token);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                int errorCode = response.code();
                if(response.isSuccessful()){
                    Log.e("user status",response.body().getUser().getStatus());
                    String UserStatus = response.body().getUser().getStatus();
                    String UserType = response.body().getUser().getUser_type();

                    if (UserStatus.equals("No")){
                        Intent intent =  new Intent(SplashActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else {

                        Intent intent =  new Intent(SplashActivity.this,FinguerprintAuth.class);
                        intent.putExtra("UserType",UserType);
                        startActivity(intent);
                        finish();

                    }
                }
                else {
                    switch (errorCode) {

                        case 401:
                        case 400:
                            //error token
                            Intent LoginIntent =  new Intent(SplashActivity.this,MainActivity.class);
                            startActivity(LoginIntent);
                            break;

                        default:
                            Toast.makeText(getApplicationContext(),"Something is wrong",Toast.LENGTH_SHORT).show();
                            break;
                    }
                }


            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("error create UsrSESSION",t.getMessage());
            }
        });
    }
}

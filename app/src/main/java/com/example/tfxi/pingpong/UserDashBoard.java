package com.example.tfxi.pingpong;

import AdapterClass.TodayReportAdapter;
import AdapterClass.UserListAdapter;
import ApiInterface.RequetInterface;
import ApiInterface.UserInterface;
import Config.Config;
import Model.AdminRequest;
import Model.ApiError;
import Model.Notification;
import Model.NotificationData;
import Model.Request;
import Error.*;
import Model.RequestList;
import Model.TodayList;
import Model.UserReqest;
import SharedPref.UserPref;
import Utils.ErrorUtils;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import Config.FingerprintHandler;

public class UserDashBoard extends AppCompatActivity {

    ImageView Requestbtn,notificationIcon;
    Button mButtonStartPause;
    EditText Requestresone;
    private TextView Requesttxt,timetxt,textView36,toolbar_title;
    UserPref userPref;
    private List<RequestList> requestLists;
    private String SessionName,SessionEmployeeNumber;
    public   ApiError apiError = new ApiError();
    Animation animation;
    private Chronometer chronometer;
    private boolean runiing;
    private ProgressDialog progressDialog;
    private ConstraintLayout finished;
    private LinearLayout overbox;
    private ListView userhistory;
    private CountDownTimer countDownTimer;
    private long timeleftsecond = 10000;
    Animation fromsmall,fromnothing,loadclose,goback;
    LottieAnimationView lottieAnimationView,imageView17;
    String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dash_board);
        Requestbtn = (ImageView) findViewById(R.id.Requestbtn);
        mButtonStartPause = findViewById(R.id.button_start_pause);
        animation = AnimationUtils.loadAnimation(this,R.anim.time);
        chronometer = findViewById(R.id.chronometer);
        Requesttxt = (TextView) findViewById(R.id.Requesttxt);
        Requestresone = (EditText) findViewById(R.id.Requestresone);
        notificationIcon =(ImageView) findViewById(R.id.imageView6);
        textView36 =(TextView)findViewById(R.id.textView36);
        toolbar_title=(TextView)findViewById(R.id.toolbar_title);

        timetxt =(TextView)findViewById(R.id.timetxt);
        finished = (ConstraintLayout)findViewById(R.id.finished);
        overbox =(LinearLayout) findViewById(R.id.overbox);
        userhistory = (ListView) findViewById(R.id.userhistory);
        lottieAnimationView = (LottieAnimationView)findViewById(R.id.animation);
        imageView17 = (LottieAnimationView) findViewById(R.id.imageView17);

        Requestbtn.setVisibility(View.GONE);
        Requestresone.setVisibility(View.GONE);
        Requesttxt.setVisibility(View.GONE);
        mButtonStartPause.setVisibility(View.GONE);
        chronometer.setVisibility(View.GONE);
        textView36.setVisibility(View.GONE);
        notificationIcon.setVisibility(View.INVISIBLE);

        lottieAnimationView.setVisibility(View.GONE);

        fromsmall = AnimationUtils.loadAnimation(this,R.anim.fromsmall);
        fromnothing = AnimationUtils.loadAnimation(this,R.anim.fromnoting);
        loadclose = AnimationUtils.loadAnimation(this,R.anim.loadclose);
        goback = AnimationUtils.loadAnimation(this,R.anim.goback);

        finished.setAlpha(0);
        overbox.setAlpha(0);

        chronometer.setFormat("Time: %s");
        progressDialog = new ProgressDialog(this,R.style.MyAlertDialogStyle);
        userPref =new UserPref((getApplicationContext()));
        HashMap<String,String> UserSessionDetails = userPref.GetUserSessionData();
        SessionName = UserSessionDetails.get("UserNAME");
        SessionEmployeeNumber = UserSessionDetails.get("EmpNo");

        toolbar_title.setText("Hello ,"+" " +SessionName);
        token = userPref.CheckToken();

        CheckReq(SessionEmployeeNumber);
    }


    public void CheckReq(String EmployeeNumber){

        progressDialog.setMessage("Loading Data...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        RequetInterface requetInterface = Config.getRetrofitInstance().create(RequetInterface.class);
        Call<RequestList> call = requetInterface.CheckRequest("Bearer"+token,EmployeeNumber);
        call.enqueue(new Callback<RequestList>() {
            @Override
            public void onResponse(Call<RequestList> call, Response<RequestList> response) {
                Log.e("vhhha", String.valueOf(response.code()));
                int errorCode = response.code();

                switch (errorCode) {
                    case 200:
                        requestLists = response.body().getRequest();

                        if(requestLists.isEmpty()){
                            progressDialog.dismiss();

                            Requestresone.setVisibility(View.VISIBLE);
                            Requestbtn.setVisibility(View.VISIBLE);
                            Requesttxt.setVisibility(View.GONE);
                            mButtonStartPause.setVisibility(View.GONE);
                            lottieAnimationView.setVisibility(View.GONE);
                            Requestresone.setHint("Add Reason");

                            Requestbtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    CreatRequest();
                                }
                            });
                        }

                        for (int i = 0; i < requestLists.size(); i++) {

                            String status = requestLists.get(i).getStatus();
                            final Integer requestID =requestLists.get(i).getId();
                            final String note = requestLists.get(i).getNote();



                            switch (status) {
                                case "Pending":
                                    progressDialog.dismiss();
                                    Requestresone.setVisibility(View.GONE);
                                    Requestbtn.setVisibility(View.GONE);
                                    Requesttxt.setVisibility(View.VISIBLE);

                                    lottieAnimationView.setVisibility(View.VISIBLE);
                                    lottieAnimationView.setAnimation("pending.json");
                                    lottieAnimationView.playAnimation();

                                    Requesttxt.setText("Pending Request");
                                    break;

                                case "Approve":
                                    progressDialog.dismiss();
                                    Requestresone.setVisibility(View.VISIBLE);
                                    Requestbtn.setVisibility(View.VISIBLE);
                                    Requestresone.setHint("Enter Your OTP");

                                    final int finalI = i;
                                    Requestbtn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                        if (!ValidateRequestResone()){
                                            Requestresone.setError("Please Add OTP cODDE");
                                            return;
                                        }
                                        else {
                                            SentOtp(requestLists.get(finalI).getId(),Requestresone.getEditableText().toString());
                                        }
                                        }
                                    });
                                    break;

                                case "Done":
                                   // final int time=requestLists.get(i).getSetTime();

                                    if(note !=null && !note.isEmpty()){

                                      //  Log.e("not null",note+time);
                                        Note adminnote = new Note();
                                        Bundle bundle = new Bundle();
                                        bundle.putString("note",note);
                                        bundle.putInt("time",requestLists.get(i).getSetTime());
                                        adminnote.setArguments(bundle);
                                        adminnote.show(getSupportFragmentManager(), "my_dialog");
                                    }

                                    else{
                                        Log.e("null note","null");
                                    }

                                    progressDialog.dismiss();
                                    Requestresone.setText("");
                                    Requestresone.setVisibility(View.GONE);
                                    Requestbtn.setVisibility(View.GONE);
                                    Requesttxt.setVisibility(View.VISIBLE);
                                    Requesttxt.setText("ALL Done Satart work");
                                    lottieAnimationView.setVisibility(View.VISIBLE);
                                    lottieAnimationView.setAnimation("start.json");
                                    lottieAnimationView.playAnimation();
                                    mButtonStartPause.setVisibility(View.VISIBLE);
                                    mButtonStartPause.setText("Start");

                                    mButtonStartPause.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Start(requestID);
                                        }
                                    });
                                    break;

                                case "Working":
                                    progressDialog.dismiss();
                                    Requestresone.setVisibility(View.GONE);
                                    Requestbtn.setVisibility(View.GONE);
                                    Start(requestID);
                                    break;

                                case "Cancel"://when otp expier and cancel request user need create new request
                                case "Reject":
                                case "Finished":
                                    progressDialog.dismiss();
                                    Requestresone.setVisibility(View.VISIBLE);
                                    Requestbtn.setVisibility(View.VISIBLE);
                                    Requesttxt.setVisibility(View.GONE);
                                    mButtonStartPause.setVisibility(View.GONE);
                                    lottieAnimationView.setVisibility(View.GONE);
                                    Requestresone.setHint("Add Reason");

                                    Requestbtn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            CreatRequest();
                                        }
                                    });
                                    break;

                                default:
                                    break;
                            }

                        }

                        LoadTodayList();
                        textView36.setVisibility(View.VISIBLE);
                        break;

                    case 401:
                    case 400:
                    case 422:
                        TokenExpierdError adminBanded = new TokenExpierdError();
                        adminBanded.show(getSupportFragmentManager(), "my_dialog");
                        break;

                    default:
                        break;
                }
            }

            @Override
            public void onFailure(Call<RequestList> call, Throwable t) {
                progressDialog.dismiss();
                ServerError serverError = new ServerError();
                serverError.show(getSupportFragmentManager(), "my_dialog");
            }
        });
    }

    public void CreatRequest(){

        progressDialog.setMessage("Creating Request...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        if (!ValidateRequestResone()){
            return;
        }else {
            Request request = new Request();
            request.setUserID(SessionEmployeeNumber);
            request.setRequestResone(Requestresone.getEditableText().toString());

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Config.Api)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            RequetInterface requetInterface = retrofit.create(RequetInterface.class);
            Call<Request> call = requetInterface.CreateReq("Bearer"+token,request);

            call.enqueue(new Callback<Request>() {
                @Override
                public void onResponse(Call<Request> call, Response<Request> response) {
                    int errorCode = response.code();

                    switch (errorCode) {
                        case 200:
                            //waite for aprovement
                            progressDialog.dismiss();
                            Requestresone.setVisibility(View.GONE);
                            Requestbtn.setVisibility(View.GONE);
                            Requesttxt.setVisibility(View.VISIBLE);
                            Requesttxt.setText("Pending Request");

                            lottieAnimationView.setVisibility(View.VISIBLE);
                            lottieAnimationView.setAnimation("pending.json");
                            lottieAnimationView.playAnimation();

                            break;
                        case 401:
                        case 400:
                            progressDialog.dismiss();
                            break;
                        case 422:
                            progressDialog.dismiss();
                            Requestresone.setVisibility(View.VISIBLE);
                            break;
                        default:
                            progressDialog.dismiss();
                            break;
                    }


                }

                @Override
                public void onFailure(Call<Request> call, Throwable t) {
                    progressDialog.dismiss();
                    ServerError serverError = new ServerError();
                    serverError.show(getSupportFragmentManager(), "my_dialog");

                }
            });

        }
    }

    public void SentOtp(final Integer RequestID,String OTPCode){
        progressDialog.setMessage("Verifying  OTP Code ...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        Request request  = new Request();
        request.setId(RequestID);
        request.setOtp_code(OTPCode);

        RequetInterface requetInterface = Config.getRetrofitInstance().create(RequetInterface.class);
        Call<Request> call = requetInterface.SendOTP("Bearer"+token,request);
        call.enqueue(new Callback<Request>() {
            @Override
            public void onResponse(Call<Request> call, Response<Request> response) {

                if(response.isSuccessful()){
                    progressDialog.dismiss();
                    Otp serverError = new Otp();
                    serverError.show(getSupportFragmentManager(), "my_dialog");
                }
                else {
                    progressDialog.dismiss();
                    apiError = ErrorUtils.parseError(response);
                    String ErrorMessage = apiError.message();

                    switch (ErrorMessage){
                        case "Used": //alrady genarate otp for this it done or expier
                            Popup popup = new Popup();
                            popup.show(getSupportFragmentManager(),"my_dialog");
                            break;

                        case "OTP_EXPIRED":
                            Otpexpier otpexpier = new Otpexpier();
                            otpexpier.show(getSupportFragmentManager(),"my_dialog");
                            break;

                        case "OTP Token Not Match":
                            OTPnotMatch otPnotMatch = new OTPnotMatch();
                            otPnotMatch.show(getSupportFragmentManager(),"my_dialog");
                            break;

                        default:
                            Log.e("default","de");
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<Request> call, Throwable t) {
                progressDialog.dismiss();
                ServerError serverError = new ServerError();
                serverError.show(getSupportFragmentManager(), "my_dialog");
            }
        });

    }

    private void Start(Integer RequestID) {
        if(!runiing){
            Requesttxt.setVisibility(View.GONE);
            Requestresone.setVisibility(View.GONE);
            Requestbtn.setVisibility(View.GONE);
            chronometer.setVisibility(View.VISIBLE);
            int sec = userPref.getTime();

            chronometer.setBase(SystemClock.elapsedRealtime()- sec*1000);
            chronometer.start();
            runiing = true;

            StartWorking(RequestID);
        }
        else {

           chronometer.stop();
            Requesttxt.setVisibility(View.VISIBLE);
            Requesttxt.setText("Done");
            lottieAnimationView.setVisibility(View.GONE);

            int sec = userPref.getTime();
            runiing = false;
            StopWorking(RequestID,sec);
        }
    }

    public  boolean ValidateRequestResone(){
        return !TextUtils.isEmpty(Requestresone.getEditableText().toString());
    }

    public void StartWorking(final Integer RequestID){

        progressDialog.setMessage("Verifying  processing");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm:ss");
        String date = df.format(Calendar.getInstance().getTime());

        final Request request  = new Request();
        request.setId(RequestID);
        request.setStarted_time(date);

        RequetInterface requetInterface = Config.getRetrofitInstance().create(RequetInterface.class);
        Call<Request> call = requetInterface.StartWork("Bearer"+token,request);
        call.enqueue(new Callback<Request>() {
            @Override
            public void onResponse(Call<Request> call, Response<Request> response) {
                if(response.isSuccessful()){

                    progressDialog.dismiss();
                    Intent serviceIntent = new Intent(getApplicationContext(), WorkingService.class);
                    startService(serviceIntent);

                    lottieAnimationView.clearAnimation();
                    lottieAnimationView.setAnimation("timmer.json");
                    lottieAnimationView.playAnimation();

                    lottieAnimationView.setVisibility(View.VISIBLE);
                    mButtonStartPause.setVisibility(View.VISIBLE);
                    mButtonStartPause.setText("Finish Work");

                    mButtonStartPause.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Start(RequestID);
                        }
                    });

                }
                else {
                    progressDialog.dismiss();
                    apiError = ErrorUtils.parseError(response);
                    String ErrorMessage = apiError.message();

                    if ("cant start".equals(ErrorMessage)) {
                        Popup popup = new Popup();
                        popup.show(getSupportFragmentManager(), "my_dialog");
                    } else {
                        Log.e("default", "de");
                    }
                }

            }

            @Override
            public void onFailure(Call<Request> call, Throwable t) {
                progressDialog.dismiss();
                ServerError serverError = new ServerError();
                serverError.show(getSupportFragmentManager(), "my_dialog");
            }
        });

    }

    public void StopWorking(Integer RequestID,Integer Time){
        progressDialog.setMessage("Ending working....");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        mButtonStartPause.setVisibility(View.GONE);
        chronometer.setVisibility(View.GONE);

        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm:ss");
        String date = df.format(Calendar.getInstance().getTime());

        final Request request  = new Request();
        request.setId(RequestID);
        request.setFinished_time(date);
        request.setWorking_time(Time);
        request.setUserID(SessionEmployeeNumber);

        RequetInterface requetInterface = Config.getRetrofitInstance().create(RequetInterface.class);
        Call<Request> call = requetInterface.EndWork("Bearer"+token,request);
        call.enqueue(new Callback<Request>() {
            @Override
            public void onResponse(Call<Request> call, Response<Request> response) {
                if(response.isSuccessful()){
                    progressDialog.dismiss();
                    Intent serviceIntent = new Intent(getApplicationContext(), WorkingService.class);
                    stopService(serviceIntent);

                    overbox.setAlpha(1);
                    overbox.startAnimation(fromnothing);
                    finished.setAlpha(1);
                    finished.startAnimation(fromsmall);
                    notificationIcon.setEnabled(false);
                    CountDown();
                }
                else {
                    progressDialog.dismiss();
                    apiError = ErrorUtils.parseError(response);
                    String ErrorMessage = apiError.message();

                    if ("Not Update".equals(ErrorMessage)) {
                        progressDialog.dismiss();
                        Popup popup = new Popup();
                        popup.show(getSupportFragmentManager(), "my_dialog");
                    } else {
                        Log.e("default", "de");
                    }
                }
            }

            @Override
            public void onFailure(Call<Request> call, Throwable t) {
                Log.e("default", t.getMessage());
                progressDialog.dismiss();
                ServerError serverError = new ServerError();
                serverError.show(getSupportFragmentManager(), "my_dialog");
            }
        });

    }

    private void CountDown(){
        countDownTimer = new CountDownTimer(timeleftsecond,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                long str = millisUntilFinished / 1000;
                String TimeFinished = String.valueOf(str);
                timetxt.setText(TimeFinished);
            }

            @Override
            public void onFinish() {
                overbox.startAnimation(goback);
                finished.startAnimation(goback);

                ViewCompat.animate(finished).setStartDelay(1000).alpha(0).start();
                ViewCompat.animate(overbox).setStartDelay(1000).alpha(0).start();

               userPref.Logout();
            }
        }.start();


    }

    public void LoadTodayList(){
        ArrayList<UserReqest> notificationLists = new ArrayList<>();
        for (int i = 0; i < requestLists.size(); i++) {
            notificationLists.add(new UserReqest(requestLists.get(i).getRequestResone(),requestLists.get(i).getStatus()));
        }
        UserListAdapter notificationAdapter = new UserListAdapter(this,notificationLists);
        notificationAdapter.notifyDataSetChanged();
        userhistory.setAdapter(notificationAdapter);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}

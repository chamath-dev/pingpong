package com.example.tfxi.pingpong;

import AdapterClass.NotificationAdapter;
import AdapterClass.TodayReportAdapter;
import ApiInterface.AdminInterface;
import Config.Config;
import Model.AdminRequest;
import Model.DevisionBand;
import Model.NotificationData;
import Model.NotificationList;
import Model.Request;
import Model.RequestList;
import Model.TodayList;
import Model.User;
import Model.UserList;
import SharedPref.UserPref;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import Error.*;
public class AdminDashboard extends AppCompatActivity  {

    private CardView inhouseusers,pendingrequest;
    private TextView usercount,pendingcount,pendingusercount,userabantxt,useractivetxt;
    private UserPref userPref;
    String token;
    private List<Request> requestLists;
    private List<User> userList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView todayreport;
    private FloatingActionButton mainbtn,Userban,UserActive;
    Animation fabopen,fabclode,fabbackword,fabforword,fromsmall,fromnothing,loadclose,goback;
    boolean isOpen= false;

    ImageView closebtn,ActveUser,Banuserbtn,imageView9,imageView19,imageView22,imageView15,imageView6;
    ConstraintLayout linearLayout;

    LinearLayout overbox,banuser;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
       /* swipeRefreshLayout =(SwipeRefreshLayout) findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(this);*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);





        inhouseusers = (CardView) findViewById(R.id.inhouseusers);
        pendingrequest= (CardView)findViewById(R.id.pendingrequest);
        usercount = (TextView) findViewById(R.id.usercount);
        pendingcount =(TextView) findViewById(R.id.pendingcount);
        pendingusercount = (TextView) findViewById(R.id.pendingusercount);
        closebtn = (ImageView) findViewById(R.id.close);

        linearLayout = (ConstraintLayout) findViewById(R.id.linearLayout);
        overbox =(LinearLayout) findViewById(R.id.overbox);

        ActveUser = (ImageView)findViewById(R.id.ActveUser);
        Banuserbtn = (ImageView) findViewById(R.id.Banuserbtn);
        imageView9 = (ImageView) findViewById(R.id.imageView9);
        imageView19 = (ImageView) findViewById(R.id.imageView19);
        imageView22 = (ImageView) findViewById(R.id.imageView22);
        imageView15 = (ImageView) findViewById(R.id.imageView15);
        imageView6 =(ImageView) findViewById(R.id.imageView6);


        todayreport=(ListView) findViewById(R.id.todayreport);
       mainbtn = (FloatingActionButton) findViewById(R.id.floatingActionButton) ;

        fabopen = AnimationUtils.loadAnimation(this,R.anim.fab_open);
        fabclode = AnimationUtils.loadAnimation(this,R.anim.fab_close);
        fabbackword = AnimationUtils.loadAnimation(this,R.anim.rotate_backward);
        fabforword = AnimationUtils.loadAnimation(this,R.anim.rotate_forward);

        fromsmall = AnimationUtils.loadAnimation(this,R.anim.fromsmall);
        fromnothing = AnimationUtils.loadAnimation(this,R.anim.fromnoting);

        loadclose = AnimationUtils.loadAnimation(this,R.anim.loadclose);
        goback = AnimationUtils.loadAnimation(this,R.anim.goback);

        ActveUser.setEnabled(false);
        Banuserbtn.setEnabled(false);
        imageView19.setEnabled(false);
        imageView22.setEnabled(false);
        imageView15.setEnabled(false);



        userPref =new UserPref((getApplicationContext()));
        token = userPref.CheckToken();


        if(userPref.CheckToken() != null){

            LoadWorkingUserDetails();
            LaodPendingRequest();
            LaodPendingUsers();
            LoadToadyReport();
            // AnimUserBtn();
            linearLayout.setAlpha(0);
            overbox.setAlpha(0);
            closebtn.setVisibility(View.GONE);


            mainbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    inhouseusers.setEnabled(false);
                    pendingrequest.setEnabled(false);
                    imageView9.setEnabled(false);


                    mainbtn.setEnabled(false);


                    ActveUser.setEnabled(true);
                    Banuserbtn.setEnabled(true);
                    imageView19.setEnabled(true);
                    imageView22.setEnabled(true);
                    imageView15.setEnabled(true);

                    closebtn.setVisibility(View.VISIBLE);
                    closebtn.startAnimation(loadclose);
                    overbox.setAlpha(1);
                    overbox.startAnimation(fromnothing);
                    linearLayout.setAlpha(1);
                    linearLayout.startAnimation(fromsmall);


                }
            });

            closebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActveUser.setEnabled(false);
                    Banuserbtn.setEnabled(false);
                    imageView19.setEnabled(false);
                    imageView22.setEnabled(false);
                    imageView15.setEnabled(false);


                    inhouseusers.setEnabled(true);
                    pendingrequest.setEnabled(true);
                    imageView9.setEnabled(true);
                    imageView19.setEnabled(true);
                    imageView22.setEnabled(true);
                    mainbtn.setEnabled(true);
                    imageView15.setEnabled(true);

                    overbox.startAnimation(goback);
                    linearLayout.startAnimation(goback);
                    closebtn.startAnimation(goback);
                    closebtn.setVisibility(View.GONE);
                    ViewCompat.animate(linearLayout).setStartDelay(1000).alpha(0).start();
                    ViewCompat.animate(overbox).setStartDelay(1000).alpha(0).start();
                }
            });

            ActveUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =  new Intent(AdminDashboard.this,ActiveUser.class);
                    startActivity(intent);
                    finish();
                }
            });

            Banuserbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =  new Intent(AdminDashboard.this,UnactiveUser.class);
                    startActivity(intent);
                    finish();
                }
            });

            imageView19.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =  new Intent(AdminDashboard.this, DwvisionBand.class);
                    startActivity(intent);
                    finish();
                }
            });

            imageView22.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =  new Intent(AdminDashboard.this, ActiveDevision.class);
                    startActivity(intent);
                    finish();
                }
            });

            imageView15.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =  new Intent(AdminDashboard.this, UsersReport.class);
                    startActivity(intent);
                    finish();
                }
            });
            imageView6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    userPref.Logout();
                }
            });

        }
        else {
            Intent intent =  new Intent(AdminDashboard.this,MainActivity.class);
            startActivity(intent);
        }





    }

    public void WorkingUserView(View view) {
        //intent
        Intent intent =  new Intent(AdminDashboard.this,WorkingUsers.class);
        startActivity(intent);
        finish();
    }

    public void PendingRequest(View view) {
        Intent intent =  new Intent(AdminDashboard.this,PendingReqest.class);
        startActivity(intent);
        finish();
    }

    public void PendingUser(View view) {

        Intent intent =  new Intent(AdminDashboard.this,PendingUsers.class);
        startActivity(intent);
        finish();

    }

    public void  LoadWorkingUserDetails(){

        AdminInterface adminInterface = Config.getRetrofitInstance().create(AdminInterface.class);
        Call<AdminRequest> call = adminInterface.getWorkingUsers("Bearer"+token);
        call.enqueue(new Callback<AdminRequest>() {
            @Override
            public void onResponse(Call<AdminRequest> call, Response<AdminRequest> response) {
//                Log.e("fuctinadmin",String.valueOf(response.body().getCount()));
                usercount.setText(String.valueOf(response.body().getCount()));

                requestLists =response.body().getRequest();


                if(response.isSuccessful()){

                    for (int i = 0; i < requestLists.size(); i++) {
                       // usercount.setText(String.valueOf(requestLists.get(i).getCount()));
                        Log.e("sdsds",String.valueOf(requestLists.get(i).getCount()));
                    }

                }
                else {
                    Log.e("sdsds","dsdsds");

                }
            }

            @Override
            public void onFailure(Call<AdminRequest> call, Throwable t) {
                Log.e("fdfdbhfd",t.getMessage());
                ServerError serverError = new ServerError();
                serverError.show(getSupportFragmentManager(), "my_dialog");

            }
        });
    }

    public  void LaodPendingRequest(){

        AdminInterface adminInterface = Config.getRetrofitInstance().create(AdminInterface.class);
        Call<AdminRequest> call = adminInterface.getPendingRequest("Bearer"+token);
        call.enqueue(new Callback<AdminRequest>() {
            @Override
            public void onResponse(Call<AdminRequest> call, Response<AdminRequest> response) {
                Log.e("fuctinadmin pen",String.valueOf(response.body().getCount()));
                pendingcount.setText(String.valueOf(response.body().getCount()));

                requestLists =response.body().getRequest();


                if(response.isSuccessful()){

                    for (int i = 0; i < requestLists.size(); i++) {
                        // usercount.setText(String.valueOf(requestLists.get(i).getCount()));
                        Log.e("sdsds",String.valueOf(requestLists.get(i).getCount()));
                    }

                }
                else {
                    Log.e("sdsds","dsdsds");

                }
            }

            @Override
            public void onFailure(Call<AdminRequest> call, Throwable t) {
                Log.e("fdfdbhfd",t.getMessage());
                ServerError serverError = new ServerError();
                serverError.show(getSupportFragmentManager(), "my_dialog");

            }
        });

    }

    public  void LaodPendingUsers(){

        AdminInterface adminInterface = Config.getRetrofitInstance().create(AdminInterface.class);
        Call<UserList> call = adminInterface.getPendingUsers("Bearer"+token);
        call.enqueue(new Callback<UserList>() {
            @Override
            public void onResponse(Call<UserList> call, Response<UserList> response) {

                pendingusercount.setText(String.valueOf(response.body().getCount()));

                userList =response.body().getUser();


                if(response.isSuccessful()){

                    for (int i = 0; i < userList.size(); i++) {
                        // usercount.setText(String.valueOf(requestLists.get(i).getCount()));
                        Log.e("sdsds",String.valueOf(userList.get(i).getEmail()));
                    }

                }
                else {
                    Log.e("sdsds","dsdsds");

                }
            }

            @Override
            public void onFailure(Call<UserList> call, Throwable t) {
                Log.e("fdfdbhfd",t.getMessage());
                ServerError serverError = new ServerError();
                serverError.show(getSupportFragmentManager(), "my_dialog");

            }
        });

    }

    public void LoadToadyReport(){

        AdminInterface adminInterface = Config.getRetrofitInstance().create(AdminInterface.class);
        Call<AdminRequest> call = adminInterface.TodayReport("Bearer"+token);
        call.enqueue(new Callback<AdminRequest>() {
            @Override
            public void onResponse(Call<AdminRequest> call, Response<AdminRequest> response) {
                Log.e("dfdfdfdfdfuck",String.valueOf(response.code()));
                if(response.isSuccessful()){
                    requestLists=response.body().getRequest();
                    LoadTodayList();
                }else {

                }
            }

            @Override
            public void onFailure(Call<AdminRequest> call, Throwable t) {
                Log.e("fdfdbhfd",t.getMessage());
                ServerError serverError = new ServerError();
                serverError.show(getSupportFragmentManager(), "my_dialog");

            }
        });

    }

    public void LoadTodayList(){
        ArrayList<TodayList> notificationLists = new ArrayList<>();
        for (int i = 0; i < requestLists.size(); i++) {
            notificationLists.add(new TodayList(requestLists.get(i).getName(),requestLists.get(i).getUserID(),requestLists.get(i).getRequestResone(),requestLists.get(i).getStatus()));
        }
        TodayReportAdapter notificationAdapter = new TodayReportAdapter(this,notificationLists);
        notificationAdapter.notifyDataSetChanged();
        todayreport.setAdapter(notificationAdapter);




    }

}

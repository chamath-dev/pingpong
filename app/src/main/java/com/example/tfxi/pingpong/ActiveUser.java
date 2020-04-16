package com.example.tfxi.pingpong;

import AdapterClass.NotificationAdapter;
import AdapterClass.PendingUserAdapter;
import ApiInterface.AdminInterface;
import Config.Config;
import Model.NotificationData;
import Model.NotificationList;
import Model.PendingUserList;
import Model.User;
import Model.Users;
import SharedPref.UserPref;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import Error.*;

public class ActiveUser extends AppCompatActivity {
    private androidx.appcompat.widget.Toolbar toolbar;
    private ListView activeuserlist;
    private UserPref userPref;
    String token;
    private List<Users> usersList;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_user);

        activeuserlist= (ListView) findViewById(R.id.activeuserlist);
        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);

        userPref =new UserPref((getApplicationContext()));
        token = userPref.CheckToken();

        progressDialog = new ProgressDialog(this,R.style.MyAlertDialogStyle);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Active Users");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LoadActiveUserList();
    }

    public  void LoadActiveUserList(){
        progressDialog.setMessage("Loading ...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        AdminInterface adminInterface = Config.getRetrofitInstance().create(AdminInterface.class);
        Call<Users> call = adminInterface.ActiveUserList("Bearer"+token);
        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                usersList = response.body().getUser();
                int errorCode = response.code();

                if (response.isSuccessful()){

                    if (usersList.isEmpty()){
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"No any banded users",Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                    LoadList();


                }else {
                    switch (errorCode) {

                        case 401:
                        case 400:
                        case 422:
                            progressDialog.dismiss();
                            TokenExpierdError adminBanded = new TokenExpierdError();
                            adminBanded.show(getSupportFragmentManager(), "my_dialog");

                            break;
                        default:
                            Toast.makeText(getApplicationContext(),"Something is wrong",Toast.LENGTH_SHORT).show();
                            break;
                    }

                }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                progressDialog.dismiss();
                ServerError serverError = new ServerError();
                serverError.show(getSupportFragmentManager(), "my_dialog");

            }
        });

    }


    public void LoadList(){
        ArrayList<PendingUserList> pendingUserLists = new ArrayList<>();
        for (int i = 0; i < usersList.size(); i++) {
          pendingUserLists.add(new PendingUserList(usersList.get(i).getName(),usersList.get(i).getEmpoyeeNo(),usersList.get(i).getUser_devision(),usersList.get(i).getBan_Type()));
        }
        PendingUserAdapter pendingUserAdapter = new PendingUserAdapter(this,pendingUserLists);
        pendingUserAdapter.notifyDataSetChanged();
        activeuserlist.setAdapter(pendingUserAdapter);

        activeuserlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Users user = usersList.get(position);
                ActiveUsers(user.getEmpoyeeNo());
            }
        });

    }


    public void ActiveUsers(String EmpllyeeNo){
        progressDialog.setMessage("User Activating..");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        Users user = new Users();
        user.setEmpoyeeNo(EmpllyeeNo);

        AdminInterface adminInterface = Config.getRetrofitInstance().create(AdminInterface.class);
        Call<Users> call = adminInterface.ActievUsers("Bearer"+token,user);
        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                int errorCode = response.code();
                if(response.isSuccessful()){
                    progressDialog.dismiss();

                    ActiveDone done = new ActiveDone();
                    done.show(getSupportFragmentManager(), "my_dialog");
                }
                else {
                    switch (errorCode) {

                        case 401:
                        case 400:
                        case 422:
                            progressDialog.dismiss();
                            TokenExpierdError adminBanded = new TokenExpierdError();
                            adminBanded.show(getSupportFragmentManager(), "my_dialog");

                            break;
                        default:
                            Toast.makeText(getApplicationContext(),"Something is wrong",Toast.LENGTH_SHORT).show();
                            break;
                    }

                }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                progressDialog.dismiss();
                try {

                }catch (IllegalStateException E){
                    progressDialog.dismiss();
                    Done done = new Done();
                    done.show(getSupportFragmentManager(), "my_dialog");
                }
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent =  new Intent(ActiveUser.this,AdminDashboard.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent =  new Intent(ActiveUser.this,AdminDashboard.class);
        startActivity(intent);
        finish();
    }




}

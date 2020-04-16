package com.example.tfxi.pingpong;

import AdapterClass.PendingUserAdapter;
import AdapterClass.UserListAdapter;
import ApiInterface.AdminInterface;
import ApiInterface.RequetInterface;
import Config.Config;
import Model.PendingUserList;
import Model.RequestList;
import Model.UserReqest;
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
public class Report extends AppCompatActivity {

    private androidx.appcompat.widget.Toolbar toolbar;
    private ListView activeuserlist;
    private UserPref userPref;
    String token;

    private ProgressDialog progressDialog;
    private Intent intent;
    private String empid,UserToken,Title,Status;
    private List<RequestList> requestLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        activeuserlist= (ListView) findViewById(R.id.activeuserlist);
        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);

        userPref =new UserPref((getApplicationContext()));
        token = userPref.CheckToken();

        progressDialog = new ProgressDialog(this,R.style.MyAlertDialogStyle);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("User Report");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        intent = getIntent();
        empid = intent.getStringExtra("Emp_No");

        CheckReq(empid);
    }

    public void CheckReq(String EmployeeNumber){

        progressDialog.setMessage("Loading Data...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        String token = userPref.CheckToken();

        RequetInterface requetInterface = Config.getRetrofitInstance().create(RequetInterface.class);
        Call<RequestList> call = requetInterface.CheckRequest("Bearer"+token,EmployeeNumber);
        call.enqueue(new Callback<RequestList>() {
            @Override
            public void onResponse(Call<RequestList> call, Response<RequestList> response) {

                int errorCode = response.code();

                switch (errorCode) {
                    case 200:
                        requestLists = response.body().getRequest();
                        if(requestLists.isEmpty()){
                            Toast.makeText(getApplicationContext(),"No any data",Toast.LENGTH_SHORT).show();
                        }
                        LoadTodayList();
                        progressDialog.dismiss();
                        break;

                    case 401:
                    case 400:
                    case 422:
                        TokenExpierdError adminBanded = new TokenExpierdError();
                        adminBanded.show(getSupportFragmentManager(), "my_dialog");
                        break;

                    default:
                        Toast.makeText(getApplicationContext(),"Something is wrong",Toast.LENGTH_SHORT).show();
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
    public void LoadTodayList(){
        ArrayList<UserReqest> notificationLists = new ArrayList<>();
        for (int i = 0; i < requestLists.size(); i++) {
            notificationLists.add(new UserReqest(requestLists.get(i).getRequestResone(),requestLists.get(i).getStatus()));
        }
        UserListAdapter notificationAdapter = new UserListAdapter(this,notificationLists);
        notificationAdapter.notifyDataSetChanged();
        activeuserlist.setAdapter(notificationAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent =  new Intent(Report.this,UsersReport.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent =  new Intent(Report.this,UsersReport.class);
        startActivity(intent);
        finish();
    }
}

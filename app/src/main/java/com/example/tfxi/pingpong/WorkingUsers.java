package com.example.tfxi.pingpong;

import AdapterClass.NotificationAdapter;
import AdapterClass.WorkingUserAdapter;
import ApiInterface.AdminInterface;
import ApiInterface.UserInterface;
import Config.Config;
import Model.AdminRequest;
import Model.NotificationData;
import Model.NotificationList;
import Model.Request;
import Model.WorkingList;
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
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;
import Error.*;

public class WorkingUsers extends AppCompatActivity {
    private androidx.appcompat.widget.Toolbar toolbar;
    private ListView workinguserlist;
    private UserPref userPref;
    private String token;
    private List<Request> requestLists;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_working_users);

        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        workinguserlist= (ListView) findViewById(R.id.workinguserlist);
        progressDialog = new ProgressDialog(this,R.style.MyAlertDialogStyle);

        userPref =new UserPref((getApplicationContext()));
        token = userPref.CheckToken();

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Working users");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LoadWorkingUserDetails();

    }


    public void  LoadWorkingUserDetails(){

        progressDialog.setMessage("Loading Details..");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        AdminInterface adminInterface = Config.getRetrofitInstance().create(AdminInterface.class);
        Call<AdminRequest> call = adminInterface.getWorkingUsers("Bearer"+token);
        call.enqueue(new Callback<AdminRequest>() {
            @Override
            public void onResponse(Call<AdminRequest> call, Response<AdminRequest> response) {

                int errorCode = response.code();
                requestLists =response.body().getRequest();


                if(response.isSuccessful()){

                    if(requestLists.isEmpty()){
                        Toast.makeText(getApplicationContext(),"No any data",Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                    ShowNotificationList();

                }
                else {
                    progressDialog.dismiss();
                    switch (errorCode) {
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
            }

            @Override
            public void onFailure(Call<AdminRequest> call, Throwable t) {
                Log.e("fdfdbhfd",t.getMessage());
                progressDialog.dismiss();
                ServerError serverError = new ServerError();
                serverError.show(getSupportFragmentManager(), "my_dialog");

            }
        });
    }



    public  void ShowNotificationList(){
        ArrayList<WorkingList> workingLists = new ArrayList<>();
        for (int i = 0; i < requestLists.size(); i++) {
            Log.e("ttt",requestLists.get(i).getUser_devision());
            workingLists.add(new WorkingList(requestLists.get(i).getName(),requestLists.get(i).getUserID(),requestLists.get(i).getRequestResone(),requestLists.get(i).getStarted_time(),requestLists.get(i).getUser_devision()));
        }
        WorkingUserAdapter workingUserAdapter = new WorkingUserAdapter(this,workingLists);
        workingUserAdapter.notifyDataSetChanged();
        workinguserlist.setAdapter(workingUserAdapter);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent =  new Intent(WorkingUsers.this,AdminDashboard.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent =  new Intent(WorkingUsers.this,AdminDashboard.class);
        startActivity(intent);
        finish();
    }
}

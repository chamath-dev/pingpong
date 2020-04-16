package com.example.tfxi.pingpong;

import AdapterClass.PendingUserAdapter;
import ApiInterface.AdminInterface;
import Config.Config;
import Model.NotificationData;
import Model.PendingUserList;
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

public class UsersReport extends AppCompatActivity {

    private androidx.appcompat.widget.Toolbar toolbar;
    private ListView activeuserlist;
    private UserPref userPref;
    String token;
    private List<Users> usersList;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_report);

        activeuserlist= (ListView) findViewById(R.id.activeuserlist);
        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);

        userPref =new UserPref((getApplicationContext()));
        token = userPref.CheckToken();

        progressDialog = new ProgressDialog(this,R.style.MyAlertDialogStyle);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("User Report");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LoadActiveUserList();
    }


    public  void LoadActiveUserList(){
        progressDialog.setMessage("Loading data ...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        AdminInterface adminInterface = Config.getRetrofitInstance().create(AdminInterface.class);
        Call<Users> call = adminInterface.UserReport("Bearer"+token);
        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                int errorCode = response.code();
                usersList = response.body().getUser();

                if (response.isSuccessful()){

                    if (usersList.isEmpty()){
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"No any data",Toast.LENGTH_SHORT).show();
                    }

                    LoadList();
                    progressDialog.dismiss();
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

                Intent SingleNotification =  new Intent(UsersReport.this, Report.class);

                Users notificationData = usersList.get(position);

                SingleNotification.putExtra("Emp_No",notificationData.getEmpoyeeNo());

                startActivity(SingleNotification);
                finish();

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent =  new Intent(UsersReport.this,AdminDashboard.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent =  new Intent(UsersReport.this,AdminDashboard.class);
        startActivity(intent);
        finish();
    }

}

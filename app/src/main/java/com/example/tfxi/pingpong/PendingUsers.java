package com.example.tfxi.pingpong;

import AdapterClass.NotificationAdapter;
import AdapterClass.PendingUserAdapter;
import ApiInterface.AdminInterface;
import Config.Config;
import Model.AdminRequest;
import Model.NotificationData;
import Model.NotificationList;
import Model.PendingUserList;
import Model.User;
import Model.UserList;
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
import java.util.HashMap;
import java.util.List;
import Error.*;
public class PendingUsers extends AppCompatActivity {
    private androidx.appcompat.widget.Toolbar toolbar;
    private ListView listView;
    private ProgressDialog progressDialog;
    UserPref userPref;
    private String SessionEmployeeNumber,token,SessionName;
    private List<User> userList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_users);

        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar2);
        listView =(ListView) findViewById(R.id.PendingUserList);
        progressDialog = new ProgressDialog(this,R.style.MyAlertDialogStyle);

        userPref =new UserPref((getApplicationContext()));
        token = userPref.CheckToken();

        HashMap<String,String> UserSessionDetails = userPref.GetUserSessionData();
        SessionName = UserSessionDetails.get("UserNAME");
        SessionEmployeeNumber = UserSessionDetails.get("EmpNo");

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Pending user request");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        LoadPendingUserList();

    }

    public  void LoadPendingUserList(){

        progressDialog.setMessage("Loading data .....");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        AdminInterface adminInterface = Config.getRetrofitInstance().create(AdminInterface.class);
        Call<UserList> call = adminInterface.getPendingUsers("Bearer"+token);
        call.enqueue(new Callback<UserList>() {
            @Override
            public void onResponse(Call<UserList> call, Response<UserList> response) {

                int errorCode = response.code();
                userList =response.body().getUser();

                if(response.isSuccessful()){
                    progressDialog.dismiss();
                    if(userList.isEmpty()){
                        Toast.makeText(getApplicationContext(),"No any data",Toast.LENGTH_SHORT).show();
                    }
                    ShowPendingUsers();
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
            public void onFailure(Call<UserList> call, Throwable t) {
                progressDialog.dismiss();
                ServerError serverError = new ServerError();
                serverError.show(getSupportFragmentManager(), "my_dialog");
            }
        });
    }


    public  void ShowPendingUsers(){
        ArrayList<PendingUserList> pendingUserLists = new ArrayList<>();
        for (int i = 0; i < userList.size(); i++) {

            pendingUserLists.add(new PendingUserList(userList.get(i).getName(),userList.get(i).getEmpoyee_no(),userList.get(i).getUser_devision(),""));
        }
        PendingUserAdapter pendingUserAdapter = new PendingUserAdapter(this,pendingUserLists);
        pendingUserAdapter.notifyDataSetChanged();
        listView.setAdapter(pendingUserAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
       User user = userList.get(position);
                AcceptUser(user.getEmpoyee_no());

            }
        });
    }

    public void AcceptUser(String Emp_No){
        progressDialog.setMessage("User Activation processing .....");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        Users user = new Users();
        user.setEmpoyeeNo(Emp_No);

        AdminInterface adminInterface = Config.getRetrofitInstance().create(AdminInterface.class);
        Call<Users> call = adminInterface.AllowUser("Bearer"+token,user);
        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {

                int errorCode = response.code();

                if(response.isSuccessful()){
                    progressDialog.dismiss();

                    Done done = new Done();
                    done.show(getSupportFragmentManager(), "my_dialog");
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
            Intent intent =  new Intent(PendingUsers.this,AdminDashboard.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent =  new Intent(PendingUsers.this,AdminDashboard.class);
        startActivity(intent);
        finish();
    }

}

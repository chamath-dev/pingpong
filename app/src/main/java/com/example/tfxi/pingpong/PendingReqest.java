package com.example.tfxi.pingpong;

import AdapterClass.PendingRequestAdapter;
import AdapterClass.WorkingUserAdapter;
import ApiInterface.AdminInterface;
import Config.Config;
import Model.AdminRequest;
import Model.NotificationData;
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

import java.util.ArrayList;
import java.util.List;
import Error.*;
public class PendingReqest extends AppCompatActivity {
    private androidx.appcompat.widget.Toolbar toolbar;
    private ListView pendinfrequestlist;
    private UserPref userPref;
    private String token;
    private List<Request> requestLists;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_reqest);
        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);

        pendinfrequestlist= (ListView) findViewById(R.id.pendinfrequestlist);
        progressDialog = new ProgressDialog(this,R.style.MyAlertDialogStyle);

        userPref =new UserPref((getApplicationContext()));
        token = userPref.CheckToken();

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Pending Request");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        PendingRequest();
    }


    public void  PendingRequest(){

        progressDialog.setMessage("Pending Request Details..");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        AdminInterface adminInterface = Config.getRetrofitInstance().create(AdminInterface.class);
        Call<AdminRequest> call = adminInterface.getPendingRequest("Bearer"+token);
        call.enqueue(new Callback<AdminRequest>() {
            @Override
            public void onResponse(Call<AdminRequest> call, Response<AdminRequest> response) {
                int errorCode = response.code();
                requestLists =response.body().getRequest();



                if(response.isSuccessful()){
                    progressDialog.dismiss();
                    if(requestLists.isEmpty()){
                        Toast.makeText(getApplicationContext(),"No any pending request",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        ShowNotificationList();
                    }


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
            public void onFailure(Call<AdminRequest> call, Throwable t) {
                progressDialog.dismiss();
                ServerError serverError = new ServerError();
                serverError.show(getSupportFragmentManager(), "my_dialog");

            }
        });
    }



    public  void ShowNotificationList(){
        ArrayList<WorkingList> workingLists = new ArrayList<>();
        for (int i = 0; i < requestLists.size(); i++) {
            workingLists.add(new WorkingList(requestLists.get(i).getName(),requestLists.get(i).getUserID(),requestLists.get(i).getRequestResone(),requestLists.get(i).getStarted_time(),requestLists.get(i).getUser_devision()));

        }
        PendingRequestAdapter pendingRequestAdapter = new PendingRequestAdapter(this,workingLists);
        pendingRequestAdapter.notifyDataSetChanged();
        pendinfrequestlist.setAdapter(pendingRequestAdapter);

        pendinfrequestlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                Intent intent =  new Intent(PendingReqest.this, AcceptRequest.class);

                Request adminRequest = requestLists.get(position);

                intent.putExtra("RequestID",adminRequest.getId().toString());
                intent.putExtra("Emp_No",adminRequest.getUserID());
                intent.putExtra("Emp_Name",adminRequest.getName());
                intent.putExtra("Resone",adminRequest.getRequestResone());
                intent.putExtra("RequestaddTime",adminRequest.getStarted_time());

                startActivity(intent);
                finish();

            }
        });




    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent =  new Intent(PendingReqest.this,AdminDashboard.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent =  new Intent(PendingReqest.this,AdminDashboard.class);
        startActivity(intent);
        finish();
    }



}

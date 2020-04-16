package com.example.tfxi.pingpong;

import ApiInterface.AdminInterface;
import Config.Config;
import Model.AdminRequest;
import Model.Request;
import SharedPref.UserPref;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;
import Error.*;
public class AcceptRequest extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private androidx.appcompat.widget.Toolbar toolbar;
    private UserPref userPref;
    private String token,RequestaddTime,Resone,Emp_Name,Emp_No,RequestID;
    private List<Request> requestLists;
    private ProgressDialog progressDialog;
    EditText resonetxt;
    TextView Resone_,Emp_id_,empname_,time;
    ImageView accept,reject;
    Button timepicker;
    int totalsecond;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_request);

        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        progressDialog = new ProgressDialog(this,R.style.MyAlertDialogStyle);
        resonetxt = (EditText) findViewById(R.id.resonetxt);
        Resone_ =( TextView) findViewById(R.id.Resone);

        Emp_id_ =( TextView) findViewById(R.id.Emp_id);
        empname_ =( TextView) findViewById(R.id.empname);

        timepicker=(Button)findViewById(R.id.timepicker);
        time = (TextView) findViewById(R.id.time);

        reject =(ImageView) findViewById(R.id.reject);
        accept = (ImageView) findViewById(R.id.accept);


        userPref =new UserPref((getApplicationContext()));
        token = userPref.CheckToken();

        Intent intent = getIntent();
        RequestID = intent.getStringExtra("RequestID");
        Emp_No = intent.getStringExtra("Emp_No");
        Emp_Name = intent.getStringExtra("Emp_Name");
        Resone = intent.getStringExtra("Resone");

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Pending request control");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SetDataToView();

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!ValidateSetTime()){
                    AceeptRequest(Emp_No,Integer.parseInt(RequestID),"Approve",null,null);
                }
                else{
                    if (!ValidateNoteBtn()){
                        return;
                    }
                    else {
                        String text = null;
                        text = resonetxt.getEditableText().toString();
                        AceeptRequest(Emp_No,Integer.parseInt(RequestID),"Approve",text,String.valueOf(totalsecond));
                    }
                }

            }
        });

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ValidateNoteBtn()){
                    return;
                }
                else {
                    RejectRequest(Emp_No,Integer.parseInt(RequestID),"Reject",resonetxt.getEditableText().toString());
                }
            }
        });

        timepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePicker timePicker = new TimePicker();
                timePicker.show(getSupportFragmentManager(),"time picker");

            }
        });
    }

    public boolean ValidateNoteBtn(){

        if(TextUtils.isEmpty(resonetxt.getEditableText().toString())){
            resonetxt.setError("Put note for user");
            return false;
        }
        return true;
    }

    public boolean ValidateSetTime(){
        return !TextUtils.isEmpty(time.getText().toString());
    }


    public  void SetDataToView(){
        Resone_.setText(Resone);

        Emp_id_.setText(Emp_No);
        empname_.setText(Emp_Name);

    }

    public  void  AceeptRequest(String employyenumber,Integer Requestnumber,String Action,String Note,String Time){

        progressDialog.setMessage("Processing .... ");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        Request request = new Request();

        if( Time !=null && !Time.isEmpty()){
            request.setId(Requestnumber);
            request.setUserID(employyenumber);
            request.setAction(Action);
            request.setNote(Note);
            request.setSetTime(Integer.parseInt(Time));
        }
        else {
            request.setId(Requestnumber);
            request.setUserID(employyenumber);
            request.setAction(Action);
            request.setNote(Note);
            request.setSetTime(null);
        }



        AdminInterface adminInterface = Config.getRetrofitInstance().create(AdminInterface.class);
        Call<Request> call = adminInterface.AproveAction("Bearer"+token,request);
        call.enqueue(new Callback<Request>() {
            @Override
            public void onResponse(Call<Request> call, Response<Request> response) {

                if(response.isSuccessful()){
                    progressDialog.dismiss();
                    Done done = new Done();
                    done.show(getSupportFragmentManager(), "my_dialog");
                }
                else {
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<Request> call, Throwable t) {
                progressDialog.dismiss();
                Done done = new Done();
                done.show(getSupportFragmentManager(), "my_dialog");
            }
        });

    }

    public void RejectRequest(String employyenumber,Integer Requestnumber,String Action,String Note){

        progressDialog.setMessage("Processing..");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        Request request = new Request();
        request.setId(Requestnumber);
        request.setUserID(employyenumber);
        request.setAction(Action);
        request.setNote(Note);

        AdminInterface adminInterface = Config.getRetrofitInstance().create(AdminInterface.class);
        Call<Request> call = adminInterface.RejectAction("Bearer"+token,request);
        call.enqueue(new Callback<Request>() {
            @Override
            public void onResponse(Call<Request> call, Response<Request> response) {

                if(response.isSuccessful()){
                    progressDialog.dismiss();
                    Done done = new Done();
                    done.show(getSupportFragmentManager(), "my_dialog");
                }
                else {
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<Request> call, Throwable t) {
                progressDialog.dismiss();

                Done done = new Done();
                done.show(getSupportFragmentManager(), "my_dialog");
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent =  new Intent(AcceptRequest.this,AdminDashboard.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent =  new Intent(AcceptRequest.this,AdminDashboard.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
        time.setText("Hour : "+hourOfDay+"  minute : "+minute);
        int hour,hourtosec,minuts;

        hour=hourOfDay*60; // hours to minnuts
        hourtosec=hour*60;//hour minuts to second

        minuts=minute*60;// minuts to second

        totalsecond=hourtosec+minuts;

    }
}

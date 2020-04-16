package com.example.tfxi.pingpong;

import AdapterClass.DivisionAdapater;
import ApiInterface.AdminInterface;
import Config.Config;
import Model.Division;
import Model.DivistionList;
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
public class ActiveDevision extends AppCompatActivity {

    private androidx.appcompat.widget.Toolbar toolbar;
    private ListView listView;
    private ProgressDialog progressDialog;
    UserPref userPref;
    private String SessionEmployeeNumber,token,SessionName;
    private List<Division> divisions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_devision);

        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        listView =(ListView) findViewById(R.id.divisionband);
        progressDialog = new ProgressDialog(this,R.style.MyAlertDialogStyle);

        userPref =new UserPref((getApplicationContext()));
        token = userPref.CheckToken();

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Division active");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LoadingDevisionData();
    }

    public  void LoadingDevisionData(){

        progressDialog.setMessage("Load Details..");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        AdminInterface adminInterface = Config.getRetrofitInstance().create(AdminInterface.class);
        Call<Division> call = adminInterface.ActiveDevisionData("Bearer"+token);
        call.enqueue(new Callback<Division>() {
            @Override
            public void onResponse(Call<Division> call, Response<Division> response) {
                int errorCode = response.code();
                divisions =response.body().getDivision();

                if(response.isSuccessful()){
                    if(divisions.isEmpty()){
                        Toast.makeText(getApplicationContext(),"No any data",Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                    ShowDivisionList();
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
            public void onFailure(Call<Division> call, Throwable t) {

                progressDialog.dismiss();
                ServerError serverError = new ServerError();
                serverError.show(getSupportFragmentManager(), "my_dialog");
            }
        });
    }


    public  void ShowDivisionList(){
        ArrayList<DivistionList> divistionListArrayList = new ArrayList<>();
        for (int i = 0; i < divisions.size(); i++) {
            divistionListArrayList.add(new DivistionList(divisions.get(i).getDevisionName()));
        }
        DivisionAdapater divisionAdapater = new DivisionAdapater(this,divistionListArrayList);
        divisionAdapater.notifyDataSetChanged();
        listView.setAdapter(divisionAdapater);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                Division division = divisions.get(position);
                Active(division.getId());
            }
        });
    }

    public void Active (Integer devisionid){

        progressDialog.setMessage("Active division..");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        Division division = new Division();
        division.setId(devisionid);

        AdminInterface adminInterface = Config.getRetrofitInstance().create(AdminInterface.class);
        Call<Division> call = adminInterface.ActiveDevision("Bearer"+token,division);
        call.enqueue(new Callback<Division>() {
            @Override
            public void onResponse(Call<Division> call, Response<Division> response) {


                if(response.isSuccessful()){
                    progressDialog.dismiss();

                    DivisionActiveteDone done = new DivisionActiveteDone();
                    done.show(getSupportFragmentManager(), "my_dialog");



                }
                else {

                    progressDialog.dismiss();

                }
            }

            @Override
            public void onFailure(Call<Division> call, Throwable t) {
                Log.e("fdfdbhfd",t.getMessage());
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
            Intent intent =  new Intent(ActiveDevision.this,AdminDashboard.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent =  new Intent(ActiveDevision.this,AdminDashboard.class);
        startActivity(intent);
        finish();
    }
}

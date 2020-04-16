package com.example.tfxi.pingpong;

import ApiInterface.UserInterface;
import Config.Config;
import Model.ApiError;
import Model.DevisionBand;
import Model.User;
import SharedPref.UserPref;
import Utils.ErrorUtils;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import Error.*;


public class MainActivity extends AppCompatActivity {
    ImageView regbtn;
    EditText emp_no,password;
    UserPref userPref;
    private ProgressDialog progressDialog;
    private ApiError apiError = new ApiError();
    Button loginbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        regbtn= (ImageView) findViewById(R.id.regbtn);
        loginbtn = (Button) findViewById(R.id.login);
        emp_no = (EditText) findViewById(R.id.emp_no2);
        password = (EditText) findViewById(R.id.password2);
        progressDialog = new ProgressDialog(this,R.style.MyAlertDialogStyle);
        userPref = new UserPref(getApplicationContext());

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!ValidateEmpNo() | !Validatepassword()){
                    return;
                }
                else {
                    String empno,password_;
                    empno = emp_no.getEditableText().toString();
                    password_ =password.getEditableText().toString();
                    GetJwtToken(empno,password_);
                }
            }
        });


        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    public boolean ValidateEmpNo(){
        if(TextUtils.isEmpty(emp_no.getEditableText().toString())){
            emp_no.setError("Employee Number field cannot be blank");
            return  false;
        }
        else {
            return true;
        }
    }

    public boolean Validatepassword(){
        if(TextUtils.isEmpty(password.getEditableText().toString())){
            password.setError("Password field cannot be blank");
            return  false;
        }
        else
        {
            return true;
        }
    }

    public void GetJwtToken(String EmpNumber,String Password){

        progressDialog.setMessage("Checking login credentials");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        final User user = new User();
        user.setEmpoyee_no(EmpNumber);
        user.setPassword(Password);
        user.setUuID(GenerateuuID());

        UserInterface userInterface = Config.getRetrofitInstance().create(UserInterface.class);
        Call<User> call = userInterface.GetjwtToken(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if(response.isSuccessful()){
                    progressDialog.dismiss();
                    CreatUserSession(response.body().getToken());
                }

                else {
                    apiError = ErrorUtils.parseError(response);
                    String ErrorMessage = apiError.message();

                    if ("Admin has banded You".equals(ErrorMessage)) {
                        progressDialog.dismiss();
                        AdminBanded adminBanded = new AdminBanded();
                        adminBanded.show(getSupportFragmentManager(), "my_dialog");
                    }
                    else if("TOKEN_EXPIRED".equals(ErrorMessage)){
                        progressDialog.dismiss();
                        TokenExpierdError adminBanded = new TokenExpierdError();
                        adminBanded.show(getSupportFragmentManager(), "my_dialog");
                    }
                    else if("Your devision cannot allow the".equals(ErrorMessage)){
                        progressDialog.dismiss();
                        DevisionBand devisionBand = new DevisionBand();
                        devisionBand.show(getSupportFragmentManager(), "my_dialog");

                    }

                    else {
                        progressDialog.dismiss();
                        UnauthorizedError unauthorizedError = new UnauthorizedError();
                        unauthorizedError.show(getSupportFragmentManager(), "my_dialog");
                    }
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                progressDialog.dismiss();
                ServerError serverError = new ServerError();
                serverError.show(getSupportFragmentManager(), "my_dialog");
            }
        });
    }

    public void CreatUserSession(final String Token){

        progressDialog.setMessage("Creating User Session");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        UserInterface userInterface =Config.getRetrofitInstance().create(UserInterface.class);
        Call<User> call = userInterface.GetUserDetails("Bearer" +Token);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                String UserType = response.body().getUser().getUser_type();

                String UserStatus = response.body().getUser().getStatus();
                if(response.isSuccessful()){

                    if (UserStatus.equals("No")){
                        progressDialog.dismiss();
                        UserNotAcceptError userNotAcceptError = new UserNotAcceptError();
                        userNotAcceptError.show(getSupportFragmentManager(), "my_dialog");
                    }
                    else {
                        userPref.CreateUserSEssion(response.body().getUser().getName(),response.body().getUser().getEmpoyee_no());
                        progressDialog.dismiss();

                        if (UserType.equals("Admin")){
                            userPref.CreateAcesstoken(Token);
                            Intent intent =  new Intent(MainActivity.this,FinguerprintAuth.class);
                            intent.putExtra("UserType",UserType);
                            startActivity(intent);
                            finish();


                        }else if(UserType.equals("User")){
                            userPref.CreateAcesstoken(Token);
                            Intent intent =  new Intent(MainActivity.this,FinguerprintAuth.class);
                            intent.putExtra("UserType",UserType);
                            startActivity(intent);
                            finish();

                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Something is wrong",Toast.LENGTH_SHORT).show();
                        }
                    }

                }

                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                progressDialog.dismiss();
                ServerError serverError = new ServerError();
                serverError.show(getSupportFragmentManager(), "my_dialog");
            }
        });
    }

    private String GenerateuuID(){
        return Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

}

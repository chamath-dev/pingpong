package com.example.tfxi.pingpong;

import ApiInterface.UserInterface;
import Config.Config;
import Model.ApiError;
import Model.User;
import SharedPref.UserPref;
import Utils.ErrorUtils;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.print.PrinterId;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.onesignal.OneSignal;

import Error.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");

    EditText nametxt,emp_no,email,password,conformpassword;
    Button Regbtn;
    private ProgressDialog progressDialog;
    public ApiError apiError = new ApiError();
    private Spinner spinner;
    private List<String> Devision;
    private TextView textView39;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nametxt = (EditText) findViewById(R.id.nametxt);
        emp_no = (EditText) findViewById(R.id.emp_no);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        conformpassword = (EditText) findViewById(R.id.conformpassword);
        spinner =(Spinner) findViewById(R.id.spinner);
        textView39 = (TextView)findViewById(R.id.textView39);

        textView39.setVisibility(View.GONE);

        Regbtn = (Button) findViewById(R.id.Regbtn);

        progressDialog = new ProgressDialog(this,R.style.MyAlertDialogStyle);



        Regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConformValidation();
            }
        });
        LoadSpringValue();
    }

    private void LoadSpringValue(){

        Devision = new ArrayList<String>();

        Devision.add("Select Division");
        Devision.add("IT Division");
        Devision.add("Cleaning Division");


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner,Devision);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);



    }


    private boolean ServiceTerminalValidation(){
        String selectedTerminal = (String) spinner.getSelectedItem();
        if(selectedTerminal.equals("Select Devision")){
            textView39.setVisibility(View.VISIBLE);
            textView39.setText("Pleaqse select devision");
            return  false;
        }
        else{
            textView39.setVisibility(View.GONE);
            return true;
        }
    }

            private boolean CheckName(){
               if(!TextUtils.isEmpty(nametxt.getEditableText())){
                   return true;
               }
               else {
                   nametxt.setError("Name Field Cannot be empty");
                   return false;
               }
            }

            private boolean ValidateEmailAddress(){
                if(TextUtils.isEmpty(email.getEditableText())){
                    email.setError("Email Address Field cannot be empty");
                    return false;
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(email.getEditableText()).matches()){
                    email.setError("Please Enter Valid Email Address");
                    return false;
                }
                else {
                    return  true;
                }
            }

            private boolean VliadtePassword(){
                if(TextUtils.isEmpty(password.getEditableText())){
                    password.setError("Password Field Can not Be Blank");
                    return false;
                }
                else if(!PASSWORD_PATTERN.matcher(password.getEditableText()).matches()){
                    password.setError("Password is too weak");
                    return false;
                }
                else {
                    return true;
                }
            }

            private boolean ValidateConformPssword(){
                   if(TextUtils.isEmpty(conformpassword.getEditableText().toString())){
                       conformpassword.setError("Conform Password Field Can not Be Empty");
                       return false;
                   }
                   else if(!password.getEditableText().toString().equals(conformpassword.getEditableText().toString())){
                       conformpassword.setError("Passwords Not Match");
                       return false;
                   }
                   else {
                       return true;
                   }
             }

             private  boolean ValidateEmplNumber(){
        if(TextUtils.isEmpty(emp_no.getEditableText().toString())){
            emp_no.setError("Employyee number field Cannot be null");
            return false;
        }
        else {
            return true;
        }

             }

            public void ConformValidation(){
                if(!CheckName() | !ValidateEmailAddress() | !VliadtePassword() | !ValidateConformPssword() | !ValidateEmplNumber() | !ServiceTerminalValidation()){
                    return;
                }
                String name,empno,uuid,email_,password_,devision_;
                name =  nametxt.getEditableText().toString();
                empno =emp_no.getEditableText().toString();
                uuid =GenerateuuID();
                email_ = email.getEditableText().toString();
                password_ = password.getEditableText().toString();
                devision_ = spinner.getSelectedItem().toString();

                Log.e("fuctiong devision",devision_);

                RegUser(name,empno,uuid,email_,password_,devision_);

             }


             public void RegUser(String Name,String EmployeeNo,String UUID,String Email,String Password,String Devision){

                 progressDialog.setMessage("Register New User");
                 progressDialog.show();
                 progressDialog.setCancelable(false);
                 progressDialog.setCanceledOnTouchOutside(false);

                 User user = new User();
                 user.setName(Name);
                 user.setEmpoyee_no(EmployeeNo);
                 user.setUuID(UUID);
                 user.setEmail(Email);
                 user.setUser_devision(Devision);
                 user.setPassword(Password);
                 user.setNotification_id(OneSignal.getPermissionSubscriptionState().getSubscriptionStatus().getUserId());



                 UserInterface userInterface = Config.getRetrofitInstance().create(UserInterface.class);
                 Call<User> call = userInterface.UserReg(user);
                 call.enqueue(new Callback<User>() {
                     @Override
                     public void onResponse(Call<User> call, Response<User> response) {
                         if(response.isSuccessful()){
                             Log.e("done","done");
                             progressDialog.dismiss();

                             UserPref userPref = new UserPref(getApplicationContext());
                             userPref.CreateRegSession();

                             Intent intent = new Intent(RegisterActivity.this,AfterReg.class);
                             startActivity(intent);
                             finish();
                         }
                         else {

                             apiError = ErrorUtils.parseError(response);
                             String ErrorMessage = apiError.message();

                             if ("Problem".equals(ErrorMessage)) {
                                 progressDialog.dismiss();
                                 Popup popup = new Popup();
                                 popup.show(getSupportFragmentManager(), "my_dialog");
                             } else {
                                 progressDialog.dismiss();
                                 Log.e("default", "de");
                             }
                         }

                     }

                     @Override
                     public void onFailure(Call<User> call, Throwable t) {

                         Log.e("vdjbfkjdf",t.getMessage());
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

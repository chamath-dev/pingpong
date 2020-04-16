package SharedPref;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.tfxi.pingpong.SplashActivity;

import java.util.HashMap;

public class UserPref {

    public static final String UserRegSession = "UserLoginSessionPref";
    int private_mode =0;
    public static final String isReg ="IsReg";
    public static final String AcessToken ="AcessToken";
    public static final String UserNAME = "UserNAME";
    public static final String EmpNo = "EmpNo";

    public static final String time = "time";
    public static final String Savetime = "Savetime";

    SharedPreferences UserReg;
    SharedPreferences.Editor UserRegEditore;
    Context context;


    public UserPref(Context context) {
        this.context = context;
        UserReg = context.getSharedPreferences(UserRegSession,private_mode);
        UserRegEditore =UserReg.edit();
    }

    public void CreateRegSession(){
        UserRegEditore.putBoolean(isReg,true);
        UserRegEditore.commit();
    }

    public void CreateAcesstoken(String Token){
        UserRegEditore.putString(AcessToken,Token);
        UserRegEditore.commit();
    }
    public void CreateTime(int timeSec){
        UserRegEditore.putInt(time,timeSec);
        UserRegEditore.commit();
    }

    public void SaveSetTime(int timeSec){
        UserRegEditore.putInt(Savetime,timeSec);
        UserRegEditore.commit();
    }

    public int getTime(){

        return UserReg.getInt(time,0);
    }


    public int getSaveTime(){

        return UserReg.getInt(Savetime,0);
    }

    public void IsUserAcess(){
        UserRegEditore.putString("IsUserAccess","false");
        UserRegEditore.commit();
    }

    public void Logout(){
        SharedPreferences settings = context.getSharedPreferences(UserRegSession, Context.MODE_PRIVATE);
        settings.edit().clear().apply();
        UserRegEditore.clear();
        UserRegEditore.commit();



        Intent intent = new Intent(context, SplashActivity.class);
        intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }




    public void CreateUserSEssion(String UserName,String EmpNo_){
        UserRegEditore.putString(UserNAME,UserName);
        UserRegEditore.putString(EmpNo,EmpNo_);
        UserRegEditore.commit();
    }

    public boolean CheckIsUserReg(){
        if(!this.isRegister()){
            return false;
        }
        else {
            return true;
        }
    }

    private boolean isRegister(){
        return UserReg.getBoolean(isReg,false);
    }



    public  String CheckToken(){
        String token = UserReg.getString(AcessToken,null);

        if(token == null){
            return  null;
        }
        else {
            return token;
        }
    }


    public HashMap<String,String> GetUserSessionData(){
        HashMap<String,String> User = new HashMap<>();
        User.put(UserNAME,UserReg.getString(UserNAME,null));
        User.put(EmpNo,UserReg.getString(EmpNo,null));
        return User;
    }




}

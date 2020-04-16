package Error;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.tfxi.pingpong.AdminDashboard;
import com.example.tfxi.pingpong.R;
import com.example.tfxi.pingpong.UserDashBoard;

import SharedPref.UserPref;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class Note  extends DialogFragment {
    TextView notetxt,timetxt;
    UserPref userPref;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.note,null);
        notetxt = (TextView)view.findViewById(R.id.notetxt);
        timetxt = (TextView)view.findViewById(R.id.timetxt);

        Bundle bundle = getArguments();
        String note = bundle.getString("note","");
        Integer time = bundle.getInt("time",0);

        if( time !=null && !time.equals(0)){
            Log.e("fragment  notempty"," not empry"+time);

            notetxt = (TextView)view.findViewById(R.id.notetxt);
            notetxt.setText(note);
            timetxt.setVisibility(View.VISIBLE);
            timetxt.setText("you must complete this task with in "+converToTime(String.valueOf(time)));
        }
        else{

            Log.e("fragment empty","empry");
            notetxt = (TextView)view.findViewById(R.id.notetxt);
            notetxt.setText(note);
            timetxt.setVisibility(View.GONE);

        }


        final AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());


        builder.setView(view);
        builder.setTitle("Request Note");


        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
              dismiss();


            }
        });
        AlertDialog dialog = builder.create();
        return dialog;
    }

    private String converToTime(String Time){
        userPref = new UserPref(getActivity());
        userPref.SaveSetTime(Integer.parseInt(Time));

        int timeInt=Integer.parseInt(Time);
        int Hours =timeInt / 3600;
        Log.e("hours",String.valueOf(Hours));

        int Minuts = timeInt % 3600;
        Log.e("Minuts",String.valueOf(Minuts));

        int ActualMinuts=Minuts/60;
        Log.e("ActualMinuts",String.valueOf(ActualMinuts));




        return "Hour :"+Hours+" : "+"Minuts :"+ActualMinuts;
    }
}
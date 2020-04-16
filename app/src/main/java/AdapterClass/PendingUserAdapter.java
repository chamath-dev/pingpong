package AdapterClass;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tfxi.pingpong.R;

import java.util.ArrayList;

import Model.NotificationList;
import Model.PendingUserList;
import androidx.cardview.widget.CardView;

public class PendingUserAdapter extends BaseAdapter {
    Context context;
    ArrayList<PendingUserList> notificationData;

    public  PendingUserAdapter(Context context,ArrayList<PendingUserList> notificationData){
        this.context = context;
        this.notificationData = notificationData;
    }


    @Override
    public int getCount() {
        return notificationData.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView= LayoutInflater.from(context).inflate(R.layout.pendinguserlist,parent,false);
        }

        String banType= notificationData.get(position).getBan_Type();
        TextView Title = (TextView) convertView.findViewById(R.id.Username);
        TextView Body = (TextView) convertView.findViewById(R.id.UserID);
        TextView devisiontxt = (TextView) convertView.findViewById(R.id.devisiontxt);
       CardView cardbody = (CardView)convertView.findViewById(R.id.cardbody);




        if( banType !=null && !banType.isEmpty()){


            cardbody.setCardBackgroundColor(Color.parseColor("#EF5350"));

            Title.setText(notificationData.get(position).getUsername());
            Body.setText(notificationData.get(position).getUserID());
            devisiontxt.setText(notificationData.get(position).getDevision());

        }
        else {
            cardbody.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
            Title.setText(notificationData.get(position).getUsername());
            Body.setText(notificationData.get(position).getUserID());
            devisiontxt.setText(notificationData.get(position).getDevision());
        }








        return convertView;
    }
}

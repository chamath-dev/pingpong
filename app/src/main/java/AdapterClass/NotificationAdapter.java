package AdapterClass;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tfxi.pingpong.R;

import java.util.ArrayList;

import Model.NotificationList;
import androidx.cardview.widget.CardView;

public class NotificationAdapter extends BaseAdapter {
    Context context;
    ArrayList<NotificationList> notificationData;

    public  NotificationAdapter(Context context,ArrayList<NotificationList> notificationData){
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
            convertView= LayoutInflater.from(context).inflate(R.layout.notificationview,parent,false);
        }

        TextView Title = (TextView) convertView.findViewById(R.id.NewsHeading);
        TextView Body = (TextView) convertView.findViewById(R.id.descriptiontxt);
        CardView cardview = (CardView) convertView.findViewById(R.id.cardview);
        String Satus = notificationData.get(position).getStatus();

        if (Satus.equals("No")){
            cardview.setCardBackgroundColor(R.color.md_white_1000);
        }
        Title.setText(notificationData.get(position).getTitle());
        Body.setText(notificationData.get(position).getBody());






        return convertView;
    }
}

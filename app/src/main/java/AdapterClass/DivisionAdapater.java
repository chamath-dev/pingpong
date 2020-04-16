package AdapterClass;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tfxi.pingpong.R;

import java.util.ArrayList;

import Model.DivistionList;
import Model.NotificationList;
import androidx.cardview.widget.CardView;

public class DivisionAdapater  extends BaseAdapter {
    Context context;
    ArrayList<DivistionList> notificationData;

    public  DivisionAdapater(Context context,ArrayList<DivistionList> notificationData){
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
            convertView= LayoutInflater.from(context).inflate(R.layout.devisionlist,parent,false);
        }

        TextView Title = (TextView) convertView.findViewById(R.id.devisionName);



        Title.setText(notificationData.get(position).getDevisionName());







        return convertView;
    }
}

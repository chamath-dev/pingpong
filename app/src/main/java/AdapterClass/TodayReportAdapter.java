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

import Model.TodayList;
import Model.WorkingList;
import androidx.cardview.widget.CardView;

public class TodayReportAdapter extends BaseAdapter {
    Context context;
    ArrayList<TodayList> todayLists;

    public  TodayReportAdapter(Context context,ArrayList<TodayList> todayListArrayList){
        this.context = context;
        this.todayLists = todayListArrayList;
    }


    @Override
    public int getCount() {
        return todayLists.size();
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
            convertView= LayoutInflater.from(context).inflate(R.layout.todaylist,parent,false);
        }

        CardView cardView = (CardView) convertView.findViewById(R.id.todaylistview) ;
        TextView empname = (TextView) convertView.findViewById(R.id.empname);
        TextView empid = (TextView) convertView.findViewById(R.id.empid);
        TextView resone = (TextView) convertView.findViewById(R.id.resone);
         TextView status = (TextView) convertView.findViewById(R.id.status);

         String Status= todayLists.get(position).getStatus();
        Log.e("Dfdfdd",Status);
        switch (Status) {
            case "Pending":
                cardView.setCardBackgroundColor(Color.parseColor("#D4E157"));
                empname.setText(todayLists.get(position).getUserNane());
                empid.setText(todayLists.get(position).getUserID());

                resone.setText(todayLists.get(position).getResone());
                status.setText(todayLists.get(position).getStatus());

                break;
            case "Approve":
                cardView.setCardBackgroundColor(Color.parseColor("#66BB6A"));
                empname.setText(todayLists.get(position).getUserNane());
                empid.setText(todayLists.get(position).getUserID());

                resone.setText(todayLists.get(position).getResone());
                status.setText(todayLists.get(position).getStatus());

                break;
            case "Reject":
                cardView.setCardBackgroundColor(Color.parseColor("#EF5350"));
                empname.setText(todayLists.get(position).getUserNane());
                empid.setText(todayLists.get(position).getUserID());

                resone.setText(todayLists.get(position).getResone());
                status.setText(todayLists.get(position).getStatus());

                break;
            case "Cancel":
                cardView.setCardBackgroundColor(Color.parseColor("#FF7043"));
                empname.setText(todayLists.get(position).getUserNane());
                empid.setText(todayLists.get(position).getUserID());

                resone.setText(todayLists.get(position).getResone());
                status.setText(todayLists.get(position).getStatus());

                break;
            case "Done":
                cardView.setCardBackgroundColor(Color.parseColor("#FFA726"));
                empname.setText(todayLists.get(position).getUserNane());
                empid.setText(todayLists.get(position).getUserID());

                resone.setText(todayLists.get(position).getResone());
                status.setText(todayLists.get(position).getStatus());

                break;
            case "Finished":
                cardView.setCardBackgroundColor(Color.parseColor("#26A69A"));
                empname.setText(todayLists.get(position).getUserNane());
                empid.setText(todayLists.get(position).getUserID());

                resone.setText(todayLists.get(position).getResone());
                status.setText(todayLists.get(position).getStatus());

                break;

            case "Working":
                cardView.setCardBackgroundColor(Color.parseColor("#A4B42B"));
                empname.setText(todayLists.get(position).getUserNane());
                empid.setText(todayLists.get(position).getUserID());

                resone.setText(todayLists.get(position).getResone());
                status.setText(todayLists.get(position).getStatus());

                break;
            default:


                break;
        }



        return convertView;
    }
}
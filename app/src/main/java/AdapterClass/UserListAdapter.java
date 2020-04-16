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
import Model.UserReqest;
import androidx.cardview.widget.CardView;

public class UserListAdapter  extends BaseAdapter {
    Context context;
    ArrayList<UserReqest> todayLists;

    public UserListAdapter(Context context, ArrayList<UserReqest> todayListArrayList) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.userrequestlist, parent, false);
        }

        CardView cardView = (CardView) convertView.findViewById(R.id.requset);
        TextView resone = (TextView) convertView.findViewById(R.id.resone);
        TextView status = (TextView) convertView.findViewById(R.id.status);

        String Status = todayLists.get(position).getStatus();
        Log.e("Dfdfdd", Status);
        switch (Status) {
            case "Pending":
                cardView.setCardBackgroundColor(Color.parseColor("#D4E157"));


                resone.setText(todayLists.get(position).getResone());
                status.setText(todayLists.get(position).getStatus());

                break;
            case "Approve":
                cardView.setCardBackgroundColor(Color.parseColor("#66BB6A"));


                resone.setText(todayLists.get(position).getResone());
                status.setText(todayLists.get(position).getStatus());

                break;
            case "Reject":
                cardView.setCardBackgroundColor(Color.parseColor("#EF5350"));


                resone.setText(todayLists.get(position).getResone());
                status.setText(todayLists.get(position).getStatus());

                break;
            case "Cancel":
                cardView.setCardBackgroundColor(Color.parseColor("#FF7043"));


                resone.setText(todayLists.get(position).getResone());
                status.setText(todayLists.get(position).getStatus());

                break;
            case "Done":
                cardView.setCardBackgroundColor(Color.parseColor("#FFA726"));

                resone.setText(todayLists.get(position).getResone());
                status.setText(todayLists.get(position).getStatus());

                break;
            case "Finished":
                cardView.setCardBackgroundColor(Color.parseColor("#26A69A"));


                resone.setText(todayLists.get(position).getResone());
                status.setText(todayLists.get(position).getStatus());

                break;
            default:


                break;
        }


        return convertView;
    }


}
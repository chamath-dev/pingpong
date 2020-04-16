package AdapterClass;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tfxi.pingpong.R;

import java.util.ArrayList;

import Model.WorkingList;

public class PendingRequestAdapter  extends BaseAdapter {
    Context context;
    ArrayList<WorkingList> workingLists;

    public  PendingRequestAdapter(Context context,ArrayList<WorkingList> workingListArrayList){
        this.context = context;
        this.workingLists = workingListArrayList;
    }


    @Override
    public int getCount() {
        return workingLists.size();
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
            convertView= LayoutInflater.from(context).inflate(R.layout.workinguserlist,parent,false);
        }

        TextView empname = (TextView) convertView.findViewById(R.id.Empname);
        TextView empid = (TextView) convertView.findViewById(R.id.empID);
        TextView resone = (TextView) convertView.findViewById(R.id.resone);
        TextView divisiontxt = (TextView) convertView.findViewById(R.id.divisiontxt);



        empname.setText(workingLists.get(position).getEmpname());

        empid.setText(workingLists.get(position).getEmpid());

        resone.setText(workingLists.get(position).getResone());
        divisiontxt.setText(workingLists.get(position).getDivision());







        return convertView;
    }
}
package com.example.loginregister.adapters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.loginregister.R;
import com.example.loginregister.model.ServiceDetails;

import java.util.ArrayList;

public class ProfileListAdapter extends ArrayAdapter<ServiceDetails> implements View.OnClickListener{

    private ArrayList<ServiceDetails> dataSet;
    Context mContext;

    private static class ViewHolder{
        TextView txtName;
        ImageView num;
    }

    public ProfileListAdapter(ArrayList<ServiceDetails> dataSet,Context mContext){
        super(mContext, R.layout.profile_row_item,dataSet);
        this.dataSet=dataSet;
        this.mContext=mContext;
    }


@Override
    public void onClick(View v) {

        int position = (Integer) v.getTag();
        Object object = getItem(position);
        ServiceDetails serviceDetails = (ServiceDetails)object;
        switch(v.getId()){
            case R.id.item_name:
                Snackbar.make(v,serviceDetails.getName(),Snackbar.LENGTH_LONG).setAction("No Action",null).show();
                serviceDetails.setStatus(1, BitmapFactory.decodeResource(mContext.getResources(),R.drawable.check));
                Log.i("adap","ke ander");
                notifyDataSetChanged();
                break;
        }
    }

    private int lastPosition = -1;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ServiceDetails serviceDetails = getItem(position);
        ViewHolder viewHolder;
        final View result;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.profile_row_item,parent,false);
            viewHolder.txtName=(TextView)convertView.findViewById(R.id.item_name);
            viewHolder.num=(ImageView)convertView.findViewById(R.id.item_no);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }
        if(serviceDetails.getStatus()==0){
            viewHolder.txtName.setTextColor(Color.BLACK);
        } else {
            viewHolder.txtName.setTextColor(Color.GRAY);
        }
        viewHolder.txtName.setText(serviceDetails.getName());
        viewHolder.num.setImageBitmap(serviceDetails.getNumbering());
        return convertView;
    }
}

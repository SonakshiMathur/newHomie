package com.example.loginregister.activities;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Filter;
import android.widget.Toast;

import com.example.loginregister.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class ManageCategoryFilter extends Filter {
    HashMap<String, List<String>> lItems;
    Context context;
    ExpandableListView expandableListView;
    ManageCategoryFilter(Context c){
        super();
        context =c;
        lItems=ExpandableListDataPump.getData();
        Log.i("fffff",lItems.toString());
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        // NOTE: this function is *always* called from a background thread, and
        // not the UI thread.

        //constraint = edit.getText().toString().toLowerCase();
        FilterResults result = new FilterResults();
        List<String> filt=new ArrayList<>();
        if(constraint != null && constraint.toString().length() > 0) {
            for(int i = 0, l = lItems.size(); i < l; i++) {
                for (int j = 0; j < (lItems.get((String)(lItems.keySet().toArray()[i]))).size(); j++) {
                    if ((lItems.get((String)(lItems.keySet().toArray()[i])).get(j)).toLowerCase().contains(constraint))

                        filt.add(lItems.get((String)(lItems.keySet().toArray()[i])).get(j));
                }
            }
            result.count = filt.size();
            result.values = filt;
        } else {
            for(int i = 0, l = lItems.size(); i < l; i++) {
                for (int j = 0; j < lItems.get((String)(lItems.keySet().toArray()[i])).size(); j++) {

                    filt.add(lItems.get((String)(lItems.keySet().toArray()[i])).get(j));
                }
            }
            synchronized(this) {
                result.count = filt.size();
                result.values = filt;
            }
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void publishResults(CharSequence constraint, FilterResults result) {
        // NOTE: this function is *always* called from the UI thread.
        Log.i("result values",result.values.toString());
        ArrayList<String>filtered = (ArrayList<String>)result.values;
        HashMap<String, List<String>> updatedItems=new HashMap<>();


        for(int i = 0; i < lItems.size(); i++) {
            List<String> toPut=new ArrayList<>();
            for(int j = 0; j < (lItems.get((String)(lItems.keySet().toArray()[i]))).size(); j++) {

                for(int k=0; k<filtered.size(); k++){
                    if(lItems.get((String)(lItems.keySet().toArray()[i])).get(j).equals(filtered.get(k))){
                        toPut.add(lItems.get((String)(lItems.keySet().toArray()[i])).get(j));
                    }
                }

            }
            if(toPut.size()>0) {
                updatedItems.put((String) ((lItems.keySet().toArray())[i]), toPut);
            }
        }
        Log.i("updates",updatedItems.toString());
        CustomExpandableListAdapter adapt = new CustomExpandableListAdapter(context, new ArrayList<String>(updatedItems.keySet()),updatedItems);
        View rootView = ((Activity)context).getWindow().getDecorView().findViewById(android.R.id.content);
        ExpandableListView expandableListView=(ExpandableListView)rootView.findViewById(R.id.myVieww);
        expandableListView.setAdapter(adapt);


    }


}

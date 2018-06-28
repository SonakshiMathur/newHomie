package com.example.loginregister.activities;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginregister.R;
import com.example.loginregister.helpers.SharedPrefManager;
import com.example.loginregister.helpers.URLs;
import com.example.loginregister.model.ServiceDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;



public class ServiceExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<ServiceDetails> expandableListTitle;
    private HashMap<String, List<Bitmap>> expandableListDetail;
    private Fragment fContext;
    private int pos;
    private String group;

    ManageCategoryFilter filter;
    public ServiceExpandableListAdapter(Context context, List<ServiceDetails> expandableListTitle,
                                        HashMap<String, List<Bitmap>> expandableListDetail, Fragment fContext) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
        this.fContext = fContext;
    }
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    public static final String UPLOAD_URL = URLs.URL_IMAGE_UPLOAD;
    public static final String UPLOAD_KEY = "image";
    private void uploadImage(final String name, Bitmap bmp){
        class UploadImage extends AsyncTask<Bitmap,Void,String> {

            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();
            int cols;
            UploadImage(int cols){
                this.cols=cols;
            }
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(context, "Uploading Image", "Please wait...",true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
               final String SHARED_PREF_NAME = "sharedpref";
                 final String KEY_award = "award";
                 final String KEY_idproof = "idproof";
                final String KEY_references = "references";
                final String KEY_work = "work";
                final String KEY_ratechart = "ratechart";
                SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                JSONObject newd=new JSONObject();
                try {
                    JSONObject res = new JSONObject(s);
                     newd= res.getJSONObject("new_d");
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                try {  editor.putString(KEY_award, newd.getString("award"));
                    editor.putString(KEY_idproof, newd.getString("idproof"));
                    editor.putString(KEY_references, newd.getString("references"));
                    editor.putString(KEY_work, newd.getString("work"));
                    editor.putString(KEY_ratechart, newd.getString("ratechart"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                editor.apply();
                Toast.makeText(context,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Bitmap... params) {
                Bitmap bitmap = params[0];
                String uploadImage = getStringImage(bitmap);

                HashMap<String,String> data = new HashMap<>();
                data.put(UPLOAD_KEY, uploadImage);
                SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(context);
                data.put("id", Integer.toString(sharedPrefManager.User().getId()));
                data.put("award",sharedPrefManager.User().getAward());
                data.put("idproof",sharedPrefManager.User().getIdproof());
                data.put("references",sharedPrefManager.User().getReferences());
                data.put("work",sharedPrefManager.User().getWorking());
                data.put("ratechart",sharedPrefManager.User().getRatechart());

                String result = rh.sendPostRequest(UPLOAD_URL,data,cols);

                return result;
            }
        }
        int i=0;
        Log.i("name",name.toString());
        if(name.equals("Award and certificate photos")) {
            i = 1;
        }
        else if(name.equals("ID Proof Photos")) {
            i = 2;
        }
        else if(name.equals("Submit References")) {
            i = 3;
        }
        else if(name.equals("Previous work done")) {
            i = 4;
        }
        else i=5;

        UploadImage ui = new UploadImage(i);
        ui.execute(bmp);

    }




    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition).getName());
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(final int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final List<Bitmap> expandedListImgs = (List<Bitmap>) getChild(listPosition, expandedListPosition);
        Log.i("child","ke ander");
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.servicelistitem, null);
        }
        int size = expandedListImgs.size() + 1;
        LinearLayout mRecyclerView = (LinearLayout) convertView.findViewById(R.id.myprofdocs);
        mRecyclerView.removeAllViews();
        for(int i=0; i<size-1; i++){
            final ImageView img = new ImageView(context);
            img.setImageBitmap(expandedListImgs.get(i));
            final int j=i;
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                    int posi=j;
                    String name=expandableListTitle.get(listPosition).getName();
                    Bitmap bitmap = expandableListDetail.get(name).get(posi);
                    final Dialog nagDialog = new Dialog(fContext.getActivity(),android.R.style.Theme_Light_NoTitleBar_Fullscreen);
                    nagDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    nagDialog.setCancelable(false);
                    nagDialog.setContentView(R.layout.preview_image);
                    Button btnClose = (Button)nagDialog.findViewById(R.id.btnIvClose);
                    ImageView ivPreview = (ImageView)nagDialog.findViewById(R.id.iv_preview_image);
                    ivPreview.setImageBitmap(bitmap);

                    btnClose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View arg0) {

                            nagDialog.dismiss();
                        }
                    });
                    nagDialog.show();
                    //fContext.startActivityForResult(intent,3);
                }
            });
            mRecyclerView.addView(img,250,250);
        }
        ImageView add = new ImageView(context);
        add.setImageBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.adddocuments));
        final int lp=listPosition;
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                group=expandableListTitle.get(lp).getName();
                fContext.startActivityForResult(inte,2);
            }
        });
        mRecyclerView.addView(add,250,250);
        return convertView;
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    if(requestCode==2 && resultCode== Activity.RESULT_OK){
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            Bitmap resized=null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), selectedImage);
                resized= Bitmap.createScaledBitmap(bitmap, 265, 270, true);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            String name = group;
            expandableListDetail.get(name).add(bitmap);
            uploadImage(name,bitmap);
            notifyDataSetChanged();
        }

    }


    @Override
    public int getChildrenCount(int listPosition) {
        return 1;
    }
    private static class ViewHolder{
        TextView txtName;
        ImageView num;
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        ServiceDetails serviceDetails = (ServiceDetails)getGroup(listPosition);
        ServiceExpandableListAdapter.ViewHolder viewHolder;
        final View result;
        if(convertView == null){
            viewHolder = new ServiceExpandableListAdapter.ViewHolder();
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.servicegrouplist,parent,false);
            viewHolder.txtName=(TextView)convertView.findViewById(R.id.item_name);
            viewHolder.num=(ImageView)convertView.findViewById(R.id.item_no);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ServiceExpandableListAdapter.ViewHolder) convertView.getTag();
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

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return false;
    }

}

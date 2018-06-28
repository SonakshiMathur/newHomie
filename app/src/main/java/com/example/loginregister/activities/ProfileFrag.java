package com.example.loginregister.activities;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;

import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginregister.helpers.RequestHandler;
import com.example.loginregister.helpers.SharedPrefManager;
import com.example.loginregister.helpers.URLs;
import com.example.loginregister.model.SPDetails;


import com.example.loginregister.R;
import com.example.loginregister.model.ServiceDetails;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ProfileFrag extends Fragment {


    ExpandableListView listView;
    ImageButton imgButton;
    ArrayList<ServiceDetails> serviceDetails;
    HashMap<String,List<Bitmap>> imageLists;
    ServiceExpandableListAdapter adapter;
    ProgressDialog dialog;
    int waiting=1;


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        class UploadProImage extends AsyncTask<Bitmap,Void,String> {

            ProgressDialog loading;
            com.example.loginregister.activities.RequestHandler rh = new com.example.loginregister.activities.RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getContext(), "Uploading Image", "Please wait...",true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
            }

            @Override
            protected String doInBackground(Bitmap... params) {
                Bitmap bitmap = params[0];
                String uploadImage = getStringImage(bitmap);

                HashMap<String,String> data = new HashMap<>();
                data.put("propic", uploadImage);
                SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(getContext());
                data.put("id", Integer.toString(sharedPrefManager.User().getId()));


                String result = rh.sendPostRequest(URLs.URL_ProPic_UPLOAD,data,-1);

                return result;
            }
        }
        //Detects request codes
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            Bitmap resized=null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), selectedImage);
                resized= Bitmap.createScaledBitmap(bitmap, 265, 270, true);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            imgButton.setImageBitmap(resized);
            UploadProImage upp=new UploadProImage();
            upp.execute(resized);
        }
        else {
            adapter.onActivityResult(requestCode,resultCode,data);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.profile_service, container, false);
        if (getArguments() != null) {
        }
        SPDetails spDetails = SharedPrefManager.getInstance(getContext()).User();

        TextView name=(TextView)rootView.findViewById(R.id.name_of_user);
        imgButton=(ImageButton) rootView.findViewById(R.id.profilePicture);

        class GetProImage extends AsyncTask<String,Void,Void>{


            @Override
            protected Void doInBackground(String... params) {
                String id = params[0];

                URL url = null;
                //creating request handler object
                com.example.loginregister.helpers.RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params1 = new HashMap<>();
                params1.put("id", id);

                Bitmap image = null;
                try {
                    String ss = requestHandler.sendPostRequest(URLs.URL_ProPic_DOWNLOAD, params1);
                    JSONObject jsonObject = new JSONObject(ss);
                    String imagesss = jsonObject.getString("propic");
                    if (imagesss == null) {
                        return null;
                    } else {
                        byte[] decodedString = Base64.decode(imagesss, Base64.DEFAULT);
                        image = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                final Bitmap imagee=image;
                getActivity().runOnUiThread(new Runnable() {
                    @SuppressLint("ResourceType")
                    @Override
                    public void run() {
                        imgButton.setImageBitmap(imagee);

                        imgButton.setBackgroundColor(R.color.colorBg);
                    }
                });


              return null;

            }
        }

        GetProImage gp=new GetProImage();
        gp.execute(Integer.toString(SharedPrefManager.getInstance(getContext()).User().getId()));


        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI),1);
            }
        });




        listView = (ExpandableListView) rootView.findViewById(R.id.serviceList);
        serviceDetails = new ArrayList<>();
        serviceDetails.add(new ServiceDetails("Award and certificate photos", BitmapFactory.decodeResource(getContext().getResources(),R.drawable.one)));
        serviceDetails.add(new ServiceDetails("ID Proof Photos", BitmapFactory.decodeResource(getContext().getResources(),R.drawable.two)));
        serviceDetails.add(new ServiceDetails("Submit References", BitmapFactory.decodeResource(getContext().getResources(),R.drawable.three)));
        serviceDetails.add(new ServiceDetails("Previous work done", BitmapFactory.decodeResource(getContext().getResources(),R.drawable.four)));
        serviceDetails.add(new ServiceDetails("Rate Chart", BitmapFactory.decodeResource(getContext().getResources(),R.drawable.five)));


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ServiceDetails sd=serviceDetails.get(position);
                Snackbar.make(view,sd.getName(),Snackbar.LENGTH_LONG).setAction("No action",null).show();
                sd.setStatus(1, BitmapFactory.decodeResource(getContext().getResources(),R.drawable.check));
                Log.i("adap","ke ander");
                adapter.notifyDataSetChanged();
            }
        });
        getImagesFromDatabase();
        name.setText("Hello , "+spDetails.getName().toString());
        return rootView;
    }
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    public void getImagesFromDatabase(){
        final HashMap<String,List<Bitmap>> list=new HashMap<>();

        class RetImageIds extends AsyncTask<Void, Void, String> {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dialog = new ProgressDialog(getContext());
                dialog.setMessage("Retrieving Images...");
                dialog.show();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);


                class GetImage extends AsyncTask<String,Void,Bitmap>{


                    @Override
                    protected Bitmap doInBackground(String... params) {
                        String id = params[0];

                        URL url = null;
                        //creating request handler object
                        com.example.loginregister.helpers.RequestHandler requestHandler = new RequestHandler();

                        //creating request parameters
                        HashMap<String, String> params1 = new HashMap<>();
                        params1.put("id",id);

                        Bitmap image = null;
                        try {
                            String ss=requestHandler.sendPostRequest(URLs.URL_IMAGE_DIWNLOAD, params1);
                            Log.i("decode",ss);
                            JSONObject jsonObject=new JSONObject(ss);
                            String imagesss=jsonObject.getString("image");
                            if(imagesss==null){
                                return null;
                            }
                            else {
                                byte[] decodedString = Base64.decode(imagesss, Base64.DEFAULT);
                                image = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                                Log.i("mybmp",decodedString.toString());
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        return image;
                    }
                }
                try {

                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {


                        //getting the user from the response
                        JSONObject userJson = obj.getJSONObject("imgDetails");
                        String[] details=new String[5];
                        SPDetails spd = SharedPrefManager.getInstance(getContext()).User();
                        String award = userJson.getString("award");
                        spd.setAward(award);
                        details[0]=award.substring(1,award.length()-1);
                        String idproof = userJson.getString("idproof");
                        spd.setIdproof(idproof);
                        details[1]=idproof.substring(1,idproof.length()-1);
                        String references = userJson.getString("references");
                        spd.setReferences(references);
                        details[2]=references.substring(1,references.length()-1);
                        String work = userJson.getString("work");
                        spd.setWorking(work);
                        details[3]=work.substring(1,work.length()-1);
                        String ratechart = userJson.getString("ratechart");
                        spd.setRatechart(ratechart);
                        details[4]=ratechart.substring(1,ratechart.length()-1);

                        String[] names=new String[5];
                        names[0]="Award and certificate photos";
                        names[1]="ID Proof Photos";
                        names[2]="Submit References";
                        names[3]="Previous work done";
                        names[4]="Rate Chart";
                        //storing the user in shared preferences
                        SharedPrefManager.getInstance(getContext()).userLogin(spd);
                        for(int i=0; i<details.length; i++){
                            if(details[i].length()==0){
                                list.put(names[i],new ArrayList<Bitmap>());
                            }else{
                                String ids[] = details[i].split(",");
                                List<Bitmap>temp=new ArrayList<>();
                                for(int j=0; j<ids.length; j++){
                                    Log.i("idss",ids[j]);

                                    GetImage gi=new GetImage();
                                    Bitmap bmp=gi.execute(ids[j]).get();
                                    if(bmp!=null){
                                    temp.add(bmp);}

                                }
                                list.put(names[i],temp);
                            }
                        }

                    } else {
                        Toast.makeText(getContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                adapter=new ServiceExpandableListAdapter(getContext(),serviceDetails,list,ProfileFrag.this);
                listView.setAdapter(adapter);

                dialog.dismiss();
                waiting=0;
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                com.example.loginregister.helpers.RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("id",Integer.toString(SharedPrefManager.getInstance(getContext()).User().getId()));

                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_IMAGE_ID_DOWNLOAD, params);
            }
        }

        RetImageIds retImageIds = new RetImageIds();
        retImageIds.execute();


    }


}





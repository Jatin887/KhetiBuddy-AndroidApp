package com.agriculturalapp.activities;

import androidx.core.app.NavUtils;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.agriculturalapp.Api.RetrofitApi;
import com.agriculturalapp.R;
import com.agriculturalapp.adapters.SoilHealthCardAdapter;
import com.agriculturalapp.application.MyApplication;
import com.agriculturalapp.application.MySingleton;
import com.agriculturalapp.modals.PredictedResult;
import com.agriculturalapp.modals.VarietyRecommenderModel;
import com.agriculturalapp.utilities.Connection;
import com.agriculturalapp.utilities.SqliteHelper;
import com.agriculturalapp.utilities.urllib;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.POST;

public class CropVarietyRecommender extends AppCompatActivity {
    public static final String STATE = "state";
    public static final String DISTRICT = "district";
    public static final String CROP = "crop";
    private EditText nitrogen,phosphorous,ph,rainfall,potassium,fieldAreaEt;
    private SqliteHelper helper;
    private TextView result;
    private Spinner spinner1,spinner2;
    ArrayList<String> list1,list2;
    private Spinner  cropSeasonSpinner, cropYearSpinner,cropSoilSpinner;
    private Button btnSubmit;
    SoilHealthCardAdapter adapter;
    ArrayAdapter<String> arrayAdapter1,arrayAdapter2;
    RecyclerView recyclerView;
//    private static final String URL_DISTRICT = "http://kharita.freevar.com/district.php";
    private static final String TAG = "myTag";
    //private static final String URL_CROP = "http://kharita.freevar.com/crop.php";
    ArrayAdapter<String> arrayAdapter,  arrayAdapter3, arrayAdapter4,arrayAdapter5;
    ArrayList<String> distList,cropSeasonList, cropYearList,cropSoilList;
    ProgressBar p;
    private  RetrofitApi retrofitApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_variety_recommender);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://crop-recommendation-production.up.railway.app/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitApi = retrofit.create(RetrofitApi.class);
        nitrogen = findViewById(R.id.nitrogen);
        ph = findViewById(R.id.ph);
        phosphorous = findViewById(R.id.phosphorous);
        rainfall = findViewById(R.id.rainfall);
        potassium = findViewById(R.id.potassium);
        result = findViewById(R.id.result);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        fieldAreaEt = (EditText) findViewById(R.id.fieldAreaEt);
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        list1=new ArrayList<>();
        list2=new ArrayList<>();

        helper=new SqliteHelper(this);

        list1=helper.getDistinctStates();
        arrayAdapter1=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,list1);
        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(arrayAdapter1);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("positon1", "" + position);
                list2 = helper.getDistricts((String) spinner1.getItemAtPosition(position));
                arrayAdapter2 = new ArrayAdapter<String>(CropVarietyRecommender.this, android.R.layout.simple_spinner_dropdown_item, list2);
                arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(arrayAdapter2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        list2.add("Select District");

        arrayAdapter2=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,list2);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(arrayAdapter2);

        cropSeasonSpinner = (Spinner) findViewById(R.id.crop_Season_Spinner);
        cropYearSpinner = (Spinner) findViewById(R.id.crop_Year_Spinner);
        cropSoilSpinner = (Spinner) findViewById(R.id.crop_Soil_Spinner);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        p = (ProgressBar) findViewById(R.id.progress);
        distList = new ArrayList<>();
        distList.add("Select District");
        cropSeasonList = new ArrayList<>();
        cropSeasonList.add("Select Crop Season");
        cropSeasonList.add("Rabi");
        cropSeasonList.add("Kharif");
        cropSeasonList.add("Whole Season");

        cropYearList = new ArrayList<>();
        cropYearList.add("Select Crop Year");
        cropYearList.add("2019");
        cropYearList.add("2020");
        cropYearList.add("2021");
        cropYearList.add("2022");


        cropSoilList = new ArrayList<>();
        cropSoilList.add("Select Soil");
        cropSoilList.add("Alluvial Soil");
        cropSoilList.add("Black / Regur Soil");
        cropSoilList.add("Red Soil");
        cropSoilList.add("Laterite Soil");
        cropSoilList.add("Forest Soil");
        cropSoilList.add("Saline Soil");
        cropSoilList.add("Peaty / Marshy Soil");
        cropSoilList.add("Arid / Desert Soil");
        cropSoilList.add("Other Soil");

        p.setVisibility(View.VISIBLE);

//        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.States));
//        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        stateSpinner.setAdapter(arrayAdapter);
//        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String k = arrayAdapter.getItem(parent.getSelectedItemPosition());
//                if (k != null && stateSpinner.getSelectedItemPosition() != 0) {
//                    sendRequest(URL_DISTRICT, "State", k);
//                } else if (stateSpinner.getSelectedItemPosition() == 0) {
//                    distList = new ArrayList<>();
//                    distList.add("Select District");
//                    arrayAdapter2 = new ArrayAdapter<String>(CropVarietyRecommender.this, android.R.layout.simple_spinner_dropdown_item, distList);
//                    arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    distSpinner.setAdapter(arrayAdapter2);
//                }
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//            }
//        });
//
//        arrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, distList);
//        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        distSpinner.setAdapter(arrayAdapter2);
//        /*distSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String k = arrayAdapter2.getItem(parent.getSelectedItemPosition());
//                if (k != null && distSpinner.getSelectedItemPosition() != 0) {
//                    // Log.d("JyotiKey", k);
//                   // sendRequest(URL_CROP, "District", k);
//                    sendRequest("District", k);
//                } else if (distSpinner.getSelectedItemPosition() == 0) {
//
//                    cropList = new ArrayList<>();
//                    cropList.add("Select Crop");
//                    arrayAdapter2 = new ArrayAdapter<String>(CropVarietyRecommender.this, android.R.layout.simple_spinner_dropdown_item, cropList);
//                    arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    cropSpinner.setAdapter(arrayAdapter2);
//                }
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
//*/
        arrayAdapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, cropSeasonList);
        arrayAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cropSeasonSpinner.setAdapter(arrayAdapter3);

        arrayAdapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, cropYearList);
        arrayAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cropYearSpinner.setAdapter(arrayAdapter4);

        arrayAdapter5 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, cropSoilList);
        arrayAdapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cropSoilSpinner.setAdapter(arrayAdapter5);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fieldAreaEt.getText().toString().equals(" "))
                    fieldAreaEt.setError("Field must not be empty!!");

                String District = arrayAdapter2.getItem(spinner2.getSelectedItemPosition());
                String cropSeason = arrayAdapter3.getItem(cropSeasonSpinner.getSelectedItemPosition());
                String cropYear = arrayAdapter4.getItem(cropYearSpinner.getSelectedItemPosition());
                createPost(District);
                Log.e("Click","click");


//                if ((!District.equals("Select District"))
//                        && (!cropSeason.equals("Select Crop Season")) && (!cropYear.equals("Select Crop Year"))) {
//
//
//
//                }

               /* if (!State.isEmpty() && !District.isEmpty() && !Crop.isEmpty()&& !cropSeason.isEmpty()&& !cropYear.isEmpty() &&
                        (!State.equals("Select State")) && (!District.equals("Select District")) && (!Crop.equals("Select Crop"))
                        && (!cropSeason.equals("Select Crop Season")) && (!cropYear.equals("Select Crop Year"))) {

                    Toast.makeText(CropYieldPrediction.this, "Selected items are"+ State+" "+ District+" "+Crop+" "+"", Toast.LENGTH_SHORT).show();

                    final String data = "{\r\n" +
                            "        \"Inputs\": {\r\n" +
                            "                \"input1\":\r\n" +
                            "                [\r\n" +
                            "                    {\r\n" +
                            "                            'State_Name': \"Uttar Pradesh\",   \r\n" +
                            "                            'District_Name': \"Moradabad\",   \r\n" +
                            "                            'Crop_Year': \"2020\",   \r\n" +
                            "                            'Season': \"Rabi\",   \r\n" +
                            "                            'Crop': \"Wheat\",   \r\n" +
                            "                            'Area': \".2\",   \r\n" +
                            "                    }\r\n" +
                            "                ],\r\n" +
                            "        },\r\n" +
                            "    \"GlobalParameters\":  {\r\n" +
                            "    }\r\n" +
                            "}";
                    final String api_key = "2ZpzKx6ib1EZEc32ZDMUOvH5elfwLLPSyCOOqChSmp9SZzQu8QtvSguVczNru6uF6jIron9pLj9xiaHscRv0vw==";
                    final String url = "https://ussouthcentral.services.azureml.net/workspaces/3dcf36813def4bbbbc5215e117d32bdf/services/88aa5baf43574b37b08ba1a459ac204e/execute?api-version=2.0&format=swagger";
                    final String request_method = "POST";
                    new Runnable(){
                        @Override
                        public void run() {
                            StringBuffer sb = urllib.urlopen(url,api_key,data,request_method);
                            AlertDialog.Builder builder = new AlertDialog.Builder(CropYieldPrediction.this);
                            builder.setTitle("Crop Yield Predicted is");
                            builder.setMessage(sb.toString());
                            builder.show();
                        }
                    };
                }*/
            }
        });

        p.setVisibility(View.GONE);
    }

   // private void sendRequest(final String url, final String key, final String value){
//    private void sendRequest(final String url, final String key, final String value){
//
//        p.setVisibility(View.VISIBLE);
//        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                if(url==URL_DISTRICT){
//                    arrayAdapter2.clear();
//                    distList.add("Select District");
//                    distList=parseJson(response,"result","District");
//                    arrayAdapter2.addAll(distList);
//                    arrayAdapter2.notifyDataSetChanged();
//                }
//               /* else if (url==URL_CROP){
//                    arrayAdapter2.clear();
//                    cropList.add("Select Crop");
//                    cropList=parseJson(response,"result","Crop");
//                    arrayAdapter2.addAll(cropList);
//                    arrayAdapter2.notifyDataSetChanged();
//                }*/
//                Log.d("data",response);
//                p.setVisibility(View.GONE);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                p.setVisibility(View.GONE);
//                if(!new Connection(CropVarietyRecommender.this).isInternet()){
//                    Toast.makeText(CropVarietyRecommender.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
//                }else{
//                    Toast.makeText(CropVarietyRecommender.this, "Some error occured", Toast.LENGTH_SHORT).show();
//                }
//            }
//        }){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String,String> map=new HashMap<>();
//                map.put(key,value);
//                return map;
//            }
//        };
//
//        MySingleton.getInstance(MyApplication.getAppContext()).addToRequestQueue(stringRequest);
//    }
//    private ArrayList<String> parseJson(String key,String array_name,String object_name){
//        ArrayList<String> list=new ArrayList<>();
//        try {
//            JSONObject j=new JSONObject(key);
//            JSONArray a=j.getJSONArray(array_name);
//            for(int i=0;i<a.length();i++){
//                JSONObject w=a.getJSONObject(i);
//                list.add(w.getString(object_name));
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return list;
//    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (NavUtils.getParentActivityName(this) != null) {
                    finish();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void createPost(String district){
        Log.e("func","func");
        Call<PredictedResult> call = retrofitApi.createPost(
                Integer.parseInt(nitrogen.getText().toString().trim()),
                Integer.parseInt(phosphorous.getText().toString().trim()),
                Integer.parseInt(potassium.getText().toString().trim()),
                Double.parseDouble(ph.getText().toString().trim()),
                Double.parseDouble(rainfall.getText().toString().trim()),
                district
        );
        Log.e("call",Integer.parseInt(nitrogen.getText().toString().trim())+
                Integer.parseInt(phosphorous.getText().toString().trim())+
                Integer.parseInt(potassium.getText().toString().trim())+
                Double.parseDouble(ph.getText().toString().trim())+
                Double.parseDouble(rainfall.getText().toString().trim())+
                district.toLowerCase());
        call.enqueue(new Callback<PredictedResult>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<PredictedResult> call, Response<PredictedResult> response) {
                assert response.body() != null;
                result.setVisibility(View.VISIBLE);
                result.setText("You should grow " +response.body().getResult()+ " in your farm");

                Log.e("rESPO",response.body().getResult());

                if(!response.isSuccessful()){
                    Toast.makeText(CropVarietyRecommender.this,response.toString(),Toast.LENGTH_LONG).show();
                    Log.e("fail","no request");
                }
            }

            @Override
            public void onFailure(Call<PredictedResult> call, Throwable t) {
                Toast.makeText(CropVarietyRecommender.this,t.getMessage(),Toast.LENGTH_LONG).show();
                Log.e("fail",t.getMessage());

            }
        });

    }
}

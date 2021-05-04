package com.example.covid_19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class IndiaCovid extends AppCompatActivity {
    private RecyclerView recyclerView;
    private IndiaAdapter adapter;
    private RequestQueue requestQueue;
    private PieChart pieChart;
    TextView lastUpdated;
    ArrayList<IndianState> listitem;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_india_covid);
        recyclerView=findViewById(R.id.recyclerview_india);
        lastUpdated = findViewById(R.id.lastUpdated_india);
        listitem = new ArrayList<>();
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        pieChart = findViewById(R.id.piechart_india);
        progressBar = findViewById(R.id.progress_bar_india);
        //listitem.add(new IndianState("a","b","c","d","e"));

        //initializeResource();
        requestQueue =VolleySingletion.getInstance(IndiaCovid.this).getRequestQueue();
        fetchData();

        adapter = new IndiaAdapter(listitem);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        getSupportActionBar().setTitle("IND");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    //To implement arrow back behaviour
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private void initializeResource() {

    }

    /*
    private void fetchData() {
        String url = "https://api.rootnet.in/covid19-in/unofficial/covid19india.org/statewise";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("statewise");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        String active = object.getString("active");
                        String confirmed = object.getString("confirmed");
                        String deaths = object.getString("deaths");
                        String recovered = object.getString("recovered");
                        String name = object.getString("state");
                        IndianState abc =new IndianState(name,confirmed,active,recovered,deaths);
                        listitem.add(abc);
                    }
                } catch (JSONException e) { e.printStackTrace(); }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(IndiaCovid.this,error.toString(),Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(request);

    }*/

    private void fetchData() {
        String url = "https://api.covid19india.org/data.json";
        progressBar.setVisibility(View.VISIBLE);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("statewise");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        String active = object.getString("active");
                        String confirmed = object.getString("confirmed");
                        String deaths = object.getString("deaths");
                        String deltaconfirm = object.getString("deltaconfirmed");
                        String deltadeath = object.getString("deltadeaths");
                        String deltarecover = object.getString("deltarecovered");
                        String recovered = object.getString("recovered");
                        String name = object.getString("state");
                        IndianState abc =new IndianState(name,confirmed,active,recovered,deaths,deltaconfirm,deltadeath,"0",deltarecover);
                        listitem.add(abc);
                    }
                    JSONObject object = jsonArray.getJSONObject(0);
                    String active = object.getString("active");
                    String confirmed = object.getString("confirmed");
                    String deaths = object.getString("deaths");
                    String deltaconfirm = object.getString("deltaconfirmed");
                    String deltadeath = object.getString("deltadeaths");
                    String deltarecover = object.getString("deltarecovered");
                    String recovered = object.getString("recovered");
                    String name = object.getString("state");
                    String time = object.getString("lastupdatedtime");
                   // Log.i("India time",time);
                    lastUpdated.setText("Last Updated\n"+time);
                    IndianState abc =new IndianState(name,confirmed,active,recovered,deaths,deltaconfirm,deltadeath,"0",deltarecover);
                    listitem.add(0,abc);
                    //filling up piechart
                    pieChart.addPieSlice(new PieModel("Total Cases", Integer.parseInt(confirmed), Color.parseColor("#D32F2F")));
                    pieChart.addPieSlice(new PieModel("Recovered", Integer.parseInt(recovered), Color.parseColor("#388E3C")));
                    pieChart.addPieSlice(new PieModel("Deaths", Integer.parseInt(deaths),Color.parseColor("#FBC02D")));
                    pieChart.addPieSlice(new PieModel("Active Cases", Integer.parseInt(active), Color.parseColor("#1976D2")));
                    //


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter.notifyItemChanged(0);
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //textView.setText(error.toString());
            }
        });

        requestQueue.add(request);

    }


}
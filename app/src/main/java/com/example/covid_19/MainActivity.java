package com.example.covid_19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    //TextViews
    TextView tvCases,tvRecovered,tvActive,tvCritical,tvTodaysRecovered,tvTodaysCases,tvTotalDeaths,tvTodaysDeaths,tvAffectedCountries,tvtime;
    ScrollView scrollView;
    ProgressBar progressBar;
    PieChart pieChart;

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeResources();
        requestQueue = VolleySingletion.getInstance(this).getRequestQueue();
        fetchData();
    }

    private void fetchData() {
        String Url ="https://disease.sh/v3/covid-19/all";
        progressBar.setVisibility(View.VISIBLE);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //time of update
                    long time = response.getLong("updated");
                    //filling up textviews
                    tvCases.setText(response.getString("cases"));
                    tvRecovered.setText(response.getString("recovered"));
                    tvCritical.setText((response.getString("critical")));
                    tvActive.setText(response.getString("active"));
                    tvTodaysCases.setText(response.getString("todayCases"));
                    tvTotalDeaths.setText(response.getString("deaths"));
                    tvTodaysDeaths.setText(response.getString("todayDeaths"));
                    tvAffectedCountries.setText(response.getString("affectedCountries"));
                    tvTodaysRecovered.setText(response.getString("todayRecovered"));

                    //ssetting up unix time to local time
                    Date dateObject = new Date(time);
                    SimpleDateFormat dateFormatter = new SimpleDateFormat("LLL dd, yyyy h:mm a");
                    String dateToDisplay = dateFormatter.format(dateObject);
                    tvtime.setText(dateToDisplay);

                    //filling up piechart

                    pieChart.addPieSlice(new PieModel("Total Cases", Integer.parseInt(tvCases.getText().toString()), Color.parseColor("#D32F2F")));
                    pieChart.addPieSlice(new PieModel("Recovered", Integer.parseInt(tvRecovered.getText().toString()), Color.parseColor("#388E3C")));
                    pieChart.addPieSlice(new PieModel("Deaths", Integer.parseInt(tvTotalDeaths.getText().toString()),Color.parseColor("#FBC02D")));
                    pieChart.addPieSlice(new PieModel("Active Cases", Integer.parseInt(tvActive.getText().toString()), Color.parseColor("#1976D2")));

                    //
                    progressBar.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,String.valueOf(error),Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.VISIBLE);
                scrollView.setVisibility(View.GONE);
            }
        });

        requestQueue.add(request);
    }

    private void initializeResources() {
        tvCases = findViewById(R.id.cases);
        tvRecovered = findViewById(R.id.recovered);
        tvActive = findViewById(R.id.active);
        tvCritical = findViewById(R.id.critical);
        tvTodaysCases = findViewById(R.id.todays_cases);
        tvTotalDeaths = findViewById(R.id.total_deaths);
        tvTodaysDeaths = findViewById(R.id.todays_deaths);
        tvTodaysRecovered = findViewById(R.id.todays_recovered);
        tvAffectedCountries = findViewById(R.id.affected_countries);
        tvtime = findViewById(R.id.time_global);
        scrollView = findViewById(R.id.scroll_view);
        pieChart = findViewById(R.id.piechart);
        progressBar = findViewById(R.id.progress_bar);
    }

    public void trackCountries(View view) {
        startActivity(new Intent(MainActivity.this,Countries.class));
    }

    public void trackIndia(View view) {
        startActivity(new Intent(MainActivity.this,IndiaCovid.class));
    }
}
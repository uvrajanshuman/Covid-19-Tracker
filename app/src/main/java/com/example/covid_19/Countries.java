package com.example.covid_19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Countries extends AppCompatActivity {
    private EditText editText;
    private ProgressBar progressBar;

    private RecyclerView recyclerView;
    private CountryAdapter adapter;
    private RequestQueue requestQueue;

    private List<CountryDetails> countryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countries);

        getSupportActionBar().setTitle("Affected Countries");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        countryList = new ArrayList<>();
        initializeResource();

        requestQueue = VolleySingletion.getInstance(this).getRequestQueue();
        fetchData();

        //ading search functionality
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }
    private void filter(String text) {
        ArrayList<CountryDetails> filteredList = new ArrayList<>();
        for (CountryDetails item : countryList) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapter.filterList(filteredList);
    }

    //To implement arrow back behaviour
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private void fetchData() {
        String url = "https://disease.sh/v3/covid-19/countries";
        progressBar.setVisibility(View.VISIBLE);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i=0;i<response.length();i++){
                            try {
                                JSONObject country = response.getJSONObject(i);
                                long updated = country.getLong("updated");
                                String name = country.getString("country");
                                String cases = country.getString("cases");
                                String death = country.getString("deaths");
                                String recovered = country.getString("recovered");
                                String active = country.getString("active");
                                JSONObject countryInfo = country.getJSONObject("countryInfo");
                                String flag = countryInfo.getString( "flag");
                                countryList.add(new CountryDetails(updated,name,flag,cases,death,recovered,active));
                            } catch (JSONException e) {e.printStackTrace(); }

                        }
                        progressBar.setVisibility(View.GONE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Countries.this,error.toString(),Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(request);

    }

    private void initializeResource() {
        editText = findViewById(R.id.search);
        progressBar = findViewById(R.id.progress_bar);
        //config recyclerview
        recyclerView = findViewById(R.id.countries_recyclerview);
        recyclerView.setHasFixedSize(true);
        adapter = new CountryAdapter(countryList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
package com.example.covid_19;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleySingletion {
    private static VolleySingletion mInstance;
    private RequestQueue mRequestQueue;
    private VolleySingletion(Context context){
        mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }
    public static synchronized VolleySingletion getInstance(Context context){
        if (mInstance == null){
            mInstance = new VolleySingletion(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue(){
        return mRequestQueue;
    }
}

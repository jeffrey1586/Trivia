package com.example.mini_.trivia;

import android.view.View;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/*
    Objects of this class can do POST requests with parameters.
*/
public class PostRequest extends StringRequest  {
    String final_points;
    // Constructor
    public PostRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener, String points) {
        super(method, url, listener, errorListener);
        final_points = points;
    }

    // Method to supply parameters to the request
    @Override
    protected Map<String, String> getParams() {
        Map<String, String> params = new HashMap<>();
        params.put("User", final_points);
        return params;
    }
}

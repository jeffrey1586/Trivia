package com.example.mini_.trivia;

import android.content.Context;
import android.content.Intent;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ScoreRequest implements Response.Listener<JSONArray>, Response.ErrorListener{

    private Context context;
    private com.example.mini_.trivia.ScoreRequest.Callback theActivity;
    ArrayList array = new ArrayList<String>();

    public ScoreRequest(Context context) {
        this.context = context;
    }

    public interface Callback {
        void gotQuestion(ArrayList<String> scores);
        void gotQuestionError(String message);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        String errorMessage = error.getMessage();
        theActivity.gotQuestionError(errorMessage);
    }

    @Override
    public void onResponse(JSONArray response) {
        for (int i = 0; i < (response.length() + 1); i++) {
            try {
                JSONObject scoreInfo = response.getJSONObject(i);
                String username = scoreInfo.getString("username");
                String high_score = scoreInfo.getString("points");
                array.add(username + ": " + high_score);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        System.out.println("helloo" + array);
        theActivity.gotQuestion(array);
    }

    public void getQuestionItem(com.example.mini_.trivia.ScoreRequest.Callback activity) {
        RequestQueue queue = Volley.newRequestQueue(context);

        theActivity = activity;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                ("https://ide50-chongejonge.cs50.io:8080/trivia", this, this);
        queue.add(jsonArrayRequest);
    }
}

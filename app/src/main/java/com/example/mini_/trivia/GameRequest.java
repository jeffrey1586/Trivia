package com.example.mini_.trivia;

import android.content.Context;
import android.content.Intent;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GameRequest implements Response.Listener<JSONObject>, Response.ErrorListener{

    private Context context;
    private Callback theActivity;
    ArrayList array = new ArrayList<QuestionItem>();

    public GameRequest(Context context) {
        this.context = context;
    }

    public interface Callback {
        void gotQuestion(ArrayList<QuestionItem> categories);
        void gotQuestionError(String message);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        String errorMessage = error.getMessage();
        theActivity.gotQuestionError(errorMessage);
    }

    @Override
    public void onResponse(JSONObject response) {
        JSONArray QuestionArray = new JSONArray();
        try {
            QuestionArray = response.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // get all the information about the questions
        for (int i = 0; i < QuestionArray.length(); i++) {
            try {
                JSONObject questionInfo = QuestionArray.getJSONObject(i);
                String category = questionInfo.getString("category");
                String type = questionInfo.getString("type");
                String difficulty = questionInfo.getString("difficulty");
                String question = questionInfo.getString("question");
                String correct_answer = questionInfo.getString("correct_answer");
                JSONArray incorrect_answers = questionInfo.getJSONArray("incorrect_answers");
                String first = (String) incorrect_answers.get(0);
                String second = (String) incorrect_answers.get(1);
                String third = (String) incorrect_answers.get(2);

                QuestionItem item = new QuestionItem(category, type, difficulty, question,
                        correct_answer, first, second, third);
                array.add(item);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        theActivity.gotQuestion(array);
    }

    public void getQuestionItem(Callback activity) {
        RequestQueue queue = Volley.newRequestQueue(context);

        theActivity = activity;

        // start a connection with a JsonObjectRequest and an url
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                ("https://opentdb.com/api.php?amount=10", null, this, this);
        queue.add(jsonObjectRequest);

    }
}

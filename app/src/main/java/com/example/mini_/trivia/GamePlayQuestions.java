package com.example.mini_.trivia;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class GamePlayQuestions extends AppCompatActivity implements Response.ErrorListener, Response.Listener<String> {

    private Spinner spinner;
    String[] items;
    int answer_confirm;
    String check;
    int count;
    int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play_questions);

        Intent intent = getIntent();
        QuestionItem selectedItem = (QuestionItem) intent.getSerializableExtra("selected_item");

        TextView question = findViewById(R.id.question_text);
        TextView category = findViewById(R.id.category_text);
        String questionInfo = selectedItem.getQuestion();
        String categoryInfo = selectedItem.getCategory();
        question.setText(questionInfo);
        category.setText("Category: " + categoryInfo);

        final String correctAnswer = selectedItem.getCorrect_answer();
        String incorrectAnswer1 = selectedItem.getFirst();
        String incorrectAnswer2 = selectedItem.getSecond();
        String incorrectAnswer3 = selectedItem.getThird();

        if (incorrectAnswer2.isEmpty()) {
            // making list of boolean answers
            spinner = findViewById(R.id.spinner);
            items = new String[]{"", correctAnswer ,incorrectAnswer1};
        }
        else {
            // making list of multiple answers
            spinner = findViewById(R.id.spinner);
            items = new String[]{"", correctAnswer, incorrectAnswer1,
                    incorrectAnswer2, incorrectAnswer3};
        }

        // making and setting the adapter for the spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(GamePlayQuestions.this,
                android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // the click listener for the spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // checking if a legitimate title is chosen
                answer_confirm = position;

                // when clicked on OK button
                Button confirmButton = findViewById(R.id.button_confirm);
                confirmButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String answer_given = spinner.getSelectedItem().toString();
                        Intent score_intent = getIntent();
                        score = score_intent.getIntExtra("score", 110);
                        if (answer_given == correctAnswer) {
                            score += 10;
                            openActivity_next();
                        }
                        else {
                            openActivity_next();
                        }
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    // this method is prompt to convert the title and the user to the next activity
    public void openActivity_next() {

        Intent intent = getIntent();
        count = intent.getIntExtra("count", 11);
        count += 1;

        if (count < 7) {
            Intent proceed_intent = new Intent(GamePlayQuestions.this, GamePlay.class);
            proceed_intent.putExtra("count", count);
            proceed_intent.putExtra("score", score);
            startActivity(proceed_intent);
        }

        else if (count == 7){
            String url = "https://ide50-chongejonge.cs50.io:8080/trivia";
            RequestQueue queue = Volley.newRequestQueue(this);
            String highScore = String.valueOf(score);
            PostRequest request = new PostRequest(Request.Method.POST, url, this, this, highScore);
            queue.add(request);

            Intent done_intent = new Intent(GamePlayQuestions.this, HighScores.class);
            startActivity(done_intent);
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
    }

    @Override
    public void onResponse(String response) {

    }
}

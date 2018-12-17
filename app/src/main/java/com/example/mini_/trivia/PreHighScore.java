package com.example.mini_.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

public class PreHighScore extends AppCompatActivity implements Response.ErrorListener, Response.Listener<String> {

    String highScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_high_score);

        // connect click listener to OK button
        Button confirm = findViewById(R.id.username_confirm);
        confirm.setOnClickListener(new PreHighScore.ScoreListClickListener());

        // get the score earned from the player
        Intent score_intent = getIntent();
        highScore = score_intent.getStringExtra("score");
    }

    private class ScoreListClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            TextView user_holder = findViewById(R.id.username_text);
            String username = user_holder.getText().toString();

            // make a post request with username and high score to url
            if (username != "") {
                String url = "https://ide50-chongejonge.cs50.io:8080/trivia";
                RequestQueue queue = Volley.newRequestQueue(PreHighScore.this);
                PostRequest request = new PostRequest(Request.Method.POST, url,
                        PreHighScore.this,PreHighScore.this, username, highScore);
                queue.add(request);

                Intent intent = new Intent(PreHighScore.this, HighScores.class);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
    }

    @Override
    public void onResponse(String response) {
    }
}

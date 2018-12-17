package com.example.mini_.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class HighScores extends AppCompatActivity implements ScoreRequest.Callback{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);

        ScoreRequest x = new ScoreRequest(this);
        x.getQuestionItem(this);

        Button start = findViewById(R.id.again_button);
        start.setOnClickListener(new HighScores.StartClickListener());
    }

    private class StartClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(HighScores.this, Menu.class);
            startActivity(intent);
        }
    }

    @Override
    public void gotQuestion(ArrayList<String> scores) {
        // show scores in the list view
        ListView lv = findViewById(R.id.score_values);
        ArrayAdapter adapter = new ArrayAdapter
                (this, android.R.layout.simple_list_item_1, scores);
        lv.setAdapter(adapter);
    }

    @Override
    public void gotQuestionError(String message) {
    }

    @Override
    public void onBackPressed() {
        // when back is pressed user will go to menu activity
        Intent toHomeScreen = new Intent(this, Menu.class);
        startActivity(toHomeScreen);
    }
}

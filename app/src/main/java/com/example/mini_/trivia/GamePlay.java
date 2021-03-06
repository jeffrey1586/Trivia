package com.example.mini_.trivia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class GamePlay extends AppCompatActivity implements GameRequest.Callback{

    int count;
    int counts_left = 8;
    int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        // connect click listener to list view
        ListView start = findViewById(R.id.category_list);
        start.setOnItemClickListener(new SelectItemClickListener());

        GameRequest x = new GameRequest(this);
        x.getQuestionItem(this);

        // get question count and score
        Intent intent = getIntent();
        count = intent.getIntExtra("count", 11);
        score = intent.getIntExtra("score", 11);

        // display how many questions left
        counts_left -= count;
        TextView counter = findViewById(R.id.count_text);
        counter.setText(counts_left + " questions to go!");
    }

    private class SelectItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            QuestionItem item = (QuestionItem) parent.getItemAtPosition(position);

            // put question count and score in intent to connected activity
            Intent intentMulti = new Intent(GamePlay.this, GamePlayQuestions.class);
            intentMulti.putExtra("selected_item", item);
            intentMulti.putExtra("count", count);
            intentMulti.putExtra("score", score);
            startActivity(intentMulti);
        }
    }

    @Override
    public void gotQuestion(ArrayList<QuestionItem> array) {
        // show categories in list view with adapter
        ListView lv = findViewById(R.id.category_list);
        GameAdapter adapter = new GameAdapter(this, R.layout.activity_categories, array);
        lv.setAdapter(adapter);
    }

    @Override
    public void gotQuestionError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
    }
}


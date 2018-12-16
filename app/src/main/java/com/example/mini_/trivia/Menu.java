package com.example.mini_.trivia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Menu extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button start = findViewById(R.id.button_start);
        Button score = findViewById(R.id.button_score);

        start.setOnClickListener(new Menu.StartClickListener());
        score.setOnClickListener(new Menu.ScoreListClickListener());
    }

    private class StartClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Menu.this, GamePlay.class);
            intent.putExtra("count", 0);
            intent.putExtra("score", 0);
            startActivity(intent);
        }
    }

    private class ScoreListClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Menu.this, HighScores.class);
            startActivity(intent);
        }
    }
}

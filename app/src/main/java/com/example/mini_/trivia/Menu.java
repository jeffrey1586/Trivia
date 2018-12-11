package com.example.mini_.trivia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Menu extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button start = findViewById(R.id.button_start);
        start.setOnClickListener(new Menu.StartClickListener());
    }

    private class StartClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Menu.this, GamePlay.class);
            startActivity(intent);
        }
    }
}

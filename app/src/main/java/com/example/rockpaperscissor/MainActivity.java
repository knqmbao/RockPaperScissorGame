package com.example.rockpaperscissor;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public Button btnPlay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPlay = (Button) findViewById(R.id.PlayButton);
        btnPlay.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,RPSGame.class);
            startActivity(intent);
        });

    }

}

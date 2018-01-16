package com.example.kamran.multiplayergame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.VectorEnabledTintResources;
import android.view.View;
import android.widget.Toast;

public class HighScores extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);

        String msg;
        msg=findViewById(R.id.TopScore).toString();

      //  Toast.makeText(getApplicationContext(), "Calling High Score", Toast.LENGTH_SHORT).show();
    }

    public  void  GoBackFromHighScore(View v)
    {

      //  Toast.makeText(getApplicationContext(), "Calling On Click even", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, CheckersBoard.class);
        startActivity(intent);
    }

}

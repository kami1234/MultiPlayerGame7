package com.example.kamran.multiplayergame;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);


      //  Intent intent = getIntent();
        //String message = intent.getDataString(Ab);

        // Capture the layout's TextView and set the string as its text
        //TextView textView = (TextView) findViewById(R.id.aBoutGame);

       // textView.setText("Hello "+ message);

        Toast.makeText(getApplicationContext(),"Calling",Toast.LENGTH_LONG).show();


    }
}

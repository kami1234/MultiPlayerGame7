package com.example.kamran.multiplayergame;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class NewGame extends Activity {
TextView tv;
    TextView RbSel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //we dont want no title as its full screeen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//To get the full screen mode
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_new_game);

        Intent intent = getIntent();
        //  String message = intent.getExtras();
        tv= (TextView) findViewById(R.id.textView);

        // Bundle b=intent.getExtras();
        //  String ib= (String)b.get("name");
        // tv.setText(ib);
//Random Number is generated on main activity then pass it to new game
        String Getval = intent.getStringExtra("Name");
        tv.setText(Getval);
        Toast.makeText(getApplicationContext(), Getval, Toast.LENGTH_SHORT).show();








    }


    public void hArdGame(View v)
    {
        Toast.makeText(getApplicationContext(), "Hard select ", Toast.LENGTH_SHORT).show();
        RbSel=(TextView) findViewById(R.id.DifficultyChoose);
        RbSel.setText("Hard Select");

    }

    public void eAsyGame(View v)
    {
        Toast.makeText(getApplicationContext(), "Easy select ", Toast.LENGTH_SHORT).show();

    }

    public void mEdiumGame(View v)
    {
        Toast.makeText(getApplicationContext(), "mEdiumselect ", Toast.LENGTH_SHORT).show();

    }
}

package com.example.kamran.multiplayergame;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends Activity {
  //  public static final String EXTRA_MESSAGE = "com.example.kamran.multiplayergame";
      public Random rand;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



                       rand = new Random();

        EditText ed ;

              // we can write an onclick listener as well lets make it for New game

          findViewById(R.id.NewGame).setOnClickListener(new View.OnClickListener(){
               @Override
               public  void onClick(View view)
               {
                      int k = rand.nextInt();
                   Intent i =new Intent(view.getContext(),NewGame.class);

                    String testString = Integer.toString(k);

                   i.putExtra("Name",testString);


                     startActivity(i);

               }


           });


    } //End Of onCreateFunction


    public  void DisplayAbout(View v)
    {
        Intent intent = new Intent(this, About.class);
        startActivity(intent);

    }
    public  void ViewHighScore(View v)
    {
        Intent intent = new Intent(this, HighScores.class);
        startActivity(intent);

    }
    public  void goToMultiPlayerRoom(View v)
    {
        Intent intent = new Intent(this,MultiPlayer.class);
        startActivity(intent);

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }







    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

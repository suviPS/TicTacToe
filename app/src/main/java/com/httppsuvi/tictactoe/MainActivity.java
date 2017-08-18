package com.httppsuvi.tictactoe;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    //Layout references
    private GridLayout tablelayout;
    private TextView player0TextView;
    private int player0Score=0;
    private TextView player1TextView;
    private int player1Score=0;

    //Logic
    private int activePlayer = 0;
    private int gameStatus[] = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    private int playedMat[][] = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}};
    private int winner=-1;
    private int allPlayed=9;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Reference layout elements
        tablelayout = (GridLayout) findViewById(R.id.tableGridLayout);
        player0TextView= (TextView) findViewById(R.id.player1PointstextView);
        player1TextView=  (TextView) findViewById(R.id.player2PointstextView);


        // Set layout heigth same as width
        Resources r = getResources();
        int wantedSize= (int) r.getDisplayMetrics().widthPixels;
        ViewGroup.LayoutParams params = tablelayout.getLayoutParams();
        params.height = wantedSize;
        tablelayout.setLayoutParams(params);


    }


    public void newEntry(View v) {

        ImageView counter = (ImageView) v;
        //move image from screen
        counter.setTranslationY(-1000f);

        int tagInt= Integer.parseInt(counter.getTag().toString());
        gameStatus[tagInt]=activePlayer;

        //update layout
        if (activePlayer == 0) {
            counter.setImageResource(R.drawable.xtoe);
            activePlayer++;
            allPlayed--;
        } else {
            counter.setImageResource(R.drawable.otoe);
            activePlayer--;
            allPlayed--;
        }
        //translate image back in position
        counter.animate().translationYBy(1000f).setDuration(100);
        //disable rewrite
        counter.setClickable(false);


       if(isFinished()) {
           Toast.makeText(this, "We have a winner!", Toast.LENGTH_SHORT).show();

           //Update score
           if(winner==1)
               player1TextView.setText(""+ ++player1Score);
           else if(winner==0)
               player0TextView.setText(""+ ++player0Score);

           //disable other click
            for(int i=0;i<tablelayout.getChildCount();i++)
                ((ImageView)tablelayout.getChildAt(i)).setClickable(false);

       }
        if (allPlayed==0&&winner==-1)
            Toast.makeText(this, "It's a Draw :D", Toast.LENGTH_SHORT).show();

    }

    public boolean isFinished() {
        for (int[] winningCombo : playedMat) {
            if (gameStatus[winningCombo[0]] == gameStatus[winningCombo[1]] &&
                    gameStatus[winningCombo[0]] == gameStatus[winningCombo[2]] &&
                    gameStatus[winningCombo[0]]!=2){
                winner=gameStatus[winningCombo[0]];
                return true;
        }}
        return false;
    }

    public void playAgain(View v) {
        winner = -1;
        allPlayed = 9;
        for (int i = 0; i < gameStatus.length; i++)
            gameStatus[i] = 2;

        ImageView temp;
        for (int i = 0; i < tablelayout.getChildCount(); i++) {
            temp = (ImageView) tablelayout.getChildAt(i);
            temp.setClickable(true);
            temp.setImageResource(0);

        }


        if (activePlayer == 1)
            Toast.makeText(this, "Player2 goes fists...", Toast.LENGTH_SHORT).show();
        else if (activePlayer == 0)
            Toast.makeText(this, "Player1 goes fists...", Toast.LENGTH_SHORT).show();


    }

    public void resetAll(){
        activePlayer = 0;
        player1Score=0;
        player0Score=0;
        player1TextView.setText("0");
        player0TextView.setText("0");

        //block of code cous i'm lazzy
        winner = -1;
        allPlayed = 9;
        for (int i = 0; i < gameStatus.length; i++)
            gameStatus[i] = 2;

        ImageView temp;
        for (int i = 0; i < tablelayout.getChildCount(); i++) {
            temp = (ImageView) tablelayout.getChildAt(i);
            temp.setClickable(true);
            temp.setImageResource(0);

        }

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;

    }

    //start About intent or popUp
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuAbout:

                new AlertDialog.Builder(this)
                        .setTitle("\tAbout")
                        .setMessage("Simple tic tac toe app created by Petar Suvajac")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setIcon(R.drawable.ico_launcher)
                        .show();
                break;

            case R.id.menuResetScore:
                resetAll();
                break;

        }

        return true;


    }

}

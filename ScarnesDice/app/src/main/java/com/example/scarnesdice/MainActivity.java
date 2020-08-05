package com.example.scarnesdice;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

import static com.example.scarnesdice.R.drawable.*;

public class MainActivity extends AppCompatActivity {

    private static int userTotalScore = 0;
    private static int userTurnScore = 0;
    private static int computerTotalScore = 0;
    private static int computerTurnScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button rollButton = findViewById(R.id.rollButton);
        rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int randomState = getRandomState();

                if( randomState == 1 ) {
                    userTurnScore = 0;

                    String result = "Your score: " + userTotalScore  +" computer score: " + computerTotalScore;
                    TextView textView = (TextView) findViewById(R.id.Scores);
                    textView.setText(result);
                    ComputerTurn();
//                    userTotalScore
                }else{
                    Log.i( "DEBUG", "Updating User Total Score " + userTotalScore );
                    userTurnScore = randomState;
                    userTotalScore += randomState;
                }

//                String result = "Your score: 0 computer score: 0 your turn score: " + userTurnScore;
                String result = "Your score: " + userTotalScore +  " computer score: " + computerTotalScore + " User turn score: " + userTurnScore;
                TextView textView = (TextView) findViewById(R.id.Scores);
                textView.setText(result);
            }
        });

        Button resetButton  = findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userTurnScore = 0;
                userTotalScore = 0;
                computerTotalScore = 0;
                computerTurnScore = 0;

                String result = "Your score: 0 computer score: 0";
                TextView textView = (TextView) findViewById(R.id.Scores);
                textView.setText(result);
            }
        });

        Button HoldButton = findViewById(R.id.holdButton);
        HoldButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String result = "Your score: " + userTotalScore  +" computer score: 0";
                TextView textView = (TextView) findViewById(R.id.Scores);
                textView.setText(result);
                Log.i( "DEBUG", "Updating UI for user" + userTotalScore );
                ComputerTurn();
            }
        });
    }

    private void ComputerTurn(){

        Button rollButton = findViewById(R.id.rollButton);
        Button HoldButton = findViewById(R.id.holdButton);

        rollButton.setEnabled(false);
        HoldButton.setEnabled(false);

        boolean computerTurn = true;

        while (computerTurn){

//            if( co >= 20 )
//                break;

            int randomState = getRandomState();
            if( randomState == 1 ) {
                computerTurnScore = 0;

                String result = "Your score: " + userTotalScore  +" computer score: " + computerTotalScore;
                TextView textView = (TextView) findViewById(R.id.Scores);
                textView.setText(result);
//                    userTotalScore
                break;
            }else{
                Log.i( "DEBUG", "Updating Computer Total Score " + userTotalScore );
                computerTurnScore = randomState;
                computerTotalScore += randomState;
            }

            String result = "Your score: " + userTotalScore +  " computer score: " + computerTotalScore + " Computer turn score: " + computerTurnScore;

            TextView textView = (TextView) findViewById(R.id.Scores);
            textView.setText(result);
        }
        rollButton.setEnabled(true);
        HoldButton.setEnabled(true);
    }

    private int getRandomState(){
        Random random = new Random();
        int randomState = 1 + random.nextInt(6);
        Log.i( "DEBUG", "random state is " + randomState );

        ImageView imageView = findViewById(R.id.diceState);

        switch (randomState){
            case 1 :
//                        userTurnScore = 0;
                imageView.setImageResource(dice1);
                break;
            case 2:
//                        userTurnScore = randomState;
                imageView.setImageResource(dice2);
                break;
            case 3 :
//                        userTotalScore = randomState;
                imageView.setImageResource(dice3);
                break;
            case 4 :
//                        userTotalScore += randomState;
                imageView.setImageResource(dice4);
                break;
            case 5:
//                        userTotalScore += randomState;
                imageView.setImageResource(dice5);
                break;
            case 6 :
//                        userTotalScore += randomState;
                imageView.setImageResource(dice6);
                break;
        }

        return randomState;
    }
}
/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.ghost;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;


public class GhostActivity extends AppCompatActivity {
    private static final String COMPUTER_TURN = "Computer's turn";
    private static final String USER_TURN = "Your turn";
    private GhostDictionary dictionary;
    private boolean userTurn = false;
    private Random random = new Random();

    TextView ghostText, ghostStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghost);
        AssetManager assetManager = getAssets();
        /**
         **
         **  YOUR CODE GOES HERE
         **
         **/
        ghostText = (TextView) findViewById(R.id.ghostText);
        ghostStatus = (TextView) findViewById(R.id.gameStatus);

        try {
            InputStream open = assetManager.open("words.txt");
            dictionary = new SimpleDictionary( open );

        } catch (IOException e) {
//            e.printStackTrace();
            Toast toast = Toast.makeText(this, "Could not load dictionary", Toast.LENGTH_LONG);
            toast.show();
        }

        Button challengeButton = findViewById(R.id.Challenge);
        challengeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                challenge();
            }
        });

        Button restartButton = findViewById(R.id.restart);
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ghostText.setText("");
                ghostStatus.setText(USER_TURN);
            }
        });

        onStart(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ghost, menu);
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

    public void challenge(){
        String word = ghostText.getText().toString();
        TextView label = (TextView) findViewById(R.id.gameStatus);

        if( word.length() >= 1 && dictionary.isWord(word) ){
            label.setText("You Won!!");
        }else {

            String result = dictionary.getAnyWordStartingWith(word);
            if( result.length() > 0 ){
                Log.i("DEBUG" , "Player loses");
                label.setText("You Lose!!");
//            ghostText.setText(result);
                return;
            }else{
                label.setText("You Win!!");
            }

        }
    }

    /**
     * Handler for the "Reset" button.
     * Randomly determines whether the game starts with a user turn or a computer turn.
     * @param view
     * @return true
     */
    public boolean onStart(View view) {
        userTurn = random.nextBoolean();
        TextView text = (TextView) findViewById(R.id.ghostText);
        text.setText("");
        TextView label = (TextView) findViewById(R.id.gameStatus);
        if (userTurn) {
            label.setText(USER_TURN);
        } else {
            label.setText(COMPUTER_TURN);
            computerTurn();
        }
        return true;
    }

    private void computerTurn() {
        Log.i("DEBUG" , "In computer Turn ");
        TextView label = (TextView) findViewById(R.id.gameStatus);
        // Do computer turn stuff then make it the user's turn again
        String prefix = ghostText.getText().toString();

        if( prefix == null ){
            Log.i("DEBUG" , "Prefix is Null");
            prefix = "";
        }

        if( prefix.length() >= 4 && dictionary.isWord(prefix) ){
            Log.i("DEBUG" , "Player Wins!!");
            label.setText("You Won!!");
            return;
        }


        String result = dictionary.getAnyWordStartingWith(prefix);
        if( result.length() == 0 ){
            Log.i("DEBUG" , "Player loses");
            label.setText("You Lose!!");
//            ghostText.setText(result);
            return;
        }
//
//        Log.i("DEBUG" , "Taking the Compuetrs turn");
        ghostText.append(String.valueOf(result.charAt(prefix.length())));
//
//        Log.i("DEBUG" , "Taking the Compuetrs turn" + ghostText.getText().toString());
        userTurn = true;
        label.setText(USER_TURN);
    }





    /**
     * Handler for user key presses.
     * @param keyCode
     * @param event
     * @return whether the key stroke was handled.
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        /**
         **
         **  YOUR CODE GOES HERE
         **
         **/
        Log.i("DEBUG","In Key Up method");
        if( keyCode >=  KeyEvent.KEYCODE_A && keyCode <= KeyEvent.KEYCODE_Z)
        {


            char keyPressed = (char) event.getUnicodeChar();
            ghostText.append(String.valueOf(keyPressed));
            userTurn = false;

            Log.i("DEBUG","Key Pressed" + keyPressed + "ghost Text is"  + ghostText.getText().toString());
            computerTurn();
        }
        return super.onKeyUp(keyCode, event);
    }
}

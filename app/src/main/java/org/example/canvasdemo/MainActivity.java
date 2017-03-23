package org.example.canvasdemo;

import android.app.Activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends Activity {
    MyView myView;
    ArrayList<GoldCoin> goldCoins = new ArrayList<GoldCoin>();
    int point = 0;

    enum Move {
        UP, DOWN, LEFT, RIGHT
    }

    //FOR AUTO RUN

    private Timer myTimer;
    private int counter = 0;
    private Move moves;
    private Move lastMoove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button rightButton = (Button) findViewById(R.id.moveRight);
        Button leftButton = (Button) findViewById(R.id.moveLeft);
        Button upButton = (Button) findViewById(R.id.moveUp);
        Button downButton = (Button) findViewById(R.id.moveDown);
        Button resetBtn = (Button) findViewById(R.id.resetBtn);

        myView = (MyView) findViewById(R.id.gameView);
        //listener of our pacman

        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moves = Move.LEFT;
            }
        });
        rightButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                moves = Move.RIGHT;
            }
        });
        upButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                moves = Move.UP;
            }
        });
        downButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                moves = Move.DOWN;
            }
        });

        for (int i = 0; i < 10; i++) {
            GoldCoin g = new GoldCoin();
            goldCoins.add(g);
            System.out.print("Adding gold coin nr: " + i);
        }

        myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                TimerMethod();
            }

        }, 0, 10);

        myView.setGoldCoint(goldCoins);
    }

    public void setPointTextView() {
        TextView tw = (TextView) findViewById(R.id.pointsTextView);
        tw.setText("Points: " + point);

    }

    public void resetGame(View view) {
        point = 0;
        setPointTextView();
        myView.resetGame();
        moves = null;
    }

    public void pauseGame(View view) {
        lastMoove = moves;
        moves = null;
     }

    public void continueGame(View view) {
        if(moves == null) {
            moves = lastMoove;
        }
    }

    //
    //// FOR AUTO RUN
    //

    @Override
    protected void onStop() {
        super.onStop();
        myTimer.cancel();
    }

    private void TimerMethod() {
        this.runOnUiThread(Timer_Tick);
    }

    private Runnable Timer_Tick = new Runnable() {
        public void run() {
        if (moves != null) {
            if (moves.equals(moves.LEFT)) {
                point = myView.movePac(1, -10);
            } else if (moves.equals(moves.RIGHT)) {
                point = myView.movePac(1, 10);
            } else if (moves.equals(moves.UP)) {
                point = myView.movePac(0, -10);
            } else if (moves.equals(moves.DOWN)) {
                point = myView.movePac(0, 10);
            }

            setPointTextView();
        }
        counter++;
        }
    };
}



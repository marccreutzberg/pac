package org.example.canvasdemo;

import android.app.DialogFragment;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends FragmentActivity implements GameOverDialog.GameOverDialogListener, VictoryDialogFragment.VictoryDialogFragmentListener, LevelsFinishedDialogFragment.LevelsFinishedDialogFragmentListener {
    MyView myView;
    ArrayList<GoldCoin> goldCoins = new ArrayList<GoldCoin>();
    ArrayList<Level> levels = new ArrayList<Level>();
    ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    int point = 0;
    boolean isPaused = false;

    @Override
    public void onGameOverDialogResetClick(DialogFragment dialog) {
        myView.resetLevel();
    }

    @Override
    public void onVictoryResetLevelClick(DialogFragment dialog) {
        myView.resetLevel();
    }

    @Override
    public void onVictoryNextLevelClick(DialogFragment dialog) {
        currentLevelIndex++;
        myView.currentLevel = levels.get(currentLevelIndex);
        myView.nextLevel();
        TextView tw = (TextView) findViewById(R.id.timeTextView);
        tw.setText("");
        TextView t = (TextView) findViewById(R.id.currentLevelTextView);
        t.setText("Current level: " + myView.currentLevel.getName());
    }

    @Override
    public void onLevelsFinishedResetGameClick(DialogFragment dialog) {
        currentLevelIndex = 0;
        myView.currentLevel = levels.get(currentLevelIndex);
        myView.resetLevel();
        TextView t = (TextView) findViewById(R.id.currentLevelTextView);
        t.setText("Current level: " + myView.currentLevel.getName());
    }


    enum Move {
        UP, DOWN, LEFT, RIGHT
    }

    private Timer myTimer;
    private Timer countDownTimer;
    private int countDownCounter = 0;
    private int counter = 0;
    private int currentLevelDuration = 0;
    private int currentLevelIndex = 0;
    private Move moves;
    private Move lastMove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        levels = generateLevels();

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
                if (!isPaused) {
                    moves = Move.LEFT;
                }
            }
        });
        rightButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!isPaused) {
                    moves = Move.RIGHT;
                }
            }
        });
        upButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isPaused) {
                    moves = Move.UP;
                }
            }
        });
        downButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!isPaused) {
                    moves = Move.DOWN;
                }

            }
        });

        Level firstLevel = levels.get(0);

        for (int i = 0; i < firstLevel.getNumberOfCoins(); i++) {
            GoldCoin g = new GoldCoin();
            goldCoins.add(g);
            System.out.print("Adding gold coin nr: " + i);
        }

        for (int i = 0; i < 2; i++) {
            Enemy e = new Enemy();
            enemies.add(e);
            System.out.println("Adding enemy nr: " + i);
        }

        myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                TimerMethod();
            }

        }, 0, 20);

        countDownTimer = new Timer();
        countDownTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                CountDownMethod();
            }
        }, 0, 1000);

        myView.setGoldCoint(goldCoins);
        myView.currentLevel = levels.get(0);
        myView.setEnemies(enemies);

        TextView t = (TextView) findViewById(R.id.currentLevelTextView);
        t.setText("Current level: " + myView.currentLevel.getName());
    }

    public void setPointTextView() {
        TextView tw = (TextView) findViewById(R.id.pointsTextView);
        tw.setText("Points: " + point);

    }

    public void resetCompletedGame(View view) {
        isPaused = false;
        point = 0;
        setPointTextView();
        myView.resetLevel();
        moves = null;
    }

    public void pauseGame(View view) {
        isPaused = true;
        lastMove = moves;
        moves = null;
    }

    public void continueGame(View view) {
        if (moves == null) {
            isPaused = false;
            moves = lastMove;
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

    private void CountDownMethod() {
        this.runOnUiThread(CountDown_Tick);
    }

    private void TimerMethod() {
        this.runOnUiThread(Timer_Tick);
    }

    private Runnable Timer_Tick = new Runnable() {
        public void run() {
            if (moves != null) {
                if (moves.equals(moves.LEFT)) {
                    point = myView.movePac(moves.toString());
                } else if (moves.equals(moves.RIGHT)) {
                    point = myView.movePac(moves.toString());
                } else if (moves.equals(moves.UP)) {
                    point = myView.movePac(moves.toString());
                } else if (moves.equals(moves.DOWN)) {
                    point = myView.movePac(moves.toString());
                }

                myView.moveEnemy();

                //enemy running the same way 10 times
                setPointTextView();
            }

            if (myView.gameOver && moves != null) {
                gameOver();
            }

            if ((currentLevelDuration - myView.counter) > 0 && myView.takenCounts == myView.goldCoins.size() && moves != null) {
                if (currentLevelIndex < levels.size() - 1) {
                    victory();
                } else {
                    resetCompletedGame();
                }
            }


        }
    };

    private Runnable CountDown_Tick = new Runnable() {
        public void run() {
            if (myView.isStarted && !isPaused) {
                countDownCounter = myView.updateTime();
                currentLevelDuration = myView.currentLevel.getDuration();

                if ((currentLevelDuration - myView.counter) <= 0) {
                    gameOver();
                }

                TextView tw = (TextView) findViewById(R.id.timeTextView);
                tw.setText("Time remaining: " + (currentLevelDuration - countDownCounter));
            }
        }

    };

    private ArrayList<Level> generateLevels() {
        ArrayList<Level> levels = new ArrayList<Level>();
        Level level1 = new Level(20, 5, "Level 1");
        Level level2 = new Level(15, 8, "Level 2");
        Level level3 = new Level(15, 10, "Level 3");
        Level level4 = new Level(10, 10, "Level 4");
        Level level5 = new Level(10, 15, "Level 5");

        levels.add(level1);
        levels.add(level2);
        levels.add(level3);
        levels.add(level4);
        levels.add(level5);

        return levels;
    }

    public void gameOver() {
        TextView tw = (TextView) findViewById(R.id.timeTextView);
        tw.setText("");
        myView.isStarted = false;
        moves = null;
        DialogFragment dialog = new GameOverDialog();
        dialog.setCancelable(false);
        dialog.show(getFragmentManager(), "GameOverDialog");
    }

    public void victory() {
        TextView tw = (TextView) findViewById(R.id.timeTextView);
        tw.setText("");
        myView.isStarted = false;
        moves = null;
        DialogFragment dialog = new VictoryDialogFragment();
        dialog.setCancelable(false);
        dialog.show(getFragmentManager(), "VictoryDialog");
    }

    public void resetCompletedGame() {
        TextView tw = (TextView) findViewById(R.id.timeTextView);
        tw.setText("");
        myView.isStarted = false;
        moves = null;
        DialogFragment dialog = new LevelsFinishedDialogFragment();
        dialog.setCancelable(false);
        dialog.show(getFragmentManager(), "LevelsFinishedDialog");
    }
}



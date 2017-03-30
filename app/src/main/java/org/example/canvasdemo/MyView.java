package org.example.canvasdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

public class MyView extends View {

    Bitmap pacIcon = BitmapFactory.decodeResource(getResources(), R.drawable.pacman);
    Bitmap coinIcon = BitmapFactory.decodeResource(getResources(), R.drawable.coin_1x);
    Bitmap enemyIcon = BitmapFactory.decodeResource(getResources(), R.drawable.red);

    ArrayList<GoldCoin> goldCoins;
    ArrayList<Enemy> enemies;
    int points = 0;
    int counter = 0;
    int takenCounts = 0;
    boolean isStarted = false;
    boolean gameOver = false;
    Level currentLevel;

    public void setGoldCoint(ArrayList<GoldCoin> gold) {
        goldCoins = gold;
    }

    public void setEnemies(ArrayList<Enemy> enemies) {
        this.enemies = enemies;
    }

    //The coordinates for our dear pacman: (0,0) is the top-left corner
    int pacx = 50;
    int pacy = 400;
    int h, w; //used for storing our height and width

    /* The next 3 constructors are needed for the Android view system,
    when we have a custom view.
     */
    public MyView(Context context) {
        super(context);

    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //In the onDraw we put all our code that should be
    //drawn whenever we update the screen.
    @Override
    protected void onDraw(Canvas canvas) {
        //Here we get the height and weight
        h = canvas.getHeight();
        w = canvas.getWidth();

        //Making a new paint object
        Paint paint = new Paint();
        canvas.drawColor(Color.WHITE); //clear entire canvas to white color

        canvas.drawBitmap(pacIcon, pacx, pacy, paint);

        //canvas.drawRect(r, paint);
        for (GoldCoin g : goldCoins) {
            if (!g.isTaken()) {
                if (!g.isInitialized()) {
                    g.setX(randomMiser(0, w - 100));
                    g.setY(randomMiser(0, h - 100));
                    g.setInitialized(true);
                }
                canvas.drawBitmap(coinIcon, g.getX(), g.getY(), paint);
            }
        }

        for (Enemy e : enemies) {
            if (!e.isInitialized()) {
                e.setX(randomMiser(0, w - enemyIcon.getWidth()));
                e.setY(randomMiser(0, h - h - enemyIcon.getHeight()));

                //if enemy is spawing on pac at game start, then enemy will get a new position
                while (spaceBetween(pacx, pacy, e.getX(), e.getY()) < 100){
                    e.setX(randomMiser(0, w - enemyIcon.getWidth()));
                    e.setY(randomMiser(0, h - enemyIcon.getHeight()));
                }


                e.setInitialized(true);
            }
            canvas.drawBitmap(enemyIcon, e.getX(), e.getY(), paint);
        }


        super.onDraw(canvas);
    }

    public int movePac(int way, int movingPixels, String direction) {
        isStarted = true;
        if (way == 0) {
            //up og ned
            if (direction == "UP") {
                pacIcon = BitmapFactory.decodeResource(getResources(), R.drawable.pacman_up);
            }
            if (direction == "DOWN") {
                pacIcon = BitmapFactory.decodeResource(getResources(), R.drawable.pacman_down);
            }
            if ((pacy + pacIcon.getHeight() + movingPixels) < h && (pacy + pacIcon.getHeight() + movingPixels) > pacIcon.getHeight()) {
                pacy = pacy + movingPixels;

            }
        }
        //FREM OG TILBAGE
        else if (way == 1) {
            if (direction == "RIGHT") {
                pacIcon = BitmapFactory.decodeResource(getResources(), R.drawable.pacman);
            }
            if (direction == "LEFT") {
                pacIcon = BitmapFactory.decodeResource(getResources(), R.drawable.pacman_left);
            }

            if ((pacx + pacIcon.getWidth() + movingPixels) < w && (pacx + pacIcon.getWidth() + movingPixels) > pacIcon.getWidth()) {
                pacx = pacx + movingPixels;
            }
        }

        isOnCoin();
        isOnEnemy();
        invalidate(); //redraw everything - this ensures onDraw() is called.

        return points;
    }


    public void isOnCoin() {
        for (GoldCoin g : goldCoins) {
            if (!g.isTaken()) {
                if (spaceBetween(pacx, pacy, g.getX(), g.getY()) < 50) {
                    g.setTaken(true);
                    takenCounts++;
                    points++;
                }
            }
        }
    }

    public void resetLevel() {
        pacIcon = BitmapFactory.decodeResource(getResources(), R.drawable.pacman);
        pacx = 50;
        pacy = 400;
        counter = 0;
        takenCounts = 0;
        isStarted = false;
        gameOver = false;
        for (GoldCoin g : goldCoins) {
            g.setX(randomMiser(0, w - 100));
            g.setY(randomMiser(0, h - 100));
            g.setInitialized(true);
            g.setTaken(false);
        }

        invalidate(); //redraw everything - this ensures onDraw() is called.
    }

    public void moveEnemy(int counter) {
        int movingEnemyPixels = 5;
        for (Enemy e : enemies) {

            if (counter % 25 == 0) {
                e.setWay(randomMiser(1, 4));
            }

            if (e.getWay() == 1) {
                //GOING RIGHT
                if ((e.getX() + enemyIcon.getWidth() + movingEnemyPixels) < w && (e.getX() + enemyIcon.getWidth() + movingEnemyPixels) > enemyIcon.getWidth()) {
                    e.setX(e.getX() + movingEnemyPixels);
                }

            } else if (e.getWay() == 2) {
                if ((e.getX() + enemyIcon.getWidth() - movingEnemyPixels) < w && (e.getX() + enemyIcon.getWidth() - movingEnemyPixels) > enemyIcon.getWidth()) {
                    e.setX(e.getX() - movingEnemyPixels);
                }
            } else if (e.getWay() == 3) {
                //GOING UP

                if ((e.getY() + enemyIcon.getHeight() + movingEnemyPixels) < h && (e.getY() + enemyIcon.getHeight() + movingEnemyPixels) > enemyIcon.getHeight()) {
                    e.setY(e.getY() + movingEnemyPixels);
                }

            } else if (e.getWay() == 4) {
                if ((e.getY() + enemyIcon.getHeight() - movingEnemyPixels) < h && (e.getY() + enemyIcon.getHeight() - movingEnemyPixels) > enemyIcon.getHeight()) {
                    e.setY(e.getY() - movingEnemyPixels);
                }
            }
        }
    }

    /**
     * Calculates the absolute distance between two objects
     *
     * @param x1 the x-coordinate of object 1
     * @param y1 the y-coordinate of object 1
     * @param x2 the x-coordinate of object 2
     * @param y2 the y-coordinate of object 2
     */
    public double spaceBetween(int x1, int y1, int x2, int y2) {
        int distX = Math.abs(x1 - x2);
        int distY = Math.abs(y1 - y2);
        return Math.sqrt((distX * distX) + (distY * distY));
    }

    /**
     * Creating a random number between min and max
     *
     * @param min Minium values of the return number
     * @param max Maximun values og the return number
     * @return
     */
    public static int randomMiser(int min, int max) {
        Random rand = new Random();
        return rand.nextInt(max - min + 1) + min;
    }

    public int updateTime() {
        counter++;
        return counter;
    }

    public void nextLevel() {
        goldCoins = new ArrayList<GoldCoin>();

        for (int i = 0; i < currentLevel.getNumberOfCoins(); i++) {
            GoldCoin g = new GoldCoin();
            goldCoins.add(g);
        }
        resetLevel();
    }

    public void isOnEnemy() {
        for (Enemy e : enemies) {
            if (spaceBetween(pacx, pacy, e.getX(), e.getY()) < 100) {
                gameOver = true;
            }
        }
    }

}
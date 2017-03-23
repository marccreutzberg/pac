package org.example.canvasdemo;

/**
 * Created by Marc Creutzberg on 23-03-2017.
 */

public class GoldCoin {
    private boolean isTaken = false;
    private int x;
    private int y;
    private boolean initialized = false;

    public GoldCoin(){

    }

    public boolean isTaken() {
        return isTaken;
    }

    public void setTaken(boolean taken) {
        isTaken = taken;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }
}
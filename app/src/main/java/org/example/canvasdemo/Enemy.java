package org.example.canvasdemo;

/**
 * Created by Marc Creutzberg on 27-03-2017.
 */

public class Enemy {
    private int x;
    private int y;
    private boolean initialized = false;
    private int way;

    public Enemy(int x, int y, boolean initialized, int way) {
        this.x = x;
        this.y = y;
        this.initialized = initialized;
        this.way = way;
    }

    public Enemy() {

    }

    public int getWay() {
        return way;
    }

    public void setWay(int way) {
        this.way = way;
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

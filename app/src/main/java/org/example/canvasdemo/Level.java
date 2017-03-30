package org.example.canvasdemo;

public class Level {
    private int duration;
    private int numberOfCoins;
    private String Name;

    public Level(int duration, int numberOfCoins, String name) {
        this.duration = duration;
        this.numberOfCoins = numberOfCoins;
        Name = name;
    }

    public Level() {
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getNumberOfCoins() {
        return numberOfCoins;
    }

    public void setNumberOfCoins(int numberOfCoins) {
        this.numberOfCoins = numberOfCoins;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}


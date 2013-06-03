package com.optimaize.titangraph.importbench.datasource;

/**
 */
public class GodSourceRecord {

    private final String name;
    private final int power;
    private final boolean alive;

    public GodSourceRecord(String name, int power, boolean alive) {
        if (name.isEmpty()) throw new IllegalArgumentException();
        this.name = name;
        this.power = power;
        this.alive = alive;
    }


    public String getName() {
        return name;
    }

    public int getPower() {
        return power;
    }

    public boolean isAlive() {
        return alive;
    }
}

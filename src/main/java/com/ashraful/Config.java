package com.ashraful;

public class Config {
    private static Config instance;

    int time = 0;

    private Config() {
    }

    static Config getInstance() {
        if(instance == null) {
            instance = new Config();
        }
        return instance;
    }

    public void reset() {
        time = 0;
    }
}

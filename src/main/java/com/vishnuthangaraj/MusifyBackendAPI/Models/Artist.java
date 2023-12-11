package com.vishnuthangaraj.MusifyBackendAPI.Models;

public class Artist {
    private String name;
    private int likes;

    // No-Args constructor
    Artist(){}

    // All-Args Constructor
    public Artist(String name, int likes) {
        this.name = name;
        this.likes = likes;
    }

    // Getters and Setters
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLikes() {
        return this.likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}

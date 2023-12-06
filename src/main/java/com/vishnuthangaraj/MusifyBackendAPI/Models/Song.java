package com.vishnuthangaraj.Musify.Models;

public class Song {
    private String name;
    private int length, likes;

    // No-Args constructor
    public Song() {
    }

    // All-Args Constructor
    public Song(String name, int length, int likes) {
        this.name = name;
        this.length = length;
        this.likes = likes;
    }

    // Getters and Setters
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLength() {
        return this.length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getLikes() {
        return this.likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}

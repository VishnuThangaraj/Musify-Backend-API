package com.vishnuthangaraj.Musify.Models;

public class Playlist {
    private String title;

    // No-Args constructor
    public Playlist() { }

    // All-Args Constructor
    public Playlist(String title) {
        this.title = title;
    }

    // Getters and Setters
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

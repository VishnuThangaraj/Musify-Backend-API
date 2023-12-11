package com.vishnuthangaraj.MusifyBackendAPI.Models;

import java.util.Date;

public class Album {
    private String title;
    private Date releaseDate;

    // No-Args constructor
    Album(){}

    // Title-Args Constructor
    public Album(String title) {
        this.title = title;
    }

    // All-Args Constructor
    public Album(String title, Date releaseDate) {
        this.title = title;
        this.releaseDate = releaseDate;
    }

    // Getters and Setters
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getReleaseDate() {
        return this.releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }
}

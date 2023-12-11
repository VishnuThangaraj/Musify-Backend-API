package com.vishnuthangaraj.MusifyBackendAPI.Controller;

import com.vishnuthangaraj.MusifyBackendAPI.Models.*;
import com.vishnuthangaraj.MusifyBackendAPI.Services.musicServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/music")
public class musicController {

    @Autowired
    musicServices musicServices;

    // Add user to the Database with given Name and MobileNumber
    // http://localhost:8080/music/add-user?name=vishnu&mobile=9876545678
    @PostMapping("/add-user")
    public String createUser(@RequestParam(name = "name")String name, String mobile){
        // Returns the newly Created user object
        User user = musicServices.createUser(name,mobile);
        return "User Created SuccessFully";
    }

    // Add new Artist to the Database with given Name
    // http://localhost:8080/music/add-artist?name=anirudh
    @PostMapping("/add-artist")
    public String createArtist(@RequestParam(name = "name")String name){
        // Returns the newly Created Artist object
        Artist artist = musicServices.createArtist(name);
        return "Artist Created SuccessFully";
    }

    // Create album with given title and artist
    // http://localhost:8080/music/add-album?title=kaithi&artistName=anirudh
    @PostMapping("/add-album")
    public String createAlbum(@RequestParam(name = "title")String title, String artistName){
        //If the artist does not exist, first create an artist with given name and add creates album
        Album album = musicServices.createAlbum(title, artistName);
        return "Album Created Successfully";
    }

    // Create and Add song to the respective Album
    // http://localhost:8080/music/add-song?name=ordinary_person&length=3&albumName=leo
    @PostMapping("/add-song")
    public String createSong(@RequestParam(name = "name") String name, int length, String albumName) throws Exception{
        // if the album does-Not exists , throws exception
        Song song = musicServices.createSong(name, length, albumName);
        return "Song Created Successfully";
    }

    // Create playlist with given title and add all songs on basis of length.
    @PostMapping("/add-playlist-on-length")
    public String createPlaylistOnLength(String mobile , String title, int length) throws Exception{
        // Create of the playlist will be the give user and will be the only listener at the time of playlist creation
        // Throws Exception if the user does not exist in the database
        Playlist playlist = musicServices.createPlaylistOnLength(mobile, title, length);
        return "Playlist Created Successfully with given Length";
    }

    //Create a playlist with given title and add all songs having the given titles in the database to that playlist
    @PostMapping("/add-playlist-on-name")
    public String createPlaylistOnName(String mobile , String title, List<String> songTitles) throws Exception{
        // Create of the playlist will be the give user and will be the only listener at the time of playlist creation
        //If the user does not exist, throw "User does not exist" exception
        Playlist playlist = musicServices.createPlaylistOnName(mobile, title, songTitles);
        return "Playlist Created Successfully with given List of Names";
    }

    ///Find the playlist with given title and add user as listener of that playlist and update user accordingly
    @PutMapping("/find-playlist")
    public String findPlaylist(String mobile, String playlistTitle) throws Exception{
        //If the user is Creator or already a listener, do nothing
        //If the user does not exist, throw "User does not exist" exception
        //If the playlist does not exist, throw "Playlist does not exist" exception
        Playlist playlist = musicServices.findPlaylist(mobile, playlistTitle);
        return "Playlist found";
    }

    // Like the song
    @PutMapping("/like-song")
    public String likeSong(String mobile, String songTitle) throws Exception{
        //The user likes the given song. The corresponding artist of the song gets auto-liked
        //A song can be liked by a user only once. If a user tried to like a song multiple times, do nothing
        //However, an artist can indirectly have multiple likes from a user, if the user has liked multiple songs of that artist.
        //If the user does not exist, throw "User does not exist" exception
        //If the song does not exist, throw "Song does not exist" exception
        Song song = musicServices.likeSong(mobile, songTitle);
        return "Song Liked Successfully";
    }

    // Find the popular Artist based on maximum likes
    // http://localhost:8080/music/popular-artist
    @GetMapping("/popular-artist")
    public String mostPopularArtist(){
        return musicServices.mostPopularArtist();
    }

    // Find the popular Song based on maximum likes
    // http://localhost:8080/music/popular-song
    @GetMapping("/popular-song")
    public String mostPopularSong(){
        return musicServices.mostPopularSong();
    }
}

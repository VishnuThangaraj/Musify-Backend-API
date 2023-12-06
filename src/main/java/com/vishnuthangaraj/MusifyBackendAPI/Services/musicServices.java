package com.vishnuthangaraj.Musify.Services;

import com.vishnuthangaraj.Musify.Models.*;
import com.vishnuthangaraj.Musify.Repository.musicRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
public class musicServices {

    @Autowired
    musicRepository musicRepository;

    // Create User with given Name and MobileNumber
    public User createUser (String name, String mobile){
        // create the user
        User user = new User(name, mobile);
        // add the user to database
        musicRepository.addUser(user);
        log.info("User created");
        return user;
    }

    // Create Artist with given Name
    public Artist createArtist(String name){
        // Create the Artist
        Artist artist = new Artist(name, 0);
        // add the artist to database
        musicRepository.addArtist(artist);
        log.info("Artist created");
        return artist;
    }

    // Create album with given title and artist
    public Album createAlbum(String title, String artistName){
        // Checks if the artist exist in the database
        Artist artist = getArtist(artistName);

        if(artist == null)
            // create artist and proceed if not exist in database
            artist = createArtist(artistName);

        Album album = new Album(title);

        musicRepository.addAlbum(album, artist);
        log.info("Album created");

        return album;
    }

    // Create song with given name and check the album exists
    public Song createSong(String name, int length, String albumName) throws Exception{
        // Check if the album exists in the database
        Album album = getAlbum(albumName);

        if(album == null){
            log.warn("Album Does not exists in the Database and Song is not created");
            throw new Exception("Album does not exists to add the Song");
        }

        Song song = new Song(name,length,0);

        // Add the song with respected album to Database
        musicRepository.addSong(song,album);

        return song;
    }

    // Create playlist on basis of Song length
    public Playlist createPlaylistOnLength(String mobile, String title, int length)throws Exception{
        User user = getUser(mobile);

        if(user == null){
            log.warn("User Does not exists in the Database and Playlist is not created");
            throw new Exception("User does not exists to create the Playlist");
        }

        Playlist playlist = new Playlist(title);

        // add the Playlist to the Database
        musicRepository.addPlaylist(playlist, user);

        // Create list of songs on basis of its length
        List<Song> playlistSongs = musicRepository.songs.stream().
                filter(song -> song.getLength() == length).toList();

        // Add List of Songs to the Playlist
        musicRepository.addSongsToPlaylist(playlist,playlistSongs);

        return playlist;
    }

    // Create Playlist on Basis of Song name
    public Playlist createPlaylistOnName(String mobile , String title, List<String> songTitles) throws Exception{
        User user = getUser(mobile);

        if(user == null){
            log.warn("User Does not exists in the Database and Playlist is not created");
            throw new Exception("User does not exists to create the Playlist");
        }

        Playlist playlist = new Playlist(title);

        // add the Playlist to the Database
        musicRepository.addPlaylist(playlist, user);

        // Create list of songs on basis of its Name
        List<Song> playlistSongs = musicRepository.songs.stream().
                filter(song -> songTitles.contains(song.getName())).toList();

        // Add List of Songs to the Playlist
        musicRepository.addSongsToPlaylist(playlist, playlistSongs);

        return playlist;
    }

    // Find Playlist from the Database and add user as listener
    public Playlist findPlaylist(String mobile, String playlistTitle) throws Exception{
        User user = getUser(mobile);

        if(user == null){
            log.warn("User Does not exists in the Database and Playlist is not created");
            throw new Exception("User does not exists to create the Playlist");
        }

        // Check if the Playlist Exists
        Playlist playlist = getPlaylist(playlistTitle);
        
        if(playlist == null){
            log.warn("User Does not exists in the Database");
            throw new Exception("Playlist does not exist in the Database");
        }

        // Check if the user is the creator or the listener of the playlist
        if(!musicRepository.checkUserIsListenerOrCreator(user, playlist)){
            // Add User as the listener
            musicRepository.addUserAsListener(user, playlist);
        }

        return playlist;
    }

    // Like a Song
    public Song likeSong(String mobile, String title) throws Exception{
        // Check if the User Exists
        User user = getUser(mobile);

        if(user == null){
            log.warn("User Does not exists in the Database and Playlist is not created");
            throw new Exception("User does not exists to create the Playlist");
        }

        // Check if the Song Exists
        Song song = getSong(title);

        if(song == null){
            log.warn("Song Does not exists in the Database");
            throw new Exception("Song does not exists to Like");
        }

        // Check if the Song is Already liked by the User
        if(musicRepository.checkUserLikedTheSong(user, song)){
            log.info("The user have already liked the song");
            return song;
        }

        // Like the Song and add user to song likes
        song.setLikes(song.getLikes()+1);
        musicRepository.addLikeToSong(user, song);
        // Increase the Like for the Artist of the Song
        musicRepository.addLikeToArtist(song);

        return song;
    }

    // Find the popular Artist based on maximum likes
    public String mostPopularArtist(){
        Artist popularArtist = musicRepository.artists.stream()
                .max(Comparator.comparingInt(Artist::getLikes)).orElse(null);

        if(popularArtist == null){
            log.warn("No Artists exists in the Database");
            return "No Artist Exists in the Database";
        }

        return "The popular Artist is : "+popularArtist.getName();
    }

    // Find the popular Song based on maximum likes
    public String mostPopularSong(){
        Song popularSong = musicRepository.songs.stream().
                max(Comparator.comparingInt(Song::getLikes)).orElse(null);

        if(popularSong == null){
            log.warn("No Song exists in the Database");
            return "No Song Exists in the Database";
        }

        return "The popular Song is : "+popularSong.getName();
    }

    // Validating Functions

    // Checks if the Artist exist in Database
    private Artist getArtist(String artistName){

        return musicRepository.artists.stream().
                filter(artist -> artist.getName().equals(artistName)).
                findFirst().orElse(null);
    }

    // Checks if the Album exist in the Database
    private Album getAlbum(String albumName){

        return musicRepository.albums.stream().
                filter(album -> album.getTitle().equals(albumName)).
                findFirst().orElse(null);
    }

    // Check if the User Exists in the Database
    private User getUser(String mobile){

        return musicRepository.users.stream().
                filter(user -> user.getMobileNumber().equals(mobile)).
                findFirst().orElse(null);
    }

    // Check if the Playlist Exists in the Database
    private Playlist getPlaylist(String title){
        
        return musicRepository.playlists.stream().
                filter(playlist -> playlist.getTitle().equals(title)).
                findFirst().orElse(null);
    }

    // Check if the Song Exists in the Database
    private Song getSong(String name){

        return musicRepository.songs.stream().
                filter(song -> song.getName().equals(name)).
                findFirst().orElse(null);
    }
}

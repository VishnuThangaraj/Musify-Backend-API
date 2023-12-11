package com.vishnuthangaraj.MusifyBackendAPI.Repository;

import com.vishnuthangaraj.MusifyBackendAPI.Models.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
@Slf4j
public class musicRepository {
    // HashMaps as Alternatives for Database
    public HashMap<Artist, List<Album>> artistAlbumMap;
    public HashMap<Album, List<Song>> albumSongMap;
    public HashMap<Playlist, List<Song>> playlistSongMap;
    public HashMap<Playlist, List<User>> playlistListenerMap;
    public HashMap<User, Playlist> creatorPlaylistMap;
    public HashMap<User, List<Playlist>> userPlaylistMap;
    public HashMap<Song, List<User>> songLikeMap;

    @Getter
    public List<User> users;
    @Getter
    public List<Song> songs;
    @Getter
    public List<Playlist> playlists;
    @Getter
    public List<Album> albums;
    @Getter
    public List<Artist> artists;

    // Constructor
    public musicRepository(){
        //To avoid hitting apis multiple times, initialize all the hashmaps here with some dummy data
        artistAlbumMap = new HashMap<>();
        albumSongMap = new HashMap<>();
        playlistSongMap = new HashMap<>();
        playlistListenerMap = new HashMap<>();
        creatorPlaylistMap = new HashMap<>();
        userPlaylistMap = new HashMap<>();
        songLikeMap = new HashMap<>();

        users = new ArrayList<>();
        songs = new ArrayList<>();
        playlists = new ArrayList<>();
        albums = new ArrayList<>();
        artists = new ArrayList<>();
    }

    // Add user to the Database
    public void addUser (User user){
        users.add(user);

        // user with no playlist
        userPlaylistMap.put(user, new ArrayList<>());
    }

    // Add Artist to the Database
    public void addArtist (Artist artist){
        artists.add(artist);

        // Artist with no albums
        artistAlbumMap.put(artist, new ArrayList<>());
    }

    // Add Album to the Database
    public void addAlbum(Album album, Artist artist){
        albums.add(album);

        // add album to the respected artist map
        artistAlbumMap.get(artist).add(album);
        // album with list of the songs
        albumSongMap.put(album,new ArrayList<>());
    }

    // Add Song to the Database
    public void addSong(Song song, Album album){
        songs.add(song);

        // add song to its album
        albumSongMap.get(album).add(song);
        // add to list of liked users for the song
        songLikeMap.put(song,new ArrayList<>());
    }

    // Add Playlist to Database
    public void addPlaylist(Playlist playlist, User user){
        playlists.add(playlist);

        playlistSongMap.put(playlist,new ArrayList<>());
        playlistListenerMap.put(playlist,new ArrayList<>());

        playlistListenerMap.get(playlist).add(user);   // current listener of the playlist
        creatorPlaylistMap.put(user,playlist);         // creator of the playlist
        userPlaylistMap.get(user).add(playlist);   // user and their list of playlists
    }

    // Add Songs to Playlist
    public void addSongsToPlaylist(Playlist playlist, List<Song> playlistSongs){
        playlistSongs.forEach(song -> playlistSongMap.get(playlist).add(song));
    }

    // Check the User is the Creator or the Listener of the Playlist
    public boolean checkUserIsListenerOrCreator(User user, Playlist playlist){
        return creatorPlaylistMap.containsKey(user) && creatorPlaylistMap.get(user) == playlist ||
                playlistListenerMap.get(playlist).contains(user);
    }

    // Add User as Listener to the Playlist
    public void addUserAsListener(User user, Playlist playlist){
        // Add the User as Listener of the Playlist
        playlistListenerMap.get(playlist).add(user);

        // Add the playlist to the Users Playlist
        if(!userPlaylistMap.get(user).contains(playlist)){
            userPlaylistMap.get(user).add(playlist);
        }
    }

    // Check the User liked the song already
    public boolean checkUserLikedTheSong(User user, Song song){
        return songLikeMap.get(song).contains(user);
    }

    // Add Like to the Song with the mentioned user
    public void addLikeToSong(User user, Song song){
        songLikeMap.get(song).add(user);
    }

    // Add Like to the Artist of the Song
    public void addLikeToArtist(Song song){

        for(Album album : albumSongMap.keySet()){
            if(albumSongMap.get(album).contains(song)){
                artistAlbumMap.keySet().stream().
                        filter(artist -> artistAlbumMap.get(artist).contains(album)).
                        findFirst().ifPresent(artist -> artist.setLikes(artist.getLikes() + 1));
            }
        }
    }
}

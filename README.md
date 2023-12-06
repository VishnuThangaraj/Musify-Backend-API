# Spotify Music Application Backend API

[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)
[![Java Version](https://img.shields.io/badge/java-11%2B-blue.svg)](https://openjdk.java.net/projects/jdk/11/)

## Overview

This repository contains the backend API for a Spotify-inspired music application built with Spring Boot. The API provides endpoints for searching tracks, managing playlists, and interacting with the Spotify API.

## Features

- **Track Search:** Search for tracks based on a query.
- **Track Details:** Retrieve detailed information about a specific track.
- **User Playlists:** Get a list of user playlists.
- **Create Playlist:** Create a new playlist.
- **Add Track to Playlist:** Add a track to a user's playlist.

## Prerequisites

Before you begin, ensure you have the following installed:

- Java 11 or later
- Maven
- Spotify Developer Account and API Key
- MongoDB (or another database of your choice)

## Getting Started

### Installation

1. Clone the repository:

    ```bash
    git clone https://github.com/your-username/spotify-music-backend.git
    cd spotify-music-backend
    ```

2. Build the project:

    ```bash
    mvn clean install
    ```

### Configuration

1. Create a Spotify Developer account and obtain your API key.

2. Configure your application properties:

    ```yaml
    # src/main/resources/application.yml

    spring:
      data:
        mongodb:
          uri: mongodb://localhost:27017/spotify
      security:
        oauth2:
          client:
            registration:
              spotify:
                clientId: <your-client-id>
                clientSecret: <your-client-secret>
                redirectUri: "{baseUrl}/login/oauth2/code/{registrationId}"
                scope:
                  - user-read-email
                  - user-library-read
                  - playlist-read-private
            provider:
              spotify:
                authorizationUri: https://accounts.spotify.com/authorize
                tokenUri: https://accounts.spotify.com/api/token
                userInfoUri: https://api.spotify.com/v1/me
    ```

## Usage

### Authentication

To authenticate with the Spotify API, follow the OAuth 2.0 authentication flow.

### Endpoints

- **Search for Tracks:**

    ```http
    GET /api/tracks?q={query}
    ```

- **Get Track Details:**

    ```http
    GET /api/tracks/{trackId}
    ```

- **Get User Playlists:**

    ```http
    GET /api/playlists
    ```

- **Create Playlist:**

    ```http
    POST /api/playlists
    ```

- **Add Track to Playlist:**

    ```http
    POST /api/playlists/{playlistId}/tracks/{trackId}
    ```

## Contributing

Contributions are welcome! Please follow the [contribution guidelines](CONTRIBUTING.md).

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

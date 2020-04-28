package com.tonikolaba.springvuejsXstarter.dto;

import com.tonikolaba.springvuejsXstarter.model.Album;

import java.util.UUID;

public class AlbumDTO {
    public UUID id;
    public String title;
    public String artist;
    public Integer releaseYear;
    public String coverURL;

    public AlbumDTO() {}

    public AlbumDTO(Album album) {
        this.id = album.getId();
        this.title = album.getTitle();
        this.artist = album.getArtist();
        this.releaseYear = album.getReleaseYear();
        this.coverURL = album.getCoverURL();
    }

    public Album asAlbum() {
        return new Album(
                this.title,
                this.artist,
                this.releaseYear,
                this.coverURL
        );
    }
}

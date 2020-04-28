package com.tonikolaba.springvuejsXstarter.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.net.URL;
import java.util.UUID;

@Document(
        indexName="album",
        replicas = 0
)
public class Album {
    @Id
    private UUID id;

    @Field(type = FieldType.Text)
    private String title;
    @Field(type = FieldType.Keyword)
    private String artist;
    @Field(type = FieldType.Integer)
    private Integer releaseYear;
    @Field(type = FieldType.Keyword)
    private String coverURL;

    public Album() {
    }

    public Album(String title, String artist, Integer releaseYear, String coverURL) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.artist = artist;
        this.releaseYear = releaseYear;
        this.coverURL = coverURL;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getCoverURL() {
        return coverURL;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public void setCoverURL(String coverURL) {
        this.coverURL = coverURL;
    }
}

package com.tonikolaba.springvuejsXstarter.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.net.URL;
import java.util.Date;
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
    @Field(type = FieldType.Date)
    private Date releaseDate;
    @Field(type = FieldType.Keyword)
    private URL coverURL;

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

    public URL getCoverURL() {
        return coverURL;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setCoverURL(URL coverURL) {
        this.coverURL = coverURL;
    }
}

package com.semmet.spring.lyricsapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SongLyrics {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private String artist;
    @Column(columnDefinition = "TEXT")
    private String alternateTitles;
    @Column(columnDefinition = "TEXT")
    private String lyrics;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return this.artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlternateTitles() {
        return this.alternateTitles;
    }

    public void setAlternateTitles(String alternateTitles) {
        this.alternateTitles = alternateTitles;
    }

    public String getLyrics() {
        return this.lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }
}

package com.semmet.spring.lyricsapi.repository;

import java.util.ArrayList;

import com.semmet.spring.lyricsapi.model.SongLyrics;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SongLyricsRepository extends CrudRepository<SongLyrics, Long> {
    
    @Query(value="select * from song_lyrics sl where sl.title like :title or sl.alternate_titles like %:title2% and sl.artist like :artist", nativeQuery=true)
    ArrayList<SongLyrics> getLyricsByTitleOrAltTitle(@Param("title") String title, @Param("title2") String title2, @Param("artist") String artist);
}
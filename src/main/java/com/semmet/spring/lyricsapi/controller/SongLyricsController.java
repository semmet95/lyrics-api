package com.semmet.spring.lyricsapi.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import com.semmet.spring.lyricsapi.exception.GlobalControllerExceptionHandler;
import com.semmet.spring.lyricsapi.exception.InvalidArgumentExceptionHandler;
import com.semmet.spring.lyricsapi.model.SongLyrics;
import com.semmet.spring.lyricsapi.repository.SongLyricsRepository;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/lyrics-api")
public class SongLyricsController {

    @Autowired
    SongLyricsRepository songLyricsRepository;

    //private static final Logger LOGGER = LoggerFactory.getLogger(SongLyricsController.class);
    private static final String LYRICS_SCRAPER_ENDPOINT = "http://130.211.43.188/lyrics-scraper";

    @GetMapping(produces = "application/json")
    public SongLyrics getSongLyrics(@RequestParam String artist, @RequestParam String track) throws URISyntaxException {

        //LOGGER.info("get request received with params:: artist = " + artist + "; track = " + track);

        ArrayList<SongLyrics> matchedSongs = songLyricsRepository.getLyricsByTitleOrAltTitle(track, track, artist);
        if(matchedSongs == null || matchedSongs.isEmpty()) {

            RestTemplate restTemplate = new RestTemplate();
            String lyricsEndpoint = String.format("%s?artist=%s&track=%s",
                                    LYRICS_SCRAPER_ENDPOINT,
                                    URLEncoder.encode(artist, StandardCharsets.UTF_8),
                                    URLEncoder.encode(track, StandardCharsets.UTF_8));
            URI uri = new URI(lyricsEndpoint);
            ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);

            if(result.getStatusCode() != HttpStatus.OK || result.getBody() == null) {
                throw new GlobalControllerExceptionHandler();
            }
            else if(result.getBody().contains("please add")) {
                throw new InvalidArgumentExceptionHandler();
            }
            
            SongLyrics songLyrics = new SongLyrics();
            songLyrics.setAlternateTitles(track);
            songLyrics.setArtist(artist);
            songLyrics.setLyrics(result.getBody());
            songLyrics.setTitle(track);

            songLyricsRepository.save(songLyrics);

            // check the updated DB
            matchedSongs = songLyricsRepository.getLyricsByTitleOrAltTitle(track, track, artist);

            if(matchedSongs == null || matchedSongs.isEmpty()) {
                throw new GlobalControllerExceptionHandler();
            }
            else {
                return songLyrics;
            }
        }
        else {
            return matchedSongs.get(0);
        }

    }
}

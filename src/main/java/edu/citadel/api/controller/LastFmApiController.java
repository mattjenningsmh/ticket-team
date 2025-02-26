package edu.citadel.api.controller;

import edu.citadel.service.LastFmApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lastfm")
public class LastFmApiController {
    private final LastFmApiService lastFmApiService;

    @Autowired
    public LastFmApiController(LastFmApiService lastFmApiService) {
        this.lastFmApiService = lastFmApiService;
    }

    @GetMapping("/artist")
    public ResponseEntity<String> getArtistInfo(@RequestParam String artist) {
        String data = lastFmApiService.getArtistInfo(artist);
        return ResponseEntity.ok(data);
    }
}
package com.tonikolaba.springvuejsXstarter.controller;

import com.tonikolaba.springvuejsXstarter.dto.AlbumDTO;
import com.tonikolaba.springvuejsXstarter.model.Album;
import com.tonikolaba.springvuejsXstarter.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/albums")
public class AlbumController {

    @Autowired
    private AlbumRepository albumRepository;

    @GetMapping("/{id}")
    public AlbumDTO getById(@PathVariable("id") UUID id) {
        return new AlbumDTO(this.albumRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Album Not Found")));
    }

    @PostMapping
    public AlbumDTO save(@RequestBody AlbumDTO data) {
        if (data.title == null || data.artist == null || data.releaseYear == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing one of required fields");
        }
        if (data.coverURL != null) {
            try {
                new URL(data.coverURL);
            } catch (MalformedURLException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Given coverURL is not a valid URL");
            }
        }
        Album toSave = data.asAlbum();
        toSave.setId(UUID.randomUUID());
        return new AlbumDTO(
                this.albumRepository.save(data.asAlbum())
        );
    }
}

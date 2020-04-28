package com.tonikolaba.springvuejsXstarter.controller;

import com.tonikolaba.springvuejsXstarter.dto.AlbumDTO;
import com.tonikolaba.springvuejsXstarter.dto.SearchQuery;
import com.tonikolaba.springvuejsXstarter.model.Album;
import com.tonikolaba.springvuejsXstarter.repository.AlbumRepository;
import com.tonikolaba.springvuejsXstarter.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/albums")
public class AlbumController {

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private AlbumService albumService;

    @GetMapping("/{id}")
    public AlbumDTO getById(@PathVariable("id") UUID id) {
        return new AlbumDTO(this.albumRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Album Not Found")));
    }

    @PostMapping
    public AlbumDTO save(@RequestBody AlbumDTO data) {
        try {
            Album saved = this.albumService.save(data.asAlbum());
            return new AlbumDTO(saved);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        }
    }

    @PostMapping("/search")
    public Page<AlbumDTO> search(@RequestBody SearchQuery query) {
        return this.albumService.search(query).map(AlbumDTO::new);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") UUID id) {
        this.albumRepository.deleteById(id);
    }
}

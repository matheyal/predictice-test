package com.tonikolaba.springvuejsXstarter.service;

import com.tonikolaba.springvuejsXstarter.AbstractSpringTest;
import com.tonikolaba.springvuejsXstarter.dto.SearchQuery;
import com.tonikolaba.springvuejsXstarter.model.Album;
import com.tonikolaba.springvuejsXstarter.repository.AlbumRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AlbumServiceTest extends AbstractSpringTest {

    @Autowired
    private AlbumService service;

    @Autowired
    private AlbumRepository repository;

    @Before
    @After
    public void clean() {
        this.repository.deleteAll();
        this.repository.refresh();
    }

    @Test
    public void testSave() {
        Album album1 = mockAlbum("Title", "Artist");
        this.service.save(album1);
        assertEquals(1, repository.count());
        // Missing required attribute should throw exception
        assertThrows(IllegalArgumentException.class, () -> {
            Album album = mockAlbum(null, "Artist");
            this.service.save(album);
        });
        // Invalid cover url should throw exception
        assertThrows(IllegalArgumentException.class, () -> {
            Album album = mockAlbum("Title", "Artist1");
            album.setCoverURL("not-a-url");
            this.service.save(album);
        });
        // Nothing else saved
        assertEquals(1, repository.count());
    }

    @Test
    public void testSearch() {
        Album album1 = this.service.save(mockAlbum("Never Gonna Give You Up", "Rick Astley"));
        Album album2 = this.service.save(mockAlbum("Beat It", "Mickael Jackson"));
        // Empty search returns everything
        Page<Album> results = this.service.search(buildSearchQuery("", null));
        assertEquals(2, results.getTotalElements());

        // Simple search query
        results = this.service.search(buildSearchQuery("Rick", null));
        assertEquals(1, results.getTotalElements());
        assertEquals(album1.getId(), results.getContent().get(0).getId());

        // Search limited on ids
        Album album3 = this.service.save(mockAlbum("Billie Jean", "Mickael Jackson"));
        results = this.service.search(
                buildSearchQuery(
                        "Jackson",
                        Set.of(album1.getId(), album3.getId())
                )
        );
        assertEquals(1, results.getTotalElements());
        assertEquals(album3.getId(), results.getContent().get(0).getId());
    }

    private static Album mockAlbum(String title, String artist) {
        return new Album(title, artist, 2000, null);
    }

    private static SearchQuery buildSearchQuery(String textQuery, Set<UUID> ids) {
        SearchQuery query = new SearchQuery();
        query.query = textQuery;
        query.ids = ids;
        query.page = 0;
        query.pageSize = 10;
        return query;
    }
}
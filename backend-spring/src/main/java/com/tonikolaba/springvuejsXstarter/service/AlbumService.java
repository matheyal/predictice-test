package com.tonikolaba.springvuejsXstarter.service;

import com.tonikolaba.springvuejsXstarter.model.Album;
import com.tonikolaba.springvuejsXstarter.repository.AlbumRepository;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

@Service
public class AlbumService {

    @Autowired
    private AlbumRepository albumRepository;

    public Album save(Album album) {
        if (album.getTitle() == null || album.getArtist() == null || album.getReleaseYear() == null) {
            throw new IllegalArgumentException("Missing required fields");
        }
        if (album.getCoverURL() != null) {
            try {
                new URL(album.getCoverURL());
            } catch (MalformedURLException e) {
                throw new IllegalArgumentException("cover URL is not a valid URL");
            }
        }
        album.setId(UUID.randomUUID());
        return this.albumRepository.save(album);
    }

    public Page<Album> search(String query) {
        QueryBuilder searchQuery = QueryBuilders.boolQuery().must(
                QueryBuilders.queryStringQuery(query)
                        .defaultOperator(Operator.AND)
                        .field("id")
                        .field("title")
                        .field("artist")
        );

        return this.albumRepository.search(searchQuery, PageRequest.of(0, 20));
    }

}

package com.tonikolaba.springvuejsXstarter.service;

import com.tonikolaba.springvuejsXstarter.dto.SearchQuery;
import com.tonikolaba.springvuejsXstarter.model.Album;
import com.tonikolaba.springvuejsXstarter.repository.AlbumRepository;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.search.MatchQuery;
import org.elasticsearch.index.search.MultiMatchQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

@Service
public class AlbumService {

    private static final int MAX_PAGE_SIZE = 30;

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

    public Page<Album> search(SearchQuery query) {
        QueryBuilder textQuery;
        if (query.query.isEmpty()) {
            textQuery = QueryBuilders.matchAllQuery();
        } else {
            textQuery = QueryBuilders.multiMatchQuery(query.query)
                    .fuzziness("AUTO")
                    .prefixLength(2)
                    .field("title")
                    .field("artist");
        }
        QueryBuilder searchQuery = QueryBuilders.boolQuery()
                .must(textQuery);
        int page = Math.max(query.page, 0);
        int pageSize = Math.min(query.pageSize, MAX_PAGE_SIZE);
        return this.albumRepository.search(searchQuery, PageRequest.of(page, pageSize));
    }

}
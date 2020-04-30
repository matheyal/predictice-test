package com.tonikolaba.springvuejsXstarter.service;

import com.tonikolaba.springvuejsXstarter.dto.SearchQuery;
import com.tonikolaba.springvuejsXstarter.model.Album;
import com.tonikolaba.springvuejsXstarter.repository.AlbumRepository;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
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
        if (album.getCoverURL() != null && !album.getCoverURL().isEmpty()) {
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
        if (query.query == null || query.query.isEmpty()) {
            textQuery = QueryBuilders.matchAllQuery();
        } else {
            textQuery = QueryBuilders.multiMatchQuery(query.query)
                    .fuzziness("AUTO")
                    .prefixLength(2)
                    .field("title")
                    .field("artist");
        }
        //
        QueryBuilder idsQuery;
        if (query.ids == null || query.ids.isEmpty()) {
            idsQuery = QueryBuilders.matchAllQuery();
        } else {
            idsQuery = QueryBuilders.idsQuery()
                    .addIds(query.ids.stream()
                            .map(UUID::toString)
                            .toArray(String[]::new)
                    );
        }
        BoolQueryBuilder searchQuery = QueryBuilders.boolQuery()
                .must(textQuery);
        if (query.ids != null) {
            String[] ids = query.ids.stream()
                    .map(UUID::toString)
                    .toArray(String[]::new);
            searchQuery.filter(
                    QueryBuilders.idsQuery()
                            .addIds(ids)
            );
        }
        int page = Math.max(query.page, 0);
        int pageSize = Math.min(query.pageSize, MAX_PAGE_SIZE);
        return this.albumRepository.search(searchQuery, PageRequest.of(page, pageSize));
    }

}

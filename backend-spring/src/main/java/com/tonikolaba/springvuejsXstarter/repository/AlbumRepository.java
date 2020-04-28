package com.tonikolaba.springvuejsXstarter.repository;

import com.tonikolaba.springvuejsXstarter.model.Album;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.UUID;

public interface AlbumRepository extends ElasticsearchRepository<Album, UUID> {

}

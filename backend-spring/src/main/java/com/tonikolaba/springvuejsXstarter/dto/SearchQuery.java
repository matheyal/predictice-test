package com.tonikolaba.springvuejsXstarter.dto;

import java.util.Set;
import java.util.UUID;

public class SearchQuery {
    public String query;
    public Set<UUID> ids;
    public int page;
    public int pageSize = 10;
}

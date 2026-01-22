package com.fran.ticketing_api.util;

import com.fran.ticketing_api.dto.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public final class PageResponseMapper {

    private PageResponseMapper() {}

    public static <T> PageResponse<T> from (Page<T> page){
        var meta = new PageResponse.Meta(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast()
        );


        String self = currentUrl(page.getNumber(), page.getSize());
        String next = page.hasNext() ? currentUrl(page.getNumber() + 1, page.getSize() ): null;
        String prev = page.hasPrevious() ? currentUrl(page.getNumber() - 1 , page.getSize()) : null;

        var links = new PageResponse.Links(self,next,prev);

        return new PageResponse<>(page.getContent(), meta, links);
    }


    private static String currentUrl(int page, int size) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .replaceQueryParam("page", page)
                .replaceQueryParam("size", size)
                .toUriString();
    }
}

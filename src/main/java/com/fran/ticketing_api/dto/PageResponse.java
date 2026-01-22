package com.fran.ticketing_api.dto;



import java.util.List;

public record PageResponse<T>(
        List<T> data,
        Meta meta,
        Links links
) {

    public record Meta(
            int page,
            int size,
            long totalElements,
            int totalPage,
            boolean firts,
            boolean last
    ){}

    public record Links(
            String self,
            String next,
            String prev
    ){}

}

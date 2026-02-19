package com.fran.ticketing_api.dto;



import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Respuesta paginada genérica")
public record PageResponse<T>(
        List<T> data,
        Meta meta,
        Links links
) {

    @Schema(description = "Metadatos de paginación")
    public record Meta(
            @Schema(description = "Número de página actual", example = "0") int page,
            @Schema(description = "Tamaño de página", example = "20") int size,
            @Schema(description = "Total de elementos", example = "125") long totalElements,
            @Schema(description = "Total de páginas", example = "7") int totalPage,
            @Schema(description = "Es la primera página", example = "true") boolean firts,
            @Schema(description = "Es la última página", example = "false") boolean last
    ){}

    @Schema(description = "Enlaces de navegación")
    public record Links(
            @Schema(description = "Enlace a la página actual") String self,
            @Schema(description = "Enlace a la siguiente página") String next,
            @Schema(description = "Enlace a la página previa") String prev
    ){}

}

package com.fran.ticketing_api.controller;

import com.fran.ticketing_api.dto.*;
import com.fran.ticketing_api.entitie.TicketComment;
import com.fran.ticketing_api.service.ITicketCommentService;
import com.fran.ticketing_api.util.PageResponseMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/api/tickets/{ticketId}/comments")
public class CommentController {

    @Autowired
    private ITicketCommentService commentService;

    private TicketCommentResponse toResponse(TicketComment comment) {
        Long authorId = comment.getAuthor() != null ? comment.getAuthor().getId(): null;
        Long ticketId = comment.getTicket() != null ? comment.getTicket().getId() : null;
        return new TicketCommentResponse(
                comment.getId(),
                ticketId,
                authorId,
                comment.getComment(),
                comment.getCreatedAt()
        );
    }


    @Operation(summary = "Crear comentario", description = "Añade un comentario a un ticket")
    @ApiResponse(responseCode = "201", description = "Comentario creado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TicketCommentResponse.class)))
    @PostMapping()
    public ResponseEntity<TicketCommentResponse> created(@PathVariable Long ticketId, @Valid  @RequestBody CreateCommentRequest req) {
        TicketComment comment = commentService.create(ticketId, req);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(comment.getId())
                .toUri();
        return ResponseEntity.created(location).body(toResponse(comment));
    }



    @Operation(summary = "Obtener comentario por id", description = "Devuelve un comentario por su id")
    @ApiResponse(responseCode = "200", description = "Comentario encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TicketCommentResponse.class)))
    @GetMapping("/{id}")
    public ResponseEntity<TicketCommentResponse> findById(@PathVariable("ticketId") Long ticketId, @PathVariable("id") Long id) {

        return ResponseEntity.ok(toResponse(commentService.findById(ticketId,id)));

    }


    @Operation(summary = "Actualizar comentario", description = "Actualiza un comentario")
    @ApiResponse(responseCode = "200", description = "Comentario actualizado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TicketCommentResponse.class)))
    @PatchMapping("/{id}")
    public ResponseEntity<TicketCommentResponse> update(@PathVariable("ticketId") Long ticketId, @PathVariable("id") Long id, @Valid  @RequestBody UpdateCommentRequest req) {
        TicketComment comment = commentService.update(ticketId,id, req);
        return ResponseEntity.ok(toResponse(comment));
    }


    @Operation(summary = "Eliminar comentario", description = "Elimina un comentario por id")
    @ApiResponse(responseCode = "200", description = "Comentario eliminado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DeleteResponse.class)))
    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteResponse> delete(@PathVariable Long ticketId,@PathVariable Long id) {
        commentService.delete(ticketId,id);
        return ResponseEntity.ok(new DeleteResponse("Comment deleted successfully", id));
    }


    @Operation(summary = "Listar comentarios", description = "Listar comentarios de un ticket (paginado)")
    @ApiResponse(responseCode = "200", description = "Listado paginado", content = @Content(mediaType = "application/json"))
    @GetMapping()
    public ResponseEntity<PageResponse<TicketCommentResponse>> findAll(
            @PathVariable Long ticketId,
            @RequestParam(required = false) Long author_id,
            Pageable pageable) {

        Page<TicketCommentResponse> page = commentService.search(author_id, ticketId, pageable)
                .map(this::toResponse);

        return ResponseEntity.ok(PageResponseMapper.from(page));
    }
}

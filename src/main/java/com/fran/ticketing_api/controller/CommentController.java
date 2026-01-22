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



    @GetMapping("/{id}")
    public ResponseEntity<TicketCommentResponse> findById(@PathVariable("ticketId") Long ticketId, @PathVariable("id") Long id) {

        return ResponseEntity.ok(toResponse(commentService.findById(ticketId,id)));

    }


    @PatchMapping("/{id}")
    public ResponseEntity<TicketCommentResponse> update(@PathVariable("ticketId") Long ticketId, @PathVariable("id") Long id, @Valid  @RequestBody UpdateCommentRequest req) {
        TicketComment comment = commentService.update(ticketId,id, req);
        return ResponseEntity.ok(toResponse(comment));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteResponse> delete(@PathVariable Long ticketId,@PathVariable Long id) {
        commentService.delete(ticketId,id);
        return ResponseEntity.ok(new DeleteResponse("Comment deleted successfully", id));
    }


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

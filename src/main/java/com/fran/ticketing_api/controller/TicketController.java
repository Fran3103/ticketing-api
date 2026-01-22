package com.fran.ticketing_api.controller;

import com.fran.ticketing_api.dto.*;
import com.fran.ticketing_api.entitie.Priority;
import com.fran.ticketing_api.entitie.Status;
import com.fran.ticketing_api.entitie.Ticket;
import com.fran.ticketing_api.entitie.TicketComment;
import com.fran.ticketing_api.service.ITicketCommentService;
import com.fran.ticketing_api.service.ITicketService;
import com.fran.ticketing_api.util.PageResponseMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {


    @Autowired
    private ITicketService ticketService;

    @Autowired
    private ITicketCommentService commentService;


    private TicketResponse toResponse (Ticket ticket) {
        Long assignedId = ticket.getAssignee() != null ? ticket.getAssignee().getId() : null;
        return new TicketResponse(
                ticket.getId(),
                ticket.getTitle(),
                ticket.getDescription(),
                ticket.getStatus(),
                ticket.getPriority(),
                assignedId,
                ticket.getCreatedAt()
        );
    }

    @PostMapping()
    public ResponseEntity<TicketResponse> createTicket(@Valid  @RequestBody CreateTicketRequest req) {
        Ticket createdTicket = ticketService.create(req);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdTicket.getId())
                .toUri();

        return ResponseEntity.created(location).body(toResponse(createdTicket));

    }


    @GetMapping("/{id}")
    public ResponseEntity<TicketDetailResponse> findTicketById (@PathVariable Long id) {

        return ResponseEntity.ok(ticketService.findDetail(id));

    }


    @PatchMapping("/{id}")
    public ResponseEntity<TicketResponse> updateTicket (@PathVariable Long id, @RequestBody UpdateTicketRequest req) {

        Ticket updatedTicket = ticketService.update(id, req);
        return ResponseEntity.ok(toResponse(updatedTicket));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteResponse> deleteTicket (@PathVariable Long id) {

        ticketService.delete(id);
        return ResponseEntity.ok(new DeleteResponse("Ticket deleted successfully", id));

        }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TicketResponse> updateTicketStatus (@PathVariable Long id, @RequestParam Status status) {

        Ticket updatedTicket = ticketService.updateStatus(id, status);
        return ResponseEntity.ok(toResponse(updatedTicket));

    }

    @GetMapping()
    public ResponseEntity<PageResponse<TicketResponse>> findAll(
           @RequestParam(required = false) Long assigneeId,
           @RequestParam(required = false) Priority priority,
           @RequestParam(required = false) Status status,
           @RequestParam(required = false) String q,
           Pageable pageable
     ) {

        Page<TicketResponse> page = ticketService.search(assigneeId,priority,status,q,pageable)
                .map(this::toResponse);


        return ResponseEntity.ok(PageResponseMapper.from(page));


    }




}

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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

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

    @Operation(summary = "Crear ticket", description = "Crea un nuevo ticket")
    @ApiResponse(responseCode = "201", description = "Ticket creado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TicketResponse.class)))
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


    @Operation(summary = "Obtener ticket por id", description = "Devuelve el detalle de un ticket por su id")
    @ApiResponse(responseCode = "200", description = "Ticket encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TicketDetailResponse.class)))
    @GetMapping("/{id}")
    public ResponseEntity<TicketDetailResponse> findTicketById (@PathVariable Long id) {

        return ResponseEntity.ok(ticketService.findDetail(id));

    }


    @Operation(summary = "Actualizar ticket", description = "Actualiza campos de un ticket")
    @ApiResponse(responseCode = "200", description = "Ticket actualizado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TicketResponse.class)))
    @PatchMapping("/{id}")
    public ResponseEntity<TicketResponse> updateTicket (@PathVariable Long id, @RequestBody UpdateTicketRequest req) {

        Ticket updatedTicket = ticketService.update(id, req);
        return ResponseEntity.ok(toResponse(updatedTicket));

    }

    @Operation(summary = "Eliminar ticket", description = "Elimina un ticket por id")
    @ApiResponse(responseCode = "200", description = "Ticket eliminado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DeleteResponse.class)))
    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteResponse> deleteTicket (@PathVariable Long id) {

        ticketService.delete(id);
        return ResponseEntity.ok(new DeleteResponse("Ticket deleted successfully", id));

        }

    @Operation(summary = "Actualizar estado del ticket", description = "Actualiza solo el estado del ticket")
    @ApiResponse(responseCode = "200", description = "Estado actualizado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TicketResponse.class)))
    @PatchMapping("/{id}/status")
    public ResponseEntity<TicketResponse> updateTicketStatus (@PathVariable Long id, @RequestParam Status status) {

        Ticket updatedTicket = ticketService.updateStatus(id, status);
        return ResponseEntity.ok(toResponse(updatedTicket));

    }

    @Operation(summary = "Listar tickets", description = "Listar tickets con filtros y paginación")
    @ApiResponse(responseCode = "200", description = "Listado paginado", content = @Content(mediaType = "application/json"))
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

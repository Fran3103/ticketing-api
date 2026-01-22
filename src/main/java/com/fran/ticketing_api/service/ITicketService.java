package com.fran.ticketing_api.service;

import com.fran.ticketing_api.dto.CreateTicketRequest;
import com.fran.ticketing_api.dto.TicketDetailResponse;
import com.fran.ticketing_api.dto.TicketResponse;
import com.fran.ticketing_api.dto.UpdateTicketRequest;
import com.fran.ticketing_api.entitie.Priority;
import com.fran.ticketing_api.entitie.Status;
import com.fran.ticketing_api.entitie.Ticket;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ITicketService {

    Ticket create(CreateTicketRequest ticket);

    Ticket update(Long id, UpdateTicketRequest ticket);

    Ticket updateStatus(Long id, Status status);

    Ticket findById(Long id);

    void delete(Long id);

    Page<Ticket> search(Long assigneeId, Priority priority, Status status,String q, Pageable pageable);

    TicketDetailResponse findDetail(Long id);
}

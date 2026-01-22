package com.fran.ticketing_api.service;

import com.fran.ticketing_api.dto.CreateCommentRequest;
import com.fran.ticketing_api.dto.UpdateCommentRequest;
import com.fran.ticketing_api.entitie.TicketComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ITicketCommentService {



    TicketComment create(Long ticketId, CreateCommentRequest  request);

    TicketComment  update(Long ticektId, Long id, UpdateCommentRequest request);

    void delete(Long ticketId, Long id);

    Page<TicketComment> search( Long author_id,Long ticket_id, Pageable pageable );

    TicketComment findById(Long ticketId, Long id);




}

package com.fran.ticketing_api.service.impl;

import com.fran.ticketing_api.dto.CreateCommentRequest;
import com.fran.ticketing_api.dto.UpdateCommentRequest;
import com.fran.ticketing_api.entitie.*;
import com.fran.ticketing_api.exception.ResourceNotFoundException;
import com.fran.ticketing_api.repository.ITicketCommentRepository;
import com.fran.ticketing_api.repository.ITicketRepository;
import com.fran.ticketing_api.repository.IUserRepository;
import com.fran.ticketing_api.service.ITicketCommentService;
import com.fran.ticketing_api.spec.CommentSpecifications;
import com.fran.ticketing_api.spec.TicketSpecifications;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class TicketCommentServiceImpl implements ITicketCommentService {

    @Autowired
    private ITicketCommentRepository commentRepo;

    @Autowired
    private ITicketRepository ticketRepo;

    @Autowired
    private IUserRepository userRepo;


    @Override
    public TicketComment create(Long ticketId, CreateCommentRequest request) {

        TicketComment comment = new TicketComment();



        Ticket ticket = ticketRepo.findById(ticketId)
                    .orElseThrow(() -> new ResourceNotFoundException("Ticket " + ticketId + " not found"));



       User author = userRepo.findById(request.author())
                    .orElseThrow(() -> new ResourceNotFoundException("User " + request.author() + " not found"));


        comment.setAuthor(author);
        comment.setTicket(ticket);
        comment.setComment(request.comment());
        return commentRepo.save(comment);

    }

    @Override
    public TicketComment update(Long ticketId,Long id, UpdateCommentRequest request) {
        TicketComment comment = commentRepo.findByIdAndTicket_Id(id, ticketId )
                .orElseThrow(() -> new ResourceNotFoundException("Comment " + id + " not found for ticket " + ticketId));

        comment.setComment(request.comment() != null  ? request.comment() : comment.getComment());
        return commentRepo.save(comment);


    }

    @Override
    public void delete(Long ticketId, Long id) {
       TicketComment comment = commentRepo.findByIdAndTicket_Id(id, ticketId)
               .orElseThrow(()-> new ResourceNotFoundException("Comment " + id+ " not found for ticket " + ticketId));

        commentRepo.delete(comment);
    }

    @Override
    public Page<TicketComment> search(Long author_id, Long ticket_id, Pageable pageable) {
        Specification<TicketComment> spec = Specification
                .where(CommentSpecifications.hasAuthor(author_id))
                .and(CommentSpecifications.hasTicketId(ticket_id));

        return commentRepo.findAll(spec,pageable);


    }

    @Override
    public TicketComment findById(Long ticketId, Long id) {
        return commentRepo.findByIdAndTicket_Id(id,ticketId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment " +id+ " not found for ticket " + ticketId));
    }
}

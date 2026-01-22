package com.fran.ticketing_api.service.impl;

import com.fran.ticketing_api.dto.CreateTicketRequest;
import com.fran.ticketing_api.dto.UpdateTicketRequest;
import com.fran.ticketing_api.entitie.Priority;
import com.fran.ticketing_api.entitie.Status;
import com.fran.ticketing_api.entitie.Ticket;
import com.fran.ticketing_api.entitie.User;
import com.fran.ticketing_api.exception.BusinessException;
import com.fran.ticketing_api.exception.ResourceNotFoundException;
import com.fran.ticketing_api.repository.ITicketRepository;
import com.fran.ticketing_api.repository.IUserRepository;
import com.fran.ticketing_api.service.ITicketService;
import com.fran.ticketing_api.spec.TicketSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;
import java.util.Set;


@Service
public class TicketServiceImpl implements ITicketService {

    @Autowired
    private ITicketRepository ticketRepo;

    @Autowired
    private IUserRepository userRepo;


    @Override
    public Ticket create(CreateTicketRequest req) {
        Ticket ticket = new Ticket();


        ticket.setTitle(req.title());
        ticket.setDescription(req.description());
        ticket.setPriority(req.priority() != null ? req.priority() : Priority.MEDIUM);
        ticket.setStatus(Status.OPEN);
        if (req.assigneeId() != null) {
            User assignee = userRepo.findById(req.assigneeId())
                    .orElseThrow(() -> new ResourceNotFoundException("User " + req.assigneeId() + " not found"));
            ticket.setAssignee(assignee);
        } else {
            ticket.setAssignee(null);
        }

        return  ticketRepo.save(ticket);


    }

    @Override
    public Ticket update(Long id, UpdateTicketRequest req) {
        Ticket ticket = ticketRepo.findById(id)
            .orElseThrow(()-> new ResourceNotFoundException("Ticket not found"));

        ticket.setTitle(req.title() == null ? ticket.getTitle() : req.title());
        ticket.setDescription(req.description() == null ? ticket.getDescription() : req.description());
        ticket.setPriority(req.priority() == null ? ticket.getPriority() : req.priority());
        if (req.assigneeId() != null) {
            User assignee = userRepo.findById(req.assigneeId())
                    .orElseThrow(() -> new ResourceNotFoundException("User " + req.assigneeId() + " not found"));
            ticket.setAssignee(assignee);
        } else {
            ticket.setAssignee(null);
        }

        return ticketRepo.save(ticket);
    }

    @Override
    public Ticket updateStatus(Long id, Status newStatus) {
        Ticket ticket = ticketRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Ticket not found"));

        Status current = ticket.getStatus();

        if(!isValidTransition(current, newStatus)){
            throw new BusinessException("Ticket #"+id +" cannot change status from "+ current + " to " + newStatus);
        }

        ticket.setStatus(newStatus);

        return ticketRepo.save(ticket);
    }


    @Override
    public Ticket findById(Long id) {
        return ticketRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Ticket not found"));
    }

    @Override
    public void delete(Long id) {
        if(!ticketRepo.existsById(id)){
            throw new ResourceNotFoundException("Ticket not found");
        }
        ticketRepo.deleteById(id);
    }


    private static final Map<Status, Set<Status>> ALLOWED_TRANSITIONS = Map.of(
        Status.OPEN, Set.of(Status.IN_PROGRESS),
        Status.IN_PROGRESS, Set.of(Status.RESOLVED),
        Status.RESOLVED, Set.of(Status.CLOSED),
        Status.CLOSED, Set.of(Status.REOPENED),
        Status.REOPENED, Set.of(Status.IN_PROGRESS, Status.CLOSED)
    );

    private boolean isValidTransition(Status current, Status next) {
        if(current == null || next == null) return false;

        return ALLOWED_TRANSITIONS.getOrDefault(current, Set.of()).contains(next);
    }

    @Override
    public Page<Ticket> search(Long assigneeId, Priority priority, Status status,String q, Pageable pageable) {
        Specification<Ticket> spec = Specification
                .where(TicketSpecifications.hasAssigneeId(assigneeId))
                .and(TicketSpecifications.hasPriority(priority))
                .and(TicketSpecifications.hasStatus(status))
                .and(TicketSpecifications.containsText(q));

        return ticketRepo.findAll(spec, pageable);
    }


}

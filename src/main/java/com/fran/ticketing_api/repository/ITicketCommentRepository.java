package com.fran.ticketing_api.repository;

import com.fran.ticketing_api.entitie.TicketComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ITicketCommentRepository extends JpaRepository<TicketComment, Long>, JpaSpecificationExecutor<TicketComment> {


    Optional<TicketComment> findByIdAndTicket_Id(Long id, Long ticketId);

    List<TicketComment> findByTicket_IdOrderByCreatedAtAsc(Long ticketId);
}

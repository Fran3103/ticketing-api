package com.fran.ticketing_api.spec;

import com.fran.ticketing_api.entitie.Priority;
import com.fran.ticketing_api.entitie.Status;
import com.fran.ticketing_api.entitie.Ticket;
import org.springframework.data.jpa.domain.Specification;

public final class TicketSpecifications {

    private TicketSpecifications() {}

    public static Specification<Ticket> hasAssigneeId(Long assigneeId){

        return (root, query, cb) -> {
            if(assigneeId == null) return cb.conjunction();
            return cb.equal(root.get("assignee").get("id"),assigneeId);

        };
    }

    public static Specification<Ticket> hasPriority(Priority priority){

        return (root, query, cb) -> {
            if(priority == null) return cb.conjunction();

            return cb.equal(root.get("priority"),priority);

        };
    }


    public static Specification<Ticket> hasStatus(Status status){

        return (root, query, cb) -> {
            if(status == null) return cb.conjunction();
            return cb.equal(root.get("status"),status);

        };
    }

    public static Specification<Ticket> containsText(String q) {
        return (root, query, cb) -> {
            if (q == null || q.trim().isEmpty()) return cb.conjunction();

            String like = "%" + q.trim().toLowerCase() + "%";

            // lower(title) like %q% OR lower(description) like %q%
            return cb.or(
                    cb.like(cb.lower(root.get("title")), like),
                    cb.like(cb.lower(root.get("description")), like)
            );
        };
    }

}


package com.fran.ticketing_api.spec;


import com.fran.ticketing_api.entitie.TicketComment;
import org.springframework.data.jpa.domain.Specification;

public final class CommentSpecifications {

    private CommentSpecifications() {}

    public static Specification<TicketComment> hasAuthor(Long authorId) {
        return (root, query, cb) ->
        {
            if(authorId == null) return cb.conjunction();
            return cb.equal(root.get("author").get("id"), authorId);
        };
    }


    public static Specification<TicketComment> hasTicketId(Long ticketId){

        return (root, query, cb) -> {
            if(ticketId == null) return cb.conjunction();
            return cb.equal(root.get("ticket").get("id"),ticketId);

        };
    }



}



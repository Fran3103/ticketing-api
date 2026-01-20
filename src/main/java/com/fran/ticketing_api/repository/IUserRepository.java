package com.fran.ticketing_api.repository;

import com.fran.ticketing_api.entitie.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

    User getByName(String name);

    User getByEmail(String email);
}

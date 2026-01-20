package com.fran.ticketing_api.service;

import com.fran.ticketing_api.dto.CreateUserRequest;
import com.fran.ticketing_api.dto.UpdateUserRequest;
import com.fran.ticketing_api.entitie.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    User getByName(String name);

    User getByEmail(String email);

    User getById(Long id);

    User create(CreateUserRequest req);

    User update(Long id, UpdateUserRequest req);

    void deleteUser(Long id);

    List<User> getAll();
}

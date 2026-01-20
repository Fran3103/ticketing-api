package com.fran.ticketing_api.service.impl;

import com.fran.ticketing_api.dto.CreateUserRequest;
import com.fran.ticketing_api.dto.UpdateUserRequest;
import com.fran.ticketing_api.entitie.User;
import com.fran.ticketing_api.exception.ResourceNotFoundException;
import com.fran.ticketing_api.repository.IUserRepository;
import com.fran.ticketing_api.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserRepository userRepo;


    @Override
    public User getByName(String name) {
        User u =  userRepo.getByName(name);

        if(u==null){
            throw new ResourceNotFoundException("User with name " + name + " not found");
        }

        return u;
    }

    @Override
    public User getByEmail(String email) {
        User u = userRepo.getByEmail(email);
        if(u==null){
            throw new ResourceNotFoundException("User with email "+email+" not found.");
        }
     return u;
    }

    @Override
    public User getById(Long id) {
        return userRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("User with id "+ id +" not found."));
    }

    @Override
    public User create(CreateUserRequest req) {
       User user = new User();

       user.setName(req.name());
       user.setEmail(req.email());
       user.setRole(req.role());
       return userRepo.save(user);
    }

    @Override
    public User update(Long id, UpdateUserRequest req) {
     User newUser = getById(id);


        newUser.setName(req.name() == null ? newUser.getName() : req.name());
        newUser.setEmail(req.email() == null ? newUser.getEmail() : req.email());
        newUser.setRole(req.role() == null ? newUser.getRole() : req.role());

        return userRepo.save(newUser);

    }

    @Override
    public void deleteUser(Long id) {
        if(!userRepo.existsById(id)){
            throw new ResourceNotFoundException("User with id "+ id +" not found.");
        }
        userRepo.deleteById(id);

    }

    @Override
    public List<User> getAll() {
        return userRepo.findAll();
    }
}

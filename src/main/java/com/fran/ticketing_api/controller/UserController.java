package com.fran.ticketing_api.controller;

import com.fran.ticketing_api.dto.CreateUserRequest;
import com.fran.ticketing_api.dto.UpdateUserRequest;
import com.fran.ticketing_api.dto.UserResponse;
import com.fran.ticketing_api.entitie.User;
import com.fran.ticketing_api.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping()
    public ResponseEntity<UserResponse> create(@Valid @RequestBody CreateUserRequest user) {
        User createdUser = userService.create(user);

        URI location = URI.create("/api/users/" + createdUser.getId());
        return ResponseEntity.created(location).body(toResponse(createdUser));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(toResponse(userService.getById(id)));
    }

    @GetMapping()
    public ResponseEntity<List<UserResponse>> list(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String name
    ) {
        if (email != null ) return ResponseEntity.ok(List.of(toResponse(userService.getByEmail(email))));
        if (name != null) return ResponseEntity.ok(List.of(toResponse(userService.getByName(name))));

        List<UserResponse> res = userService.getAll().stream().map(this::toResponse).toList();
        return ResponseEntity.ok(res);

    }


    @PatchMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @Valid @RequestBody UpdateUserRequest req) {
        User updatedUser = userService.update(id, req);
        return ResponseEntity.ok(toResponse(updatedUser));

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }



    private UserResponse toResponse(User u) {
        return new UserResponse(u.getId(), u.getName(), u.getEmail(), u.getRole(), u.getCreatedAt());
    }
}

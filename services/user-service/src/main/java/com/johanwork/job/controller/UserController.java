package com.johanwork.job.controller;

import com.johanwork.job.dto.UserRequest;
import com.johanwork.job.dto.response.GenericResponse;
import com.johanwork.job.dto.response.PageResponse;
import com.johanwork.job.dto.response.UserResponse;
import com.johanwork.job.service.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final IUserService userService;

    @GetMapping
    public ResponseEntity<GenericResponse<PageResponse<UserResponse>>> getAllUsers(
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "10") int pageSize,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String sortDirection,
            @RequestParam(required = false) String search
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.getAllUser(pageNumber, pageSize, sortBy, sortDirection, search));
    }

    @GetMapping("/profile")
    public ResponseEntity<GenericResponse<UserResponse>> getProfile(
            @RequestHeader("X-User-Email") String email
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.getProfile(email));
    }

    @PutMapping("/profile")
    public ResponseEntity<GenericResponse<UserResponse>> updateProfile(
            @RequestHeader("X-User-Email") String email,
            @RequestBody @Valid UserRequest request
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.updateProfile(email, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse<UserResponse>> getUserById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.getUserById(id));
    }

    @PatchMapping("/{id}/suspend")
    public ResponseEntity<GenericResponse<UserResponse>> suspendUser(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.suspendUser(id));
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<GenericResponse<UserResponse>> activateUser(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.activateUser(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse<UserResponse>> deleteUser(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.deleteUser(id));
    }

}

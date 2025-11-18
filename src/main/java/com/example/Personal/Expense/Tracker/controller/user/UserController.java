package com.example.Personal.Expense.Tracker.controller.user;

import com.example.Personal.Expense.Tracker.dto.request.user.UserCreationRequest;
import com.example.Personal.Expense.Tracker.dto.response.user.UserResponse;
import com.example.Personal.Expense.Tracker.dto.response.utils.APIResponse;
import com.example.Personal.Expense.Tracker.entity.User;
import com.example.Personal.Expense.Tracker.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping()
    APIResponse<UserResponse> create(@RequestBody @Valid UserCreationRequest rq) {
        return APIResponse.<UserResponse>builder()
                .result(userService.createUser(rq))
                .build();
    }

    @GetMapping
    APIResponse<List<UserResponse>> getUser(){
        return APIResponse.<List<UserResponse>>builder().result(
                userService.getUsers()
        ).build();
    }
}

package com.example.Personal.Expense.Tracker.controller.user;

import com.example.Personal.Expense.Tracker.dto.request.user.UserCreationRequest;
import com.example.Personal.Expense.Tracker.dto.response.user.UserResponse;
import com.example.Personal.Expense.Tracker.dto.response.utils.APIResponse;
import com.example.Personal.Expense.Tracker.entity.User;
import com.example.Personal.Expense.Tracker.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

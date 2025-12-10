package com.example.Personal.Expense.Tracker.controller;

import com.example.Personal.Expense.Tracker.dto.request.user.UserCreationRequest;
import com.example.Personal.Expense.Tracker.dto.request.user.UserUpdateRequest;
import com.example.Personal.Expense.Tracker.dto.response.user.UserResponse;
import com.example.Personal.Expense.Tracker.dto.response.utils.APIResponse;
import com.example.Personal.Expense.Tracker.dto.response.utils.PageResponse;
import com.example.Personal.Expense.Tracker.entity.User;
import com.example.Personal.Expense.Tracker.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserController {

    UserService userService;


    @PostMapping()
    APIResponse<UserResponse> create(@RequestBody @Valid UserCreationRequest rq) {
        return APIResponse.<UserResponse>builder()
                .result(userService.createUser(rq))
                .build();
    }

    @GetMapping
    APIResponse<PageResponse<UserResponse>> getUser(Pageable pageable) {
        return APIResponse.<PageResponse<UserResponse>>builder().result(
                userService.getUsers(pageable)
        ).build();
    }

    @GetMapping("/{ID}")
    APIResponse<UserResponse> getUserById(@PathVariable("ID") String id){
        return APIResponse.<UserResponse>builder().result(userService.getUser(id)).build();
    }


    @GetMapping("/myInfo")
    APIResponse<UserResponse> getMyInfo(){
        return APIResponse.<UserResponse>builder().result(userService.getMyInfo()).build();
    }

    @PutMapping("/{ID}")
    APIResponse<UserResponse> update(@PathVariable("ID") String ID ,  @RequestBody UserUpdateRequest rq){
        return APIResponse.<UserResponse>builder().result(userService.updateUser(ID,rq)).build();
    }
}

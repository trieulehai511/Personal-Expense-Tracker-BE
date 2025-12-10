package com.example.Personal.Expense.Tracker.controller;


import com.example.Personal.Expense.Tracker.dto.request.permission.PermissionRequest;
import com.example.Personal.Expense.Tracker.dto.response.permission.PermissionResponse;
import com.example.Personal.Expense.Tracker.dto.response.utils.APIResponse;
import com.example.Personal.Expense.Tracker.repository.PermissionRepository;
import com.example.Personal.Expense.Tracker.service.PermissionService;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/permission")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionController {

    PermissionService permissionService;

    @PostMapping
    APIResponse<PermissionResponse> create(@RequestBody PermissionRequest rq){
        return APIResponse.<PermissionResponse>builder().result(permissionService.create(rq)).build();
    }

    @GetMapping
    APIResponse<List<PermissionResponse>> getAll(){
        return APIResponse.<List<PermissionResponse>>builder().result(permissionService.getAll()).build();
    }

    @DeleteMapping("{ID}")
    APIResponse<Boolean> delete(@PathVariable("ID") String ID){
        return APIResponse.<Boolean>builder().result(permissionService.delete(ID)).build();
    }

}

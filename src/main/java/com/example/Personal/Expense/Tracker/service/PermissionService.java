package com.example.Personal.Expense.Tracker.service;


import com.example.Personal.Expense.Tracker.dto.request.permission.PermissionRequest;
import com.example.Personal.Expense.Tracker.dto.response.permission.PermissionResponse;
import com.example.Personal.Expense.Tracker.entity.Permission;
import com.example.Personal.Expense.Tracker.mapper.PermissionMapper;
import com.example.Personal.Expense.Tracker.repository.PermissionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    public PermissionResponse create(PermissionRequest rq){
        Permission permission = permissionMapper.toPermission(rq);
        permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }
    public List<PermissionResponse> getAll(){
        var permissions = permissionRepository.findAll();
        return permissions.stream().map(permissionMapper::toPermissionResponse).toList();
    }
    public boolean delete(String name){
        permissionRepository.deleteById(name);
        return true;
    }
}

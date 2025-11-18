package com.example.Personal.Expense.Tracker.service;

import com.example.Personal.Expense.Tracker.dto.request.role.RoleRequest;
import com.example.Personal.Expense.Tracker.dto.response.role.RoleResponse;
import com.example.Personal.Expense.Tracker.entity.Role;
import com.example.Personal.Expense.Tracker.mapper.RoleMapper;
import com.example.Personal.Expense.Tracker.repository.PermissionRepository;
import com.example.Personal.Expense.Tracker.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
    RoleRepository roleRepository;
    RoleMapper roleMapper;
    PermissionRepository permissionRepository;

    public RoleResponse create(RoleRequest rq){
        var role  = roleMapper.toRole(rq);
        var permission = permissionRepository.findAllById(rq.getPermissions());
        role.setPermissions(new HashSet<>(permission));
        role = roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }

    public List<RoleResponse> findAll(){
        var roles = roleRepository.findAll();
        return roles.stream().map(roleMapper::toRoleResponse).collect(Collectors.toList());
    }
}

package com.example.Personal.Expense.Tracker.mapper;


import com.example.Personal.Expense.Tracker.dto.request.permission.PermissionRequest;
import com.example.Personal.Expense.Tracker.dto.response.permission.PermissionResponse;
import com.example.Personal.Expense.Tracker.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest rq);
    PermissionResponse toPermissionResponse(Permission permission);
}

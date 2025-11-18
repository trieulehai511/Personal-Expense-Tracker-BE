package com.example.Personal.Expense.Tracker.mapper;


import com.example.Personal.Expense.Tracker.dto.request.role.RoleRequest;
import com.example.Personal.Expense.Tracker.dto.response.role.RoleResponse;
import com.example.Personal.Expense.Tracker.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest rq);

    RoleResponse toRoleResponse(Role role);

}

package com.example.Personal.Expense.Tracker.mapper;


import com.example.Personal.Expense.Tracker.dto.request.user.UserCreationRequest;
import com.example.Personal.Expense.Tracker.dto.request.user.UserUpdateRequest;
import com.example.Personal.Expense.Tracker.dto.response.user.UserResponse;
import com.example.Personal.Expense.Tracker.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest rq);
    UserResponse toUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "password", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest rq);
}

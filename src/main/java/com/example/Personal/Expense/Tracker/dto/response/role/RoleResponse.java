package com.example.Personal.Expense.Tracker.dto.response.role;

import com.example.Personal.Expense.Tracker.dto.response.permission.PermissionResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class RoleResponse {
    String name;
    String description;
    Set<PermissionResponse> permissions;
}

package com.example.Personal.Expense.Tracker.dto.request.user;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PasswordChangeRequest {
    @Size(min = 6, message = "INVALID_PASSWORD")
    String oldPassword;

    @Size(min = 6, message = "INVALID_PASSWORD")
    String newPassword;
}
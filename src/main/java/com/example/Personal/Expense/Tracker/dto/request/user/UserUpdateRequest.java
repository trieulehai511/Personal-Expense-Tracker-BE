package com.example.Personal.Expense.Tracker.dto.request.user;

import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    @Size(min = 4, message = "USERNAME INVALID")
    String username;
    @Size(min = 4, message = "INVALID PASSWORD")
    String password;
    String firstName;
    String lastName;
    //    @DobConstraint(min = 10, message = "INVALID_DOB")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    LocalDate dob;
}

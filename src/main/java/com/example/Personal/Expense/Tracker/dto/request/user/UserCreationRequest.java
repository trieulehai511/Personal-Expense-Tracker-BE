package com.example.Personal.Expense.Tracker.dto.request.user;

import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class UserCreationRequest {

    @Size(min = 4, message = "USERNAME INVALID")
    String username;
    @Size(min = 4, message = "INVALID PASSWORD")
    String password;
    String firstName;
    String lastName;
//    @DobConstraint(min = 10, message = "INVALID_DOB")
    LocalDate dob;
}

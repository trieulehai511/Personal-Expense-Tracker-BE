package com.example.Personal.Expense.Tracker.dto.request.authentication;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level =  AccessLevel.PRIVATE)
public class IntrospectTokenRequest {
    String token;
}

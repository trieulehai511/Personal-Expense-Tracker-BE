package com.example.Personal.Expense.Tracker.controller.user;


import com.example.Personal.Expense.Tracker.dto.request.authentication.AuthenticationRequest;
import com.example.Personal.Expense.Tracker.dto.request.authentication.IntrospectTokenRequest;
import com.example.Personal.Expense.Tracker.dto.response.authentication.AuthenticationResponse;
import com.example.Personal.Expense.Tracker.dto.response.authentication.IntrospectResponse;
import com.example.Personal.Expense.Tracker.dto.response.utils.APIResponse;
import com.example.Personal.Expense.Tracker.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;
    @PostMapping("/log-in")
    APIResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest rq){
        var result  = authenticationService.authenticate(rq);
        return  APIResponse.<AuthenticationResponse>builder().result(
               result
        ).build();
    }

    @PostMapping("/introspect")
    APIResponse<IntrospectResponse> introspect(@RequestBody  IntrospectTokenRequest rq) throws ParseException, JOSEException {
        var result = authenticationService.introspect(rq);
        return APIResponse.<IntrospectResponse>builder().result(
                result
        ).build();
    }

}

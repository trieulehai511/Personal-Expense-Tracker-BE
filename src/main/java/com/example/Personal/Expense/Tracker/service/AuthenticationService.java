package com.example.Personal.Expense.Tracker.service;


import com.example.Personal.Expense.Tracker.dto.request.authentication.AuthenticationRequest;
import com.example.Personal.Expense.Tracker.dto.request.authentication.IntrospectTokenRequest;
import com.example.Personal.Expense.Tracker.dto.response.authentication.AuthenticationResponse;
import com.example.Personal.Expense.Tracker.dto.response.authentication.IntrospectResponse;
import com.example.Personal.Expense.Tracker.exeption.AppException;
import com.example.Personal.Expense.Tracker.exeption.ErrorCode;
import com.example.Personal.Expense.Tracker.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    UserRepository userRepository;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SINGER_KEY;


    public IntrospectResponse introspect(IntrospectTokenRequest rq) throws JOSEException, ParseException {
        var token = rq.getToken();
        JWSVerifier jwsVerifier = new MACVerifier(SINGER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);
        Date expiryTime  = signedJWT.getJWTClaimsSet().getExpirationTime();
        var verified = signedJWT.verify(jwsVerifier);
        return IntrospectResponse.builder()
                .valid(verified && expiryTime.after(new Date()))
                .build();
    }



    public AuthenticationResponse authenticate(AuthenticationRequest rq){
        var user = userRepository.findByUsername(rq.getUsername()).orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated =  passwordEncoder.matches(rq.getPassword(), user.getPassword());
        if(!authenticated){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        var token = generateToken(rq.getUsername());
        return AuthenticationResponse.builder().token(token).authenticated(true).build();
    }
    String generateToken(String username){
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder().
                subject(username)
                .issuer("haynes.com")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS)
                        .toEpochMilli()))
            .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header,payload);
        try {
            jwsObject.sign(new MACSigner(SINGER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            System.out.println("Cannot generate token");
            log.error("Cannot generate token");
            throw new RuntimeException(e);
        }
    }

}

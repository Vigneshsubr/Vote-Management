package com.vote.votemanagement.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.vote.votemanagement.entity.Admin;
import com.vote.votemanagement.entity.Candidate;
import com.vote.votemanagement.entity.User;
import com.vote.votemanagement.repository.AdminRepository;
import com.vote.votemanagement.repository.CandidateRepository;
import com.vote.votemanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.regex.Pattern;

@Service
public class TokenProvider {


    @Autowired
    AdminRepository adminRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    CandidateRepository candidateRepository;

    @Value("${security.jwt.secret-key}")
    String secretKey;

    public String generateAccessToken(UserDetails user) {
        try {

            String username = user.getUsername();
            String name = "";

            // Check the type of the user and extract the name accordingly
            if (user instanceof Admin) {
                name = ((Admin) user).getName(); // Get Admin's name
            } else if (user instanceof Candidate) {
                name = ((Candidate) user).getName(); // Get Candidate's name
            } else if (user instanceof User) {
                name = ((User) user).getName(); // Get User's name
            }

            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            return JWT.create()
                    .withSubject(user.getUsername())
                    .withClaim("UserEmail", user.getUsername())
                    .withIssuedAt(Instant.now()) // Add issuedAt claim
                    .withExpiresAt(genAccessExperationDate())
                    .withClaim("Name", name) // Add the name claim here
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new JWTCreationException("Error while generating access token", exception);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            return JWT.require(algorithm).build().verify(token).getSubject();
        } catch (JWTVerificationException excepiton) {
            throw new JWTVerificationException("Error while validating access token", excepiton);
        }

    }

    public String generateRefreshToken(UserDetails user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            return JWT.create().withSubject(user.getUsername()).withClaim("UserEmail", user.getUsername())
                    .withExpiresAt(genRefreshExperationDate()).sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new JWTCreationException("Error while generating refresh token", exception);
        }
    }

    public String validateRefreshToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            return JWT.require(algorithm).build().verify(token).getSubject();
        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException("Error while validating refresh token", exception);
        }
    }



    public Instant genAccessExperationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.UTC);

    }



    public Instant genRefreshExperationDate() {
        return LocalDateTime.now().plusDays(7).toInstant(ZoneOffset.UTC);

    }



    public String refreshAccessToken(String refreshToken) {
        String username = validateRefreshToken(refreshToken);
        UserDetails user = loadUserByUsername(username);
        return generateAccessToken(user);
    }



    public String refreshRefreshToken(String refreshToken) {
        String username = validateRefreshToken(refreshToken);
        UserDetails user = loadUserByUsername(username);
        return generateRefreshToken(user);
    }



    private UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByEmail(username);
        if (user != null) {
            return (UserDetails) user;
        }

        Admin admin = adminRepository.findByEmail(username);
        if (admin != null) {
            return (UserDetails) admin;
        }

        Candidate candidate = candidateRepository.findByEmail(username);
        if(candidate!=null){
            return (UserDetails) candidate;
        }
        throw new UsernameNotFoundException("User not found with email: " + username);
    }



    public boolean emailValidation(String email) {
        return Pattern.compile("^[a-z0-9+_.-]+@(gmail|yahoo|outlook|zoho)\\.com$").matcher(email).matches();
    }

    public boolean passwordValidation(String password) {
        String pass = "^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=.*[@#$%^&+=])" + "(?=\\S+$).{8,20}$";
        return Pattern.compile(pass).matcher(password).matches();
    }

}

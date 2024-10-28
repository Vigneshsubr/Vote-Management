package com.vote.votemanagement.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.vote.votemanagement.entity.Admin;
import com.vote.votemanagement.entity.Candidate;
import com.vote.votemanagement.entity.User;
import com.vote.votemanagement.repository.AdminRepository;
import com.vote.votemanagement.repository.CandidateRepository;
import com.vote.votemanagement.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
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
            Long id = null; // Declare variable for the ID

            // Check the type of the user and extract the name and id accordingly
            if (user instanceof Admin) {
                Admin admin = (Admin) user;
                name = admin.getName(); // Get Admin's name
                id = admin.getId(); // Get Admin's id
            } else if (user instanceof Candidate) {
                Candidate candidate = (Candidate) user;
                name = candidate.getName(); // Get Candidate's name
                id = candidate.getId(); // Get Candidate's id
            } else if (user instanceof User) {
                User normalUser = (User) user;
                name = normalUser.getName(); // Get User's name
                id = normalUser.getId(); // Get User's id
            }

            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            return JWT.create()
                    .withSubject(user.getUsername())
                    .withClaim("UserEmail", user.getUsername())
                    .withClaim("Name", name) // Add the name claim
                    .withClaim("UserId", id) // Add the id claim
                    .withIssuedAt(Instant.now()) // Add issuedAt claim
                    .withExpiresAt(genAccessExperationDate()) // Expiration time for access token
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

    // Decode token and retrieve user ID
    public Long getUserIdFromToken(String token) {
        DecodedJWT decodedJWT = decodeToken(token);
        return decodedJWT.getClaim("UserId").asLong();
    }

    // Decode token and retrieve username
    public String getUsernameFromToken(String token) {
        DecodedJWT decodedJWT = decodeToken(token);
        return decodedJWT.getClaim("UserEmail").asString();
    }

    // Decode token and retrieve name
    public String getNameFromToken(String token) {
        DecodedJWT decodedJWT = decodeToken(token);
        return decodedJWT.getClaim("Name").asString();
    }

    private DecodedJWT decodeToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            return JWT.require(algorithm).build().verify(token);
        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException("Error while decoding access token", exception);
        }
    }

    public String recoverToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // Remove "Bearer " prefix
        }
        return null;
    }


}

package com.vote.votemanagement.service;

import com.vote.votemanagement.config.TokenProvider;
import com.vote.votemanagement.dto.*;
import com.vote.votemanagement.entity.Admin;
import com.vote.votemanagement.entity.Candidate;
import com.vote.votemanagement.entity.User;
import com.vote.votemanagement.enums.UserRole;
import com.vote.votemanagement.exception.CustomException;
import com.vote.votemanagement.exception.InvalidJwtException;
import com.vote.votemanagement.repository.AdminRepository;
import com.vote.votemanagement.repository.CandidateRepository;
import com.vote.votemanagement.repository.PollRepository;
import com.vote.votemanagement.repository.UserRepository;
import com.vote.votemanagement.util.Constants;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AuthService implements UserDetailsService {




    @Autowired
    AdminRepository adminRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CandidateRepository candidateRepository;

    @Autowired
    PollRepository pollRepository;

    @Autowired
    TokenProvider tokenProvider;

    UserRole role;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user != null) {
            return (UserDetails) user;
        }

        Admin admin = adminRepository.findByEmail(username);
        if (admin != null) {
            return (UserDetails) admin;
        }

        Candidate candidate=candidateRepository.findByEmail(username);
        if(candidate!=null){
            return(UserDetails) candidate;
        }

        throw new UsernameNotFoundException("User not found with email: " + username);
    }




    public ResponseDTO signUp(SignUpRequest signUpRequest) throws CustomException {
        // Common email & password validation
        if (!tokenProvider.emailValidation(signUpRequest.getEmail())) {
            throw new CustomException("Email is not in the correct format");
        }
        if (!tokenProvider.passwordValidation(signUpRequest.getPassword())) {
            throw new CustomException("Password is not valid");
        }

        // Check if email already exists in any of the repositories
        if (adminRepository.existsByEmail(signUpRequest.getEmail()) || candidateRepository.existsByEmail(signUpRequest.getEmail()) || userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new CustomException("This Email already exists");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(signUpRequest.getPassword());

        // Check role and create corresponding entity
        if (signUpRequest.getRole() == UserRole.ADMIN) {
            Admin admin = new Admin();
            admin.setEmail(signUpRequest.getEmail());
            admin.setPassword(encryptedPassword);
            admin.setRole(UserRole.ADMIN);
            admin.setName(signUpRequest.getName());
            admin.setAge(signUpRequest.getAge());
            admin.setAddress(signUpRequest.getAddress());
            admin.setGender(signUpRequest.getGender());
            return ResponseDTO.builder()
                    .message(Constants.CREATED)
                    .data(adminRepository.save(admin))
                    .statusCode(200)
                    .build();
        } else if (signUpRequest.getRole() == UserRole.CANDIDATE) {
            Candidate candidate = new Candidate();
            candidate.setEmail(signUpRequest.getEmail());
            candidate.setPassword(encryptedPassword);
            candidate.setRole(UserRole.CANDIDATE);
            candidate.setName(signUpRequest.getName());
            candidate.setAge(signUpRequest.getAge());
            candidate.setAddress(signUpRequest.getAddress());
            candidate.setGender(signUpRequest.getGender());
            return ResponseDTO.builder()
                    .message(Constants.CREATED)
                    .data(candidateRepository.save(candidate))
                    .statusCode(200)
                    .build();
        } else {
            User user = new User();
            user.setEmail(signUpRequest.getEmail());
            user.setPassword(encryptedPassword);
            user.setRole(UserRole.VOTER);
            user.setName(signUpRequest.getName());
            user.setAge(signUpRequest.getAge());
            user.setAddress(signUpRequest.getAddress());
            user.setGender(signUpRequest.getGender());
            return ResponseDTO.builder()
                    .message(Constants.CREATED)
                    .data(userRepository.save(user))
                    .statusCode(200)
                    .build();
        }
    }

    public SignoutResponseDto signOut(String token) {
        return new SignoutResponseDto("Successfully signed out");
    }


}




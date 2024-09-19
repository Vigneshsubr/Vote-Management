package com.vote.votemanagement.service;

import com.vote.votemanagement.config.TokenProvider;
import com.vote.votemanagement.dto.*;
import com.vote.votemanagement.entity.Admin;
import com.vote.votemanagement.entity.Candidate;
import com.vote.votemanagement.entity.Poll;
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


    public ResponseDTO signUpUser(UserDto userDto) throws CustomException {
        try {
            if (!tokenProvider.emailValidation(userDto.getEmail())) {
                throw new CustomException("Email is not in the correct format");
            }
            if (!tokenProvider.passwordValidation(userDto.getPassword())) {
                throw new CustomException("Password is not valid");
            }
            if (adminRepository.existsByEmail(userDto.getEmail()) ||
                    candidateRepository.existsByEmail(userDto.getEmail()) ||
                    userRepository.existsByEmail(userDto.getEmail())) {
                throw new CustomException("This User Email already exists");
            }
            String encryptedPassword = new BCryptPasswordEncoder().encode(userDto.getPassword());
            User user = new User();
            user.setEmail(userDto.getEmail());
            user.setPassword(encryptedPassword);
            user.setRole(UserRole.VOTER);
            user.setUsername(userDto.getUsername());


            return ResponseDTO.builder()
                    .message(Constants.CREATED)
                    .data(userRepository.save(user))
                    .statusCode(200)
                    .build();
        } catch (CustomException e) {
            // Handle known custom exceptions
            return ResponseDTO.builder()
                    .message(e.getMessage())
                    .statusCode(400)
                    .build();
        } catch (Exception e) {
            // Handle unexpected errors
            return ResponseDTO.builder()
                    .message("An unexpected error occurred")
                    .statusCode(500)
                    .build();
        }
    }


    public ResponseDTO signUpCandidate(CandidateDTO candidateDTO) throws InvalidJwtException{

       try {
           if (!tokenProvider.emailValidation(candidateDTO.getEmail())) {
               throw new InvalidJwtException("Email is not in the correct format");
           }
           if (!tokenProvider.passwordValidation(candidateDTO.getPassword())) {
               throw new InvalidJwtException("Password is not valid");
           }

           if (adminRepository.existsByEmail(candidateDTO.getEmail()) || candidateRepository.existsByEmail(candidateDTO.getEmail()) || userRepository.existsByEmail(candidateDTO.getEmail())) {
               throw new InvalidJwtException("This Candidate Email already exists");

           }

           // Fetch the Poll entity by its ID
           Poll poll = pollRepository.findById(candidateDTO.getPollId())
                   .orElseThrow(() -> new InvalidJwtException("Poll not found"));


           String encryptedPassword = new BCryptPasswordEncoder().encode(candidateDTO.getPassword());
           Candidate candidate = new Candidate();
           candidate.setEmail(candidateDTO.getEmail());
           candidate.setPassword(encryptedPassword);
           candidate.setUsername(candidateDTO.getUsername());
           candidate.setGender(candidateDTO.getGender());
           candidate.setPoll(poll);
           candidate.setRole(UserRole.CANDIDATE);

           return ResponseDTO.builder()
                   .message(Constants.CREATED)
                   .data(candidateRepository.save(candidate))
                   .statusCode(200)
                   .build();
       }
       catch (Exception e) {
           e.printStackTrace(); // Log the exception
           return ResponseDTO.builder()
                   .message("An unexpected error occurred: " + e.getMessage())
                   .statusCode(500)
                   .build();
       }

    }



    public ResponseDTO signUpAdmin(AdminDTO adminDTO) throws InvalidJwtException {

//		 if(adminRepository.findByEmail(adminDTO.getEmail())!=null) {
//				throw new InvalidJwtException("Admin all ready exists");
//
//			}

        if (!tokenProvider.emailValidation(adminDTO.getEmail())) {
            throw new InvalidJwtException("Email is not in the correct format");
        }
        if (!tokenProvider.passwordValidation(adminDTO.getPassword())) {
            throw new InvalidJwtException("Password is not valid");
        }


        if (adminRepository.existsByEmail(adminDTO.getEmail()) || candidateRepository.existsByEmail(adminDTO.getEmail()) || userRepository.existsByEmail(adminDTO.getEmail())) {
            throw new InvalidJwtException("This admin Email already exists");
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(adminDTO.getPassword());
        Admin admin=new Admin();
        admin.setEmail(adminDTO.getEmail());
        admin.setPassword(encryptedPassword);
        admin.setRole(UserRole .ADMIN );
        admin.setUsername(adminDTO.getUsername()
        );



        return ResponseDTO.builder()
                .message(Constants.CREATED)
                .data(adminRepository.save(admin))
                .statusCode(200)
                .build();
    }


}




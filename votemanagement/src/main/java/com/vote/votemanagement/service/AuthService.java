package com.vote.votemanagement.service;

import com.vote.votemanagement.config.TokenProvider;
import com.vote.votemanagement.dto.AdminDTO;
import com.vote.votemanagement.dto.ResponseDTO;
import com.vote.votemanagement.dto.UserDto;
import com.vote.votemanagement.entity.Admin;
import com.vote.votemanagement.entity.User;
import com.vote.votemanagement.enums.UserRole;
import com.vote.votemanagement.exception.CustomException;
import com.vote.votemanagement.exception.InvalidJwtException;
import com.vote.votemanagement.repository.AdminRepository;
import com.vote.votemanagement.repository.CandidateRepository;
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


//    public ResponseDTO signUpTutor(TutorDTO tutorDTO) throws InvalidJwtException{
//
//        if (!tokenProvider.emailValidation(tutorDTO.getEmail())) {
//            throw new InvalidJwtException("Email is not in the correct format");
//        }
//        if (!tokenProvider.passwordValidation(tutorDTO.getPassword())) {
//            throw new InvalidJwtException("Password is not valid");
//        }
//
//        if (adminRepository.existsByEmail(tutorDTO.getEmail()) || candidateRepository.existsByEmail(tutorDTO.getEmail()) || userRepository.existsByEmail(tutorDTO.getEmail())) {
//            throw new InvalidJwtException("This tutor Email already exists");
//        }
//
//        String encryptedPassword = new BCryptPasswordEncoder().encode(tutorDTO.getPassword());
//        Tutor tutor=new Tutor();
//        tutor.setEmail(tutorDTO.getEmail());
//        tutor.setPassword(encryptedPassword);
//        tutor.setRole(UserRole .TUTOR);
//        tutor.setAddress(tutorDTO.getAddress());
//        tutor.setName(tutorDTO.getName());
//
//        tutor.setSubject(subjectRepository.findById(tutorDTO.getSubject()).orElse(null));
//        tutor.setSchool(schoolRepository.findById(tutorDTO.getSchoolId()).orElseThrow(null));
//
//
//        return ResponseDTO.builder()
//                .message(Constants.CREATED)
//                .data(tutorRepository.save(tutor))
//                .statusCode(200)
//                .build();
//
//    }



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
        admin.setRole(UserRole .ADMIN);
        admin.setUsername(adminDTO.getName());



        return ResponseDTO.builder()
                .message(Constants.CREATED)
                .data(adminRepository.save(admin))
                .statusCode(200)
                .build();
    }


}


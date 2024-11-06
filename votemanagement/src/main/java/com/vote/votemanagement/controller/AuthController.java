package com.vote.votemanagement.controller;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.vote.votemanagement.config.TokenProvider;
import com.vote.votemanagement.dto.*;
import com.vote.votemanagement.entity.Admin;
import com.vote.votemanagement.entity.Candidate;
import com.vote.votemanagement.entity.User;
import com.vote.votemanagement.exception.CustomException;
import com.vote.votemanagement.exception.InvalidJwtException;
import com.vote.votemanagement.repository.AdminRepository;
import com.vote.votemanagement.repository.UserRepository;
import com.vote.votemanagement.service.AuthService;
import com.vote.votemanagement.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @Autowired
    TokenProvider tokenProvider;

    Constants constants;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    UserRepository userRepository;


//    @PostMapping("/sign-up/user")
//    public ResponseDTO signUpStudent(@RequestBody UserDto userDto) throws InvalidJwtException {
//
//        return authService.signUpUser(userDto);
//    }
//
////    @PostMapping("/sign-up/tutor")
////    public ResponseDTO signUpTutor(@RequestBody TutorDTO tutorDTO) throws InvalidJwtException {
////
////        return authService.signUpTutor(tutorDTO);
////    }
//
//
//    @PostMapping("sign-up/admin")
//    public ResponseDTO signUpAdmin(@RequestBody AdminDTO adminDTO)throws InvalidJwtException{
//        return authService.signUpAdmin(adminDTO);
//    }
//
//    @PostMapping("sign-up/candidate")
//    public ResponseDTO signUpCandidate(@RequestBody CandidateDTO candidateDTO)throws InvalidJwtException{
////        if (candidateDTO.getPollId() == null) {
////            throw new InvalidJwtException("Poll ID must not be null");
////        }
//        return authService.signUpCandidate(candidateDTO);
//    }

    @PostMapping("/sign-in")
    public ResponseDTO signIn(@RequestBody SignInDto user) throws AuthenticationException,CustomException {


        // var userNamePassword = new UsernamePasswordAuthenticationToken((tokenProvider.emailValidation(user.getEmail())),tokenProvider.passwordValidation(user.getPassword()));
        // var userNamePassword = new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword());


        try {

            if (!tokenProvider.emailValidation(user.getEmail())) {
                throw new CustomException("Email is not in the correct format");
            }
            if (!tokenProvider.passwordValidation(user.getPassword())) {
                throw new CustomException("Password is not valid");
            }

            var userNamePassword = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
            var authorizedUser = authenticationManager.authenticate(userNamePassword);
            var principal = authorizedUser.getPrincipal();
            String accessToken;
            String refreshToken;

            if (principal instanceof User) {
                accessToken = tokenProvider.generateAccessToken((User) principal);
                refreshToken = tokenProvider.generateRefreshToken((User) principal);
            }
             else if (principal instanceof Admin) {
                accessToken = tokenProvider.generateAccessToken((Admin) principal);
                refreshToken = tokenProvider.generateRefreshToken((Admin) principal);
            }
            else if (principal instanceof Candidate) {
                accessToken = tokenProvider.generateAccessToken((Candidate) principal);
                refreshToken = tokenProvider.generateRefreshToken((Candidate) principal);
            }
             else {
                // handle default case or throw an exception
                accessToken = tokenProvider.generateAccessToken((UserDetails) principal);
                refreshToken = tokenProvider.generateRefreshToken((UserDetails) principal);
            }

            ResponseDTO responseDto = new ResponseDTO();
            responseDto.setMessage(Constants.RETRIEVED);
            responseDto.setData(new JwtDto(accessToken,refreshToken));
            responseDto.setStatusCode(200);
            return responseDto;
        }
        catch(Exception e) {
            System.err.println(e.getMessage());
            ResponseDTO responseDto = new ResponseDTO();
            responseDto.setMessage("Invalid refresh token");
            responseDto.setStatusCode(401);
            return responseDto;
        }
    }



    @PostMapping("/refresh-token")
    public ResponseEntity<ResponseDTO> refreshToken(@RequestBody RefreshTokenDTO refreshTokenDTO) {
        try {
            String refreshToken = refreshTokenDTO.getRefreshToken();
            String newAccessToken = tokenProvider.refreshAccessToken(refreshToken);
            String newRefreshToken = tokenProvider.refreshRefreshToken(refreshToken);

            JwtDto jwtDto = new JwtDto(newAccessToken, newRefreshToken);
            ResponseDTO responseDto = new ResponseDTO();
            responseDto.setMessage(Constants.RETRIEVED);
            responseDto.setData(jwtDto);
            responseDto.setStatusCode(200);

            return ResponseEntity.ok(responseDto);
        } catch (JWTVerificationException e) {
            ResponseDTO responseDto = new ResponseDTO();
            responseDto.setMessage("Invalid refresh token");
            responseDto.setStatusCode(401);
            return ResponseEntity.status(401).body(responseDto);
        }
    }

    @PostMapping("/sign-up")
    public ResponseDTO signUp(@RequestBody SignUpRequest signUpRequest) throws CustomException {
        return authService.signUp(signUpRequest);
    }


    @PostMapping("/signout")
    public ResponseEntity<SignoutResponseDto> signOut(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        SignoutResponseDto response = authService.signOut(token);
        return ResponseEntity.ok(response);
    }

}

package com.vote.votemanagement.controller;


import com.vote.votemanagement.entity.User;
import com.vote.votemanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

   @PostMapping("/create")
    public User createUser(@RequestBody User user){
       return this.userService.createUser(user);
   }

   @GetMapping
    public List<User> getAllUsers(){
       return this.userService.getAllusers();
   }

   @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable Long id){
       return  this.userService.getUserById(id);
   }

   @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id,@RequestBody User user){
       return this.userService.updateUser(id,user);
   }

}

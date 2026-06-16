package com.app.ecom.controller;

import com.app.ecom.dto.UserRequest;
import com.app.ecom.dto.UserResponse;
import com.app.ecom.service.UserService;
import com.app.ecom.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
// this another way of using requestmapping at the class level and it is mostly used versioning of api or the repeated part of the api .
public class UserController {

    private final UserService userService;

//    public UserController(UserService userList) {
//        this.userList = userList;
//    }

    @GetMapping
//    @RequestMapping(value = "/api/users",method = RequestMethod.GET)// this is the just another way of
    // using getmapiing is the just the internal implementation of the getmapping nad this one way of  using requestmaping at method level.
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return new ResponseEntity<>(userService.FetchAllUser(), HttpStatus.OK);
//        return  ResponseEntity.ok(userService.FetchAllUser());
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserRequest userRequest) {
        userService.addUser(userRequest);
        return ResponseEntity.ok("User added successfully");
    }

    @GetMapping("{id}")
    public ResponseEntity<UserResponse> getAllUser(@PathVariable Long id) {
//        User user = userService.fetchUser(id);
//        if (user == null){
//            return ResponseEntity.notFound().build();
//        }
//        return new ResponseEntity<>(user, HttpStatus.OK);

        return userService.fetchUser(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("{id}")
    public ResponseEntity<String> createUser(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        boolean updated = userService.updateUser(id, userRequest);
        if (updated) {
            return ResponseEntity.ok("User updated successfully");
        }
        return ResponseEntity.notFound().build();
    }


}
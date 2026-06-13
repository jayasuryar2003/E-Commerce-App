package com.app.ecom;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final  UserService userService;

//    public UserController(UserService userList) {
//        this.userList = userList;
//    }

    @GetMapping("/api/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.FetchAllUser(), HttpStatus.OK);
//        return  ResponseEntity.ok(userService.FetchAllUser());
    }

    @PostMapping("/api/users")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        userService.addUser(user);
        return ResponseEntity.ok("User added successfully");
    }

    @GetMapping("/api/users/{id}")
    public ResponseEntity<User> getAllUser(@PathVariable Long id) {
//        User user = userService.fetchUser(id);
//        if (user == null){
//            return ResponseEntity.notFound().build();
//        }
//        return new ResponseEntity<>(user, HttpStatus.OK);

        return userService.fetchUser(id)
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }

}
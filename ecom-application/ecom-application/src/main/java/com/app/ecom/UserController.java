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
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.FetchAllUser(), HttpStatus.OK);
//        return  ResponseEntity.ok(userService.FetchAllUser());
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody User user) {
        userService.addUser(user);
        return ResponseEntity.ok("User added successfully");
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getAllUser(@PathVariable Long id) {
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
    public ResponseEntity<String> createUser(@PathVariable Long id, @RequestBody User updatedUser) {
        boolean updated = userService.updateUser(id, updatedUser);
        if (updated) {
            return ResponseEntity.ok("User updated successfully");
        }
        return ResponseEntity.notFound().build();
    }


}
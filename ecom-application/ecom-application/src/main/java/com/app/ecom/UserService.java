package com.app.ecom;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private List<User> userList = new ArrayList<>();
    private Long userCount = 1L;
    public List<User> FetchAllUser(){
        return userList;
    }
    public void addUser( User user) {
        user.setId(userCount++);
        userList.add(user);
    }

}

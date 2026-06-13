package com.app.ecom;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public Optional<User> fetchUser(Long id) {
//        for (User user : userList){
//            if(user.getId().equals(id)){
//                return user;
//            }
//        }
//        return  null;
        return userList.stream()
                .filter(user-> user.getId().equals(id))
                .findFirst();
    }


    public  boolean updateUser(Long id, User updateUser){
        return userList.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .map(existingUser ->  {
                    existingUser.setFirstName(updateUser.getFirstName());
                    existingUser.setLastName(updateUser.getLastName());
                    return  true;
                }).orElse(false);

    }

}

package com.app.ecom;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
//    public UserService(UserrRepository userRepository) { instead of this we can add required args constructor
//        this.userRepository = userRepository;
//    }

    //private List<User> userList = new ArrayList<>();
    private Long userCount = 1L;



    public List<User> FetchAllUser(){
//        return userList;
        return userRepository.findAll();
    }
    public void addUser( User user) {
//        user.setId(userCount++);
//        userList.add(user);
        userRepository.save(user);
    }

    public Optional<User> fetchUser(Long id) {
//        for (User user : userList){
//            if(user.getId().equals(id)){
//                return user;
//            }
//        }
//        return  null;
//        return userList.stream()
//                .filter(user-> user.getId().equals(id))
//                .findFirst();
        return userRepository.findById(id);
    }


    public  boolean updateUser(Long id, User updateUser){
//        return userList.stream()
//                .filter(user -> user.getId().equals(id))
//                .findFirst()
//                .map(existingUser ->  {
//                    existingUser.setFirstName(updateUser.getFirstName());
//                    existingUser.setLastName(updateUser.getLastName());
//                    return  true;
//                }).orElse(false);
        return userRepository.findById(id)
                 .map(existingUser ->  {
                    existingUser.setFirstName(updateUser.getFirstName());
                    existingUser.setLastName(updateUser.getLastName());
                    userRepository.save(existingUser);
                    return  true;
                }).orElse(false);

    }

}

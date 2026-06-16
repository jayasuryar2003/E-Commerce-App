package com.app.ecom.service;

import com.app.ecom.dto.AddressDto;
import com.app.ecom.dto.UserRequest;
import com.app.ecom.dto.UserResponse;
import com.app.ecom.model.Address;
import com.app.ecom.model.User;
import com.app.ecom.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
//    public UserService(UserrRepository userRepository) { instead of this we can add required args constructor
//        this.userRepository = userRepository;
//    }

    //private List<User> userList = new ArrayList<>();
    private Long userCount = 1L;



    public List<UserResponse> FetchAllUser(){
//        return userList;
        return userRepository.findAll().stream().
                map(this::mapToResponse).
                collect(Collectors.toList());
    }
    public void addUser( UserRequest userRequest) {
//        user.setId(userCount++);
//        userList.add(user);
        User user = new User();
        updateuseFromRequest(user,userRequest);
        userRepository.save(user);
    }



    public Optional<UserResponse> fetchUser(Long id) {
//        for (User user : userList){
//            if(user.getId().equals(id)){
//                return user;
//            }
//        }
//        return  null;
//        return userList.stream()
//                .filter(user-> user.getId().equals(id))
//                .findFirst();
        return userRepository.findById(id).map(this::mapToResponse);//--> for collection objects we need to use stream and collectors
        // for a single object we can directly use map function
    }


    public  boolean updateUser(Long id, UserRequest userRequest){
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
//                    existingUser.setFirstName(userRequest.getFirstName());
//                    existingUser.setLastName(userRequest.getLastName());
                     updateuseFromRequest(existingUser,userRequest);
                    userRepository.save(existingUser);
                    return  true;
                }).orElse(false);

    }

    private UserResponse mapToResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setId(String.valueOf(user.getId()));
        userResponse.setEmaill(user.getEmaill());
        userResponse.setPhone(user.getPhone());
        userResponse.setUserRole(user.getUserRole());

        if(user.getAddress() != null) {
            AddressDto addressDto = new AddressDto();
            addressDto.setStreet(user.getAddress().getStreet());
            addressDto.setCity(user.getAddress().getCity());
            addressDto.setState(user.getAddress().getState());
            addressDto.setZipcode(user.getAddress().getZipcode());
            userResponse.setAddress(addressDto);
        }
        return userResponse;
    }

    private void updateuseFromRequest(User user, UserRequest userRequest) {
                          user.setFirstName(userRequest.getFirstName());
                          user.setLastName(userRequest.getLastName());
                          user.setEmaill(userRequest.getEmaill());
                          user.setPhone(userRequest.getPhone());

                          if(userRequest.getAddress() != null)
                          {
                              Address address = new Address();
                              address.setStreet(userRequest.getAddress().getStreet());
                              address.setCity(userRequest.getAddress().getCity());
                              address.setState(userRequest.getAddress().getState());
                              address.setZipcode(userRequest.getAddress().getZipcode());
                              user.setAddress(address);
                          }    }

}

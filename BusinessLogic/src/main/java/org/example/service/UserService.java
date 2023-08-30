package org.example.service;

import org.example.BadInput;
import org.example.repository.UserRepository;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserModel getUser(long id){
        if(id <= 0){
            throw new BadInput("id",
                    Long.toString(id),
                    "Id cannot be less then or equal to 0");
        }
        return userRepository.getUser(id);
    }

    public UserModel createUser(UserModel userModel){
        return userRepository.createUser(userModel);
    }

    public UserModel updateUser(long id, UpdateUserModel userModel){
        return userRepository.updateUser(id,userModel);
    }

    public void deleteUser(int id){
        userRepository.deleteUser(id);
    }
}

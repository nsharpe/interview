package org.example.users;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserModel getUser(UUID id){
        return userRepository.getUser(id);
    }

    public UserModel createUser(UserModel userModel){
        return userRepository.createUser(userModel);
    }

    public UserModel updateUser(UUID id, UpdateUserModel userModel){
        return userRepository.updateUser(id,userModel);
    }

    @Transactional
    public void deleteUser(UUID id){
        userRepository.deleteUser(id);
    }

    public List<UUID> getAllUserIds(){
        return userRepository.getAllUserIds();
    }
}

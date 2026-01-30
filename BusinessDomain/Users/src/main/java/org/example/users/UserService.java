package org.example.users;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

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

    public Page<UserModel> getAllUsers(Pageable pageable){
        return userRepository.getAllUsers(pageable);
    }
}

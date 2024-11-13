package org.example.rest.controllers.user;

import org.example.service.UserModel;

/**
 * This is all the information required to create a user.
 * This is separate from the `UserModel` class as there are different requirements from creation, views and puts
 *
 *
 */
public class CreateUserModel {

    private String email;
    private String name;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserModel toUserModel(){
        UserModel toReturn = new UserModel();

        toReturn.setEmail(email);
        toReturn.setName(name);

        return toReturn;
    }
}

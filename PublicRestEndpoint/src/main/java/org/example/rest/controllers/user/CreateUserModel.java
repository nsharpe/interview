package org.example.rest.controllers.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.service.user.UserModel;
import org.modelmapper.ModelMapper;

/**
 * This is all the information required to create a user.
 * This is separate from the `UserModel` as user model is for all ways of creating a user.  This class is intended for public facing validation and naming conventions
 *
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserModel {

    private static ModelMapper MODEL_MAPPER = new ModelMapper();

    @NotNull
    @NotEmpty
    private String email;
    @NotNull
    @NotEmpty
    private String firstName;
    @NotNull
    @NotEmpty
    private String lastName;

    public UserModel toUserModel(){
        return MODEL_MAPPER.map(this, UserModel.class);
    }
}

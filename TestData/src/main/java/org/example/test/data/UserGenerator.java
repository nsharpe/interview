package org.example.test.data;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.publicrest.sdk.api.UserControllerApi;
import org.example.publicrest.sdk.invoker.ApiClient;
import org.example.publicrest.sdk.models.CreateUserModel;
import org.example.publicrest.sdk.models.UserModel;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static org.example.test.data.TestUtil.FAKER;

@Service
public class UserGenerator implements Generator<UserGenerator.UserInput, UserModel> {

    private final UserControllerApi userControllerApi;
    private final AuthenticationGenerator authenticationGenerator;

    public UserGenerator(ApiClient apiClient,
                         AuthenticationGenerator authenticationGenerator) {
        this.userControllerApi = new UserControllerApi(apiClient);
        this.authenticationGenerator = Objects.requireNonNull(authenticationGenerator);
    }

    @Override
    public UserModel save(UserInput input) {
        userControllerApi.getApiClient()
                .setBearerToken(authenticationGenerator.getAdminBearerToken());
        return userControllerApi.create(input.createUserModel)
                .block();
    }

    @Override
    public UserInput generateInput() {
        CreateUserModel createUserModel = new CreateUserModel();
        createUserModel.setEmail(FAKER.internet().emailAddress());
        createUserModel.setFirstName(FAKER.name().firstName());
        createUserModel.setLastName(FAKER.name().lastName());

        return UserInput.builder()
                .createUserModel(createUserModel)
                .build();
    }

    @Getter
    @Setter
    @Builder
    public static class UserInput{
        private CreateUserModel createUserModel;
    }
}

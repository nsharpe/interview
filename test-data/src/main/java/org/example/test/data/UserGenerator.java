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
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

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
        createUserModel.setFirstName(FAKER.name().firstName());
        createUserModel.setLastName(FAKER.name().lastName());

        switch (ThreadLocalRandom.current().nextInt(2)) {
            case 0:
                createUserModel.setEmail(FAKER.internet().emailAddress(createUserModel.getFirstName() + " "
                        + createUserModel.getLastName() + " "
                        + ThreadLocalRandom.current().nextInt(2000)));
                break;
            case 1:
                createUserModel.setEmail(FAKER.internet().emailAddress(FAKER.word().adjective() + " "
                        + FAKER.word().noun() + " "
                        + ThreadLocalRandom.current().nextInt(2000)));
                break;
            default:
                createUserModel.setEmail(FAKER.internet().emailAddress());
        }

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

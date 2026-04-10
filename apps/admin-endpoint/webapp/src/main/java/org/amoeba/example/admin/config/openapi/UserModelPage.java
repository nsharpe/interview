package org.amoeba.example.admin.config.openapi;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.amoeba.example.users.UserModel;
import org.amoeba.example.web.OpenApiPage;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserModelPage extends OpenApiPage {
    @Schema(description = "The list of users")
    private List<UserModel> content;


}

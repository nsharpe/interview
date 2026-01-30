package org.example.admin.config.openapi;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.users.UserModel;
import org.example.web.OpenApiPage;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserModelPage extends OpenApiPage {
    @Schema(description = "The list of users")
    private List<UserModel> content;


}

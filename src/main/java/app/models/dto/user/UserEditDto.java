package app.models.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserEditDto {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String profilePicture;
}

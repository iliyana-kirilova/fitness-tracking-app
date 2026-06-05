package app.models.dto.user;
import app.models.entity.user.Country;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRegisterRequest {
    @NotBlank
    @Size(min = 2, max = 30)
    private String firstName;
    @NotBlank
    @Size(min = 2, max = 30)
    private String lastName;
    @Size(min = 6, message = "Username must be at least 6 characters")
    private String username;
    @NotBlank
    private String email;
    @NotNull(message = "Country must not be null")
    private Country country;
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
    

}

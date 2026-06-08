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
    @Size(min = 2, max = 30 , message = "First name must be between 2 and 30 characters")
    private String firstName;
    @NotBlank
    @Size(min = 2, max = 30, message = "Last name must be between 2 and 30 characters")
    private String lastName;
    @Size(min = 6, message = "Username must be at least 6 characters")
    private String username;
    @NotBlank
    @Size(min = 8, message = "Email must be at least 8 characters")
    private String email;
    @NotNull(message = "Country must not be null")
    private Country country;
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
    

}

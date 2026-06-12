package app.models.dto.userProfile;

import app.models.entity.userProfile.ActivityLevel;
import app.models.entity.userProfile.FitnessGoal;
import app.models.entity.userProfile.Gender;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserProfileRequestDto {
    @NotNull(message = "Please select your gender")
    private Gender gender;

    @NotNull(message = "Height is required")
    @Min(value = 100, message = "Height must be at least 100 cm")
    @Max(value = 250, message = "Height cannot exceed 250 cm")
    private Integer height;


    @NotNull(message = "Weight is required")
    @DecimalMin(value = "30.0", message = "Weight must be at least 30 kg")
    @DecimalMax(value = "300.0", message = "Weight cannot exceed 300 kg")
    private Double weight;

    @NotNull(message = "Age is required")
    @Min(value = 10, message = "Age must be at least 10")
    @Max(value = 120, message = "Age cannot exceed 120")
    private Integer age;

    @NotNull(message = "Please select your activity level")
    private ActivityLevel activityLevel;

    @NotNull(message = "Please select your fitness goal")
    private FitnessGoal goal;
}

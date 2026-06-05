package app.models.dto.userProfile;

import app.models.entity.user.User;
import app.models.entity.userProfile.ActivityLevel;
import app.models.entity.userProfile.FitnessGoal;
import app.models.entity.userProfile.Gender;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UserProfileDto {
    private UUID id;
    private Gender gender;
    private Integer height;
    private Double weight;
    private Integer age;
    private ActivityLevel activityLevel;
    private FitnessGoal goal;
    private User user;
}

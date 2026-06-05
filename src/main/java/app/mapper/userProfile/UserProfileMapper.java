package app.mapper.userProfile;

import app.models.dto.userProfile.UserProfileDto;
import app.models.entity.userProfile.UserProfile;

public class UserProfileMapper {
    public static UserProfileDto toDto(UserProfile userProfile) {
        if (userProfile == null) {
            return null;
        }

        return UserProfileDto.builder()
                .id(userProfile.getId())
                .gender(userProfile.getGender())
                .height(userProfile.getHeight())
                .weight(userProfile.getWeight())
                .age(userProfile.getAge())
                .activityLevel(userProfile.getActivityLevel())
                .goal(userProfile.getGoal())
                .build();
    }
}

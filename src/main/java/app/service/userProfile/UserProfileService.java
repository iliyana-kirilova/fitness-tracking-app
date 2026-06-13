package app.service.userProfile;

import app.mapper.userProfile.UserProfileMapper;
import app.models.dto.userProfile.UserProfileDto;
import app.models.dto.userProfile.UserProfileRequestDto;
import app.models.entity.user.User;
import app.models.entity.userProfile.UserProfile;
import app.repository.user.UserRepository;
import app.repository.userProfile.UserProfileRepository;
import app.service.CaloriesCalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserProfileService {
    private final UserProfileRepository userProfileRepository;
    private final UserRepository userRepository;
    private final CaloriesCalculatorService caloriesCalculatorService;

    @Autowired
    public UserProfileService(UserProfileRepository userProfileRepository, UserRepository userRepository, CaloriesCalculatorService caloriesCalculatorService) {
        this.userProfileRepository = userProfileRepository;
        this.userRepository = userRepository;
        this.caloriesCalculatorService = caloriesCalculatorService;
    }

    public UserProfile createDefaultProfile(User user){
        UserProfile profile = UserProfile.builder()
                .user(user)
                .build();

        user.setUserProfile(profile);
        return userProfileRepository.save(profile);
    }

    //gets profile by user id, if not found returns empty profile
    public UserProfileDto getByUserId(String userId){
        return userProfileRepository.findByUserId(UUID.fromString(userId)).
                map(UserProfileMapper::toDto).
                orElse(UserProfileDto.builder().build());
    }

    public UserProfileDto saveOrUpdateProfile(String userId, UserProfileRequestDto userProfileRequestDto){
        User user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        UserProfile profile = userProfileRepository.findByUserId(UUID.fromString(userId))
                .orElse(UserProfile.builder()
                        .user(user)
                        .build());

        profile.setGender(userProfileRequestDto.getGender());
        profile.setHeight(userProfileRequestDto.getHeight());
        profile.setWeight(userProfileRequestDto.getWeight());
        profile.setAge(userProfileRequestDto.getAge());
        profile.setActivityLevel(userProfileRequestDto.getActivityLevel());
        profile.setGoal(userProfileRequestDto.getGoal());

        UserProfile saved =  userProfileRepository.save(profile);
        return UserProfileMapper.toDto(saved);

    }

    public boolean hasProfile(String userId){
        return userProfileRepository.findByUserId(UUID.fromString(userId)).isPresent();
    }

    public boolean isProfileComplete(UserProfile profile) {
        return profile.getWeight() != null
                && profile.getHeight() != null
                && profile.getAge() != null
                && profile.getGender() != null
                && profile.getActivityLevel() != null
                && profile.getGoal() != null;

    }
}

package app.service.userProfile;

import app.models.entity.user.User;
import app.models.entity.userProfile.UserProfile;
import app.repository.userProfile.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService {
    private final UserProfileRepository userProfileRepository;

    @Autowired
    public UserProfileService(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    public UserProfile createDefaultProfile(User user){
        UserProfile profile = UserProfile.builder()
                .user(user)
                .build();

        user.setUserProfile(profile);
        return userProfileRepository.save(profile);
    }
}

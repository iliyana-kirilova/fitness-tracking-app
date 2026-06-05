package app.service.user;

import app.mapper.user.UserMapper;
import app.models.dto.user.UserDto;
import app.models.dto.user.UserRegisterRequest;
import app.models.entity.user.User;
import app.repository.user.UserRepository;
import app.service.userProfile.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserProfileService userProfileService;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserProfileService userProfileService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userProfileService = userProfileService;
    }

    public UserDto register(UserRegisterRequest userRegisterRequest) {

        //check if username exists
        userRepository.findByUsername(userRegisterRequest.getUsername())
                .ifPresent(user -> {
                    throw new RuntimeException("User with this username already exists!");
                });

        //encoded password
        String encodedPassword = passwordEncoder.encode(userRegisterRequest.getPassword());
        userRegisterRequest.setPassword(encodedPassword);

        //this is where we map the UserRegisterRequest to a User entity, which will be saved in the database
        User userEntity = UserMapper.toUserEntity(userRegisterRequest);
        User savedUser = userRepository.save(userEntity);

        // create default userprofile setup for the newly registered user
        userProfileService.createDefaultProfile(savedUser);

        return UserMapper.toUserDto(savedUser);


    }

}

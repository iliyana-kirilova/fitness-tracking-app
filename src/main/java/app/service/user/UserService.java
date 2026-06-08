package app.service.user;

import app.mapper.user.UserMapper;
import app.models.dto.user.UserDto;
import app.models.dto.user.UserEditDto;
import app.models.dto.user.UserLoginRequest;
import app.models.dto.user.UserRegisterRequest;
import app.models.entity.user.User;
import app.repository.user.UserRepository;
import app.service.userProfile.UserProfileService;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    public UserDto login(UserLoginRequest userLoginRequest) {
        Optional<User> optionalUser = userRepository.findByUsername(userLoginRequest.getUsername());

        if (optionalUser.isEmpty() ||
        !passwordEncoder.matches(userLoginRequest.getPassword(), optionalUser.get().getPassword())){
            throw new RuntimeException("Username or password mismatch!");
        }

        return UserMapper.toUserDto(optionalUser.get());
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

    public List<UserDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toUserDto)
                .toList();
    }

    public UserDto getById(String id) {
        User user = userRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException("User with id [%s] does not exist.".formatted(id)));
        return UserMapper.toUserDto(user);
    }

    public UserDto update(String id, UserEditDto userEditDto) {

        User entity = userRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException("User with id [%s] does not exist.".formatted(id)));

        entity.setUsername(userEditDto.getUsername());
        entity.setFirstName(userEditDto.getFirstName());
        entity.setLastName(userEditDto.getLastName());
        entity.setEmail(userEditDto.getEmail());
        entity.setProfilePicture(userEditDto.getProfilePicture());

        User updatedUser = userRepository.save(entity);

        return UserMapper.toUserDto(updatedUser);
    }
}


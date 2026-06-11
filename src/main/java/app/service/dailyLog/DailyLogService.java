package app.service.dailyLog;

import app.mapper.dailyLog.DailyLogMapper;
import app.mapper.userProfile.UserProfileMapper;
import app.models.dto.dailyLog.DailyLogDto;
import app.models.dto.workout.WorkoutDto;
import app.models.entity.dailyLog.DailyLog;
import app.models.entity.user.User;
import app.repository.dailyLog.DailyLogRepository;
import app.repository.user.UserRepository;
import app.repository.userProfile.UserProfileRepository;
import app.service.CaloriesCalculatorService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DailyLogService {

    private final DailyLogRepository dailyLogRepository;
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final CaloriesCalculatorService calculatorService;

    public DailyLogService(DailyLogRepository dailyLogRepository, UserRepository userRepository, UserProfileRepository userProfileRepository, CaloriesCalculatorService calculatorService) {
        this.dailyLogRepository = dailyLogRepository;
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
        this.calculatorService = calculatorService;
    }

    public DailyLogDto createEmptyLog(String id) {
        UUID  uuid = UUID.fromString(id);

        Optional<DailyLog> existingDailyLog =
                dailyLogRepository.findByUser_IdAndLogDate(uuid, LocalDate.now());;

        if (existingDailyLog.isPresent()) {
            return DailyLogMapper.toDailyLogDto(existingDailyLog.get());
        }


        User user = userRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));


        DailyLog emptyLog = DailyLog.builder()
                .logDate(LocalDate.now())
                .waterIntake(0)
                .caloriesConsumed(0)
                .proteinConsumed(0.0)
                .carbsConsumed(0.0)
                .fatsConsumed(0.0)
                .user(user)
                .build();

        DailyLog savedLog = dailyLogRepository.save(emptyLog);

        return DailyLogMapper.toDailyLogDto(savedLog);
    }

    public List<DailyLogDto> getAllLogs() {

        return dailyLogRepository.findAllByOrderByLogDateDesc()
                .stream()
                .map(DailyLogMapper::toDailyLogDto)
                .toList();
    }


    public DailyLogDto getLogById(String id) {
        DailyLog dailyLog = dailyLogRepository
                .findById(UUID.fromString(id))
                .orElseThrow(() ->
                        new RuntimeException(
                                "Daily log not found with id: " + id));

        DailyLogDto dto = DailyLogMapper.toDailyLogDto(dailyLog);

        //caloriesBurned -> workout sum
        int burned = dto.getWorkoutList().stream()
                .mapToInt(WorkoutDto::getCaloriesBurned).sum();

        dto.setCaloriesBurned(burned);

        int targetCalories = userProfileRepository
                .findByUserId(dailyLog.getUser().getId())
                .map(profile -> calculatorService.calculateTargetCalories(
                        UserProfileMapper.toDto(profile)))
                .orElse(0);

        dto.setTargetCalories(targetCalories);

        return dto;
    }

    public void deleteLog(String id) {
        dailyLogRepository.deleteById(
                UUID.fromString(id)
        );
    }

    public DailyLogDto getTodayLog(String id) {

        return dailyLogRepository.findByUser_IdAndLogDate(UUID.fromString(id), LocalDate.now())
                .map(DailyLogMapper::toDailyLogDto)
                .orElse(null);
    }


}

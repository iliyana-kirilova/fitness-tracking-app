package app.service.dailyLog;

import app.mapper.dailyLog.DailyLogMapper;
import app.models.dto.dailyLog.DailyLogDto;
import app.models.entity.dailyLog.DailyLog;
import app.models.entity.user.User;
import app.repository.dailyLog.DailyLogRepository;
import app.repository.user.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DailyLogService {

    private final DailyLogRepository dailyLogRepository;
    private final UserRepository userRepository;

    public DailyLogService(DailyLogRepository dailyLogRepository, UserRepository userRepository) {
        this.dailyLogRepository = dailyLogRepository;
        this.userRepository = userRepository;
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

        return DailyLogMapper.toDailyLogDto(dailyLog);
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

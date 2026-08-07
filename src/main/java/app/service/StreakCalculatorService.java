package app.service;

import app.models.entity.dailyLog.DailyLog;
import app.repository.dailyLog.DailyLogRepository;
import app.repository.workout.WorkoutRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class StreakCalculatorService {
    private final DailyLogRepository dailyLogRepository;
    private final WorkoutRepository workoutRepository;

    public StreakCalculatorService(DailyLogRepository dailyLogRepository, WorkoutRepository workoutRepository) {
        this.dailyLogRepository = dailyLogRepository;
        this.workoutRepository = workoutRepository;
    }

    public int calculateConsecutiveDays(UUID userId) {
        List<DailyLog> logs = dailyLogRepository
                .findByUser_IdOrderByLogDateDesc(userId);

        if (logs.isEmpty()) return 0;

        int streak = 0;
        LocalDate expected = LocalDate.now();

        for (DailyLog log : logs) {
            if (log.getLogDate().equals(expected)) {
                streak++;
                expected = expected.minusDays(1);
            } else {
                break;
            }
        }
        return streak;
    }

    public int countWorkoutsThisWeek(UUID userId) {
        LocalDate startOfWeek = LocalDate.now().with(DayOfWeek.MONDAY);
        return workoutRepository
                .countByDailyLog_User_IdAndDailyLog_LogDateGreaterThanEqual(
                        userId, startOfWeek);
    }


}

package app.service.workout;

import app.mapper.workout.WorkoutMapper;
import app.models.dto.workout.WorkoutDto;
import app.models.dto.workout.WorkoutRequestDto;
import app.models.entity.dailyLog.DailyLog;
import app.models.entity.workout.Workout;
import app.repository.dailyLog.DailyLogRepository;
import app.repository.workout.WorkoutRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class WorkoutService {
    private final WorkoutRepository workoutRepository;
    private final DailyLogRepository dailyLogRepository;


    public WorkoutService(WorkoutRepository workoutRepository, DailyLogRepository dailyLogRepository) {
        this.workoutRepository = workoutRepository;
        this.dailyLogRepository = dailyLogRepository;
    }

    public void addWorkoutToLog(String logId, WorkoutRequestDto workoutRequestDto) {
        DailyLog log = findLogById(logId);
        
        Workout workout = Workout.builder()
                .type(workoutRequestDto.getType())
                .duration(workoutRequestDto.getDuration())
                .caloriesBurned(workoutRequestDto.getCaloriesBurned())
                .dailyLog(log)
                .build();
        
        workoutRepository.save(workout);
    }

    public String updateWorkout(String workoutId, WorkoutRequestDto workoutRequestDto) {
        Workout workout = workoutRepository.findById(UUID.fromString(workoutId))
                .orElseThrow(() -> new RuntimeException("Workout with id: " + workoutId + " not found"));

        workout.setType(workoutRequestDto.getType());
        workout.setDuration(workoutRequestDto.getDuration());
        workout.setCaloriesBurned(workoutRequestDto.getCaloriesBurned());

        workoutRepository.save(workout);
        return workout.getDailyLog().getId().toString();
    }

    public String deleteWorkout(String workoutId) {
        Workout workout = workoutRepository.findById(UUID.fromString(workoutId))
                .orElseThrow(() -> new RuntimeException("Workout not found: " + workoutId));

        String logId = workout.getDailyLog().getId().toString();
        workoutRepository.delete(workout);
        return logId;
    }

    public WorkoutDto getById(String id) {
        return workoutRepository.findById(UUID.fromString(id))
                .map(WorkoutMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Workout not found: " + id));
    }

    private DailyLog findLogById(String logId) {
        return dailyLogRepository.findById(UUID.fromString(logId))
                .orElseThrow(() -> new RuntimeException("Daily log not found: " + logId));
    }
}

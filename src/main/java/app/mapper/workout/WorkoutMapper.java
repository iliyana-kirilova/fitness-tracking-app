package app.mapper.workout;

import app.models.dto.workout.WorkoutDto;
import app.models.entity.workout.Workout;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class WorkoutMapper {

    public static WorkoutDto toDto(Workout workout) {
        if (workout == null) {
            return null;
        }

        return WorkoutDto.builder()
                .id(workout.getId())
                .type(workout.getType())
                .duration(workout.getDuration())
                .caloriesBurned(workout.getCaloriesBurned())
                .dailyLogId(workout.getDailyLog().getId())
                .build();
    }
}

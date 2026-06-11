package app.models.dto.workout;

import app.models.entity.workout.WorkoutType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WorkoutRequestDto {
    private WorkoutType type;
    private Integer duration;
    private Integer caloriesBurned;
}

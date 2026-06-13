package app.models.dto.workout;

import app.models.entity.workout.WorkoutType;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class WorkoutDto {

    private UUID id;
    private WorkoutType type;
    private Integer duration;
    private Integer caloriesBurned;
    private UUID dailyLogId;
}

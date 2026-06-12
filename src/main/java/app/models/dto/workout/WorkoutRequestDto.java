package app.models.dto.workout;

import app.models.entity.workout.WorkoutType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WorkoutRequestDto {
    @NotNull(message = "Please select a workout type")
    private WorkoutType type;

    @NotNull(message = "Duration is required")
    @Min(value = 1, message = "Duration must be at least 1 minute")
    @Max(value = 600, message = "Duration cannot exceed 600 minutes (10 hours)")
    private Integer duration;

    @NotNull(message = "Calories burned is required")
    @Min(value = 0, message = "Calories burned cannot be negative")
    @Max(value = 5000, message = "Calories burned seem unrealistically high")
    private Integer caloriesBurned;
}

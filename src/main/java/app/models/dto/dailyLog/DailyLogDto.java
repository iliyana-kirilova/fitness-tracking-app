package app.models.dto.dailyLog;

import app.models.entity.meal.Meal;
import app.models.entity.user.User;
import app.models.entity.workout.Workout;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class DailyLogDto {
    private UUID id;
    private LocalDate logDate;
    private Integer waterIntake;
    private Integer caloriesConsumed;
    private Double proteinConsumed;
    private Double carbsConsumed;
    private Double fatsConsumed;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private User user;
    private List<Meal> mealsList; //както UserDto(SmartWallet) -> MealDto
    private List<Workout> workoutList; //както UserDto(SmartWallet) -> WorkoutDto
}

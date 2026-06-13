package app.models.dto.dailyLog;

import app.models.dto.meal.MealDto;
import app.models.dto.workout.WorkoutDto;
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
    private Integer caloriesBurned;
    private Integer targetCalories;
    private Integer caloriesProgress;
    private Integer targetProtein;
    private Integer targetCarbs;
    private Integer targetFats;
    private Double proteinConsumed;
    private Double carbsConsumed;
    private Double fatsConsumed;
    private UUID userId;
    private List<MealDto> mealsList;
    private List<WorkoutDto> workoutList;
}

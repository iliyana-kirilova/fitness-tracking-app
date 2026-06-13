package app.mapper.dailyLog;

import app.mapper.meal.MealMapper;
import app.mapper.workout.WorkoutMapper;
import app.models.dto.dailyLog.DailyLogDto;
import app.models.dto.meal.MealDto;
import app.models.dto.workout.WorkoutDto;
import app.models.entity.dailyLog.DailyLog;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
public class DailyLogMapper {
    public static DailyLogDto toDailyLogDto (DailyLog dailyLog) {
        if (dailyLog == null) {
            return null;
        }

        List<MealDto> mealsList =
                dailyLog.getMealsList() ==null?
                        List.of():
                        dailyLog.getMealsList().stream()
                .map(MealMapper::toDto)
                .toList();

        List<WorkoutDto> workoutList = dailyLog.getWorkoutList() == null ?
                List.of() :
                dailyLog.getWorkoutList().stream()
                        .map(WorkoutMapper::toDto)
                        .toList();

        return DailyLogDto.builder()
                .id(dailyLog.getId())
                .logDate(dailyLog.getLogDate())
                .waterIntake(dailyLog.getWaterIntake())
                .caloriesConsumed(dailyLog.getCaloriesConsumed())
                .proteinConsumed(dailyLog.getProteinConsumed())
                .carbsConsumed(dailyLog.getCarbsConsumed())
                .fatsConsumed(dailyLog.getFatsConsumed())
                .userId(dailyLog.getUser().getId())
                .mealsList(mealsList)
                .workoutList(workoutList)
                .build();
    }
}

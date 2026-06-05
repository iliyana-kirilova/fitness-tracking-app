package app.mapper.dailyLog;

import app.models.dto.dailyLog.DailyLogDto;
import app.models.entity.dailyLog.DailyLog;

public class DailyLogMapper {
    public static DailyLogDto  toDailyLogDto(DailyLog dailyLog) {
        if (dailyLog == null) {
            return null;
        }

        return DailyLogDto.builder()
                .id(dailyLog.getId())
                .logDate(dailyLog.getLogDate())
                .waterIntake(dailyLog.getWaterIntake())
                .caloriesConsumed(dailyLog.getCaloriesConsumed())
                .proteinConsumed(dailyLog.getProteinConsumed())
                .carbsConsumed(dailyLog.getCarbsConsumed())
                .fatsConsumed(dailyLog.getFatsConsumed())
                .createdOn(dailyLog.getCreatedOn())
                .updatedOn(dailyLog.getUpdatedOn())
                .user(dailyLog.getUser())
                .mealsList(dailyLog.getMealsList())
                .workoutList(dailyLog.getWorkoutList())
                .build();
    }
}

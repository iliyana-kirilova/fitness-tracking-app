package app.mapper.meal;

import app.models.dto.meal.MealDto;
import app.models.entity.meal.Meal;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MealMapper {
        public static MealDto toDto(Meal meal) {
                if (meal == null) {
                    return null;
                }

                return MealDto.builder()
                        .id(meal.getId())
                        .name(meal.getName())
                        .type(meal.getType())
                        .calories(meal.getCalories())
                        .protein(meal.getProtein())
                        .carbs(meal.getCarbs())
                        .fats(meal.getFats())
                        .dailyLogId(meal.getDailyLog().getId())
                        .build();
        }
}

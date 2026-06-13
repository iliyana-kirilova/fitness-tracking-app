package app.models.dto.meal;

import app.models.entity.dailyLog.DailyLog;
import app.models.entity.meal.MealType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class MealDto {

    private UUID id;
    private String name;
    private MealType type;
    private Integer calories;
    private Double protein;
    private Double carbs;
    private Double fats;
    private UUID dailyLogId;
}

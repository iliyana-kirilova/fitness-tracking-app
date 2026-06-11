package app.models.dto.meal;

import app.models.entity.meal.MealType;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MealRequestDto {

    private String name;
    private MealType type;
    private Integer calories;
    private Double protein;
    private Double fats;
    private Double carbs;
}

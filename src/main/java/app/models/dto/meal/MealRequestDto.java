package app.models.dto.meal;

import app.models.entity.meal.MealType;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MealRequestDto {

    @Size(min = 2, max = 100, message = "Meal name must be between 2 and 100 characters")
    private String name;
    @NotNull(message = "Please select a meal type")
    private MealType type;

    @NotNull(message = "Calories are required")
    @Min(value = 0, message = "Calories cannot be negative")
    @Max(value = 5000, message = "Calories seem unrealistically high")
    private Integer calories;

    @NotNull(message = "Carbs are required")
    @DecimalMin(value = "0.0", message = "Carbs cannot be negative")
    @DecimalMax(value = "500.0", message = "Carbs value seems unrealistically high")
    private Double protein;

    @NotNull(message = "Fats are required")
    @DecimalMin(value = "0.0", message = "Fats cannot be negative")
    @DecimalMax(value = "300.0", message = "Fats value seems unrealistically high")
    private Double fats;

    @NotNull(message = "Carbs are required")
    @DecimalMin(value = "0.0", message = "Carbs cannot be negative")
    @DecimalMax(value = "500.0", message = "Carbs value seems unrealistically high")
    private Double carbs;
}

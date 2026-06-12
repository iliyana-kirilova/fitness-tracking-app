package app.models.dto.dailyLog;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WaterIntakeRequestDto {

    @NotNull(message = "Please enter an amount")
    @Min(value = 50, message = "Amount must be at least 50 ml")
    @Max(value = 2000, message = "Cannot add more than 2000 ml at once")
    private Integer amount; // in milliliters
}

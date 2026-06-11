package app.models.dto.dailyLog;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WaterIntakeRequestDto {
    private Integer amount; // in milliliters
}

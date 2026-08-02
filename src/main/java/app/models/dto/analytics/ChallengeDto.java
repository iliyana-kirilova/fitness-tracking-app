package app.models.dto.analytics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChallengeDto {
    private UUID id;
    private UUID userId;
    private String challengeType;
    private String challengeDisplayName;
    private LocalDateTime startedAt;
    private LocalDateTime deadline;
    private String status;
    private int progressPercent;
}

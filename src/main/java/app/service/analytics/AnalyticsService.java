package app.service.analytics;

import app.models.dto.analytics.*;
import app.service.analytics.client.AnalyticsClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class AnalyticsService {
    private final AnalyticsClient analyticsClient;

    public AnalyticsService(AnalyticsClient analyticsClient) {
        this.analyticsClient = analyticsClient;
    }

    public void checkAchievements(AchievementCheckRequest request) {
        try {
            List<AchievementDto> newAchievements =
                    analyticsClient.checkAchievements(request).getBody();
            if (newAchievements != null && !newAchievements.isEmpty()) {
                log.info("User [{}] unlocked {} new achievement(s)",
                        request.getUserId(), newAchievements.size());
            }
        } catch (Exception e) {
            log.warn("Analytics service unavailable: {}", e.getMessage());
        }
    }

    public List<AchievementDto> getUserAchievements(UUID userId) {
        try {
            return analyticsClient.getUserAchievements(userId).getBody();
        } catch (Exception e) {
            log.warn("Could not fetch achievements: {}", e.getMessage());
            return List.of();
        }
    }

    public List<AchievementDto> getAllAchievements() {
        try {
            return analyticsClient.getAllAchievements().getBody();
        } catch (Exception e) {
            log.warn("Could not fetch all achievements: {}", e.getMessage());
            return List.of();
        }
    }

    public void archiveAchievement(UUID id, UUID userId) {
        try {
            analyticsClient.archiveAchievement(id, userId);
            log.info("User [{}] archived achievement [{}]", userId, id);
        } catch (Exception e) {
            log.warn("Could not archive achievement: {}", e.getMessage());
        }
    }

    public void deleteAchievement(UUID id) {
        try {
            analyticsClient.deleteAchievement(id);
            log.info("Admin deleted achievement [{}]", id);
        } catch (Exception e) {
            log.warn("Could not delete achievement: {}", e.getMessage());
        }
    }

    public WeeklySummaryDto getWeeklySummary(UUID userId) {
        try {
            return analyticsClient.getWeeklySummary(userId).getBody();
        } catch (Exception e) {
            log.warn("Could not fetch weekly summary: {}", e.getMessage());
            return new WeeklySummaryDto();
        }
    }

    public ChallengeDto startChallenge(UUID userId, String challengeType) {
        ChallengeRequest request = ChallengeRequest.builder()
                .userId(userId)
                .challengeType(challengeType)
                .build();
        try {
            ChallengeDto challenge = analyticsClient.startChallenge(request).getBody();
            log.info("User [{}] started challenge [{}]", userId, challengeType);
            return challenge;
        } catch (Exception e) {
            log.warn("Could not start challenge: {}", e.getMessage());
            return null;
        }
    }

    public void abandonChallenge(UUID id, UUID userId) {
        try {
            analyticsClient.abandonChallenge(id, userId);
            log.info("User [{}] abandoned challenge [{}]", userId, id);
        } catch (Exception e) {
            log.warn("Could not abandon challenge: {}", e.getMessage());
        }
    }

    public void completeChallenge(UUID id) {
        try {
            analyticsClient.completeChallenge(id);
            log.info("Admin completed challenge [{}]", id);
        } catch (Exception e) {
            log.warn("Could not complete challenge: {}", e.getMessage());
        }
    }

    public List<ChallengeDto> getUserChallenges(UUID userId) {
        try {
            return analyticsClient.getUserChallenges(userId).getBody();
        } catch (Exception e) {
            log.warn("Could not fetch challenges: {}", e.getMessage());
            return List.of();
        }
    }

    public List<ChallengeDto> getAllChallenges() {
        try {
            return analyticsClient.getAllChallenges().getBody();
        } catch (Exception e) {
            log.warn("Could not fetch all challenges: {}", e.getMessage());
            return List.of();
        }
    }
}

package app.service.analytics.client;


import app.models.dto.analytics.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "analytics-svc", url = "${analytics.service.url}")
public interface AnalyticsClient {
    @PostMapping("/achievements/check")
    ResponseEntity<List<AchievementDto>> checkAchievements(@RequestBody AchievementCheckRequest request);

    @GetMapping("/achievements")
    ResponseEntity<List<AchievementDto>> getUserAchievements(@RequestParam("userId") UUID userId);

    @GetMapping("/achievements/all")
    ResponseEntity<List<AchievementDto>> getAllAchievements();

    @DeleteMapping("/achievements/{id}")
    ResponseEntity<Void> archiveAchievement(@PathVariable("id") UUID id,
                            @RequestParam("userId") UUID userId);

    @DeleteMapping("/achievements/{id}/admin")
    ResponseEntity<Void> deleteAchievement(@PathVariable("id") UUID id);

    @GetMapping("/weekly-summary")
    ResponseEntity<WeeklySummaryDto> getWeeklySummary(@RequestParam("userId") UUID userId);

    @PostMapping("/challenges")
    ResponseEntity<ChallengeDto> startChallenge(@RequestBody ChallengeRequest request);

    @DeleteMapping("/challenges/{id}")
    ResponseEntity<Void> abandonChallenge(@PathVariable("id") UUID id,
                          @RequestParam("userId") UUID userId);

    @PutMapping("/challenges/{id}/complete")
    ResponseEntity<Void> completeChallenge(@PathVariable("id") UUID id);

    @GetMapping("/challenges")
    ResponseEntity<List<ChallengeDto>> getUserChallenges(@RequestParam("userId") UUID userId);

    @GetMapping("/challenges/all")
    ResponseEntity<List<ChallengeDto>> getAllChallenges();
}

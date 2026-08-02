package app.web.analitycs;

import app.models.dto.analytics.AchievementDto;
import app.models.dto.analytics.ChallengeDto;
import app.models.dto.analytics.WeeklySummaryDto;
import app.service.analytics.AnalyticsService;
import app.service.user.AuthenticationUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/analytics")
public class AnalyticsController {
    public final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping
    public ModelAndView getAnalytics(
            @AuthenticationPrincipal AuthenticationUserDetails principal) {

        UUID userId = principal.getId();

        List<AchievementDto> achievements =
                analyticsService.getUserAchievements(userId);

        Set<String> unlockedTypes = achievements.stream()
                .map(AchievementDto::getAchievementType)
                .collect(Collectors.toSet());

        WeeklySummaryDto weeklySummary =
                analyticsService.getWeeklySummary(userId);

        List<ChallengeDto> activeChallenges =
                analyticsService.getUserChallenges(userId);

        ModelAndView mav = new ModelAndView("analytics");
        mav.addObject("achievements", achievements);
        mav.addObject("weeklySummary",
                analyticsService.getWeeklySummary(userId));
        mav.addObject("activeChallenges",
                analyticsService.getUserChallenges(userId));
        mav.addObject("unlockedTypes", unlockedTypes);
        mav.addObject("challengeTypes",
                analyticsService.getChallengeTypes());
        mav.addObject("allAchievementTypes",
                analyticsService.getAllAchievementTypes());
        return mav;
    }


    @DeleteMapping("/achievements/{id}")
    public String archiveAchievement(
            @PathVariable UUID id,
            @AuthenticationPrincipal AuthenticationUserDetails principal) {
        analyticsService.archiveAchievement(id, principal.getId());
        return "redirect:/analytics";
    }

    @PostMapping("/challenges")
    public String startChallenge(
            @RequestParam String challengeType,
            @AuthenticationPrincipal AuthenticationUserDetails principal) {
        analyticsService.startChallenge(principal.getId(), challengeType);
        return "redirect:/analytics";
    }

    @DeleteMapping("/challenges/{id}")
    public String abandonChallenge(
            @PathVariable UUID id,
            @AuthenticationPrincipal AuthenticationUserDetails principal) {
        analyticsService.abandonChallenge(id, principal.getId());
        return "redirect:/analytics";
    }
}

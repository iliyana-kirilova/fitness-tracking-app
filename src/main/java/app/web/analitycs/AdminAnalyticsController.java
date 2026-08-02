package app.web.analitycs;

import app.models.dto.analytics.AchievementDto;
import app.models.dto.analytics.ChallengeDto;
import app.service.analytics.AnalyticsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/admin/analytics")
public class AdminAnalyticsController {
    private final AnalyticsService analyticsService;

    public AdminAnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping
    public ModelAndView getAllAnalytics() {
        List<AchievementDto> achievements =
                analyticsService.getAllAchievements();
        List<ChallengeDto> allChallenges =
                analyticsService.getAllChallenges();

        ModelAndView mav = new ModelAndView("admin-analytics");
        mav.addObject("achievements", achievements);
        mav.addObject("allChallenges", allChallenges);
        return mav;
    }

    @DeleteMapping("/achievements/{id}")
    public String deleteAchievement(@PathVariable UUID id) {
        analyticsService.deleteAchievement(id);
        return "redirect:/admin/analytics";
    }

    @PutMapping("/challenges/{id}/complete")
    public String completeChallenge(@PathVariable UUID id) {
        analyticsService.completeChallenge(id);
        return "redirect:/admin/analytics";
    }
}

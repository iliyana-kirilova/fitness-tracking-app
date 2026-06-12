package app.web.userProfileController;

import app.models.dto.dailyLog.DailyLogDto;
import app.models.dto.userProfile.UserProfileRequestDto;
import app.models.entity.userProfile.ActivityLevel;
import app.models.entity.userProfile.FitnessGoal;
import app.models.entity.userProfile.Gender;
import app.service.CaloriesCalculatorService;
import app.service.dailyLog.DailyLogService;
import app.service.userProfile.UserProfileService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/profile")
public class UserProfileController {

    private final UserProfileService userProfileService;
    private final CaloriesCalculatorService calculatorService;
    private final DailyLogService dailyLogService;

    public UserProfileController(UserProfileService userProfileService,
                                 CaloriesCalculatorService calculatorService, DailyLogService dailyLogService) {
        this.userProfileService = userProfileService;
        this.calculatorService = calculatorService;
        this.dailyLogService = dailyLogService;
    }

    @GetMapping("/biometrics")
    public String getBiometricsForm() {
        String userId = "da56da89-db1e-4731-80c1-2650ac93da00";
        DailyLogDto todayLog = dailyLogService.getTodayLog(userId);

        if (todayLog != null) {
            return "redirect:/daily-log/" + todayLog.getId();
        }

        return "redirect:/home";
    }

    @PostMapping("/biometrics/save")
    public String saveBiometrics(@Valid @ModelAttribute UserProfileRequestDto profileRequest,
                                 BindingResult bindingResult) {
        String userId = "da56da89-db1e-4731-80c1-2650ac93da00";

        if (bindingResult.hasErrors()) {
            DailyLogDto todayLog = dailyLogService.getTodayLog(userId);
            if (todayLog != null) {
                return "redirect:/daily-log/" + todayLog.getId();
            }
            return "redirect:/home";
        }

        userProfileService.saveOrUpdateProfile(userId, profileRequest);

        DailyLogDto todayLog = dailyLogService.getTodayLog(userId);
        if (todayLog != null) {
            return "redirect:/daily-log/" + todayLog.getId();
        }
        return "redirect:/home";
    }
}

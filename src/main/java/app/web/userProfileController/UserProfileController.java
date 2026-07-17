package app.web.userProfileController;

import app.models.dto.dailyLog.DailyLogDto;
import app.models.dto.userProfile.UserProfileRequestDto;
import app.models.entity.userProfile.ActivityLevel;
import app.models.entity.userProfile.FitnessGoal;
import app.models.entity.userProfile.Gender;
import app.service.CaloriesCalculatorService;
import app.service.dailyLog.DailyLogService;
import app.service.user.AuthenticationUserDetails;
import app.service.userProfile.UserProfileService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping("/profile")
public class UserProfileController {

    private final UserProfileService userProfileService;
    private final DailyLogService dailyLogService;

    public UserProfileController(UserProfileService userProfileService,
                                 DailyLogService dailyLogService) {
        this.userProfileService = userProfileService;
        this.dailyLogService = dailyLogService;
    }

    @GetMapping("/biometrics")
    public String getBiometricsForm(@AuthenticationPrincipal AuthenticationUserDetails principal) {
        DailyLogDto todayLog = dailyLogService.getTodayLog(principal.getId().toString());

        if (todayLog != null) {
            return "redirect:/daily-log/" + todayLog.getId();
        }

        return "redirect:/home";
    }

    @PostMapping("/biometrics/save")
    public String saveBiometrics(@Valid @ModelAttribute ("profileRequest") UserProfileRequestDto profileRequest,
                                 BindingResult bindingResult,
                                 @AuthenticationPrincipal AuthenticationUserDetails principal) {


        if (bindingResult.hasErrors()) {
            DailyLogDto todayLog = dailyLogService.getTodayLog(principal.getId().toString());
            if (todayLog != null) {
                return "redirect:/daily-log/" + todayLog.getId();
            }
            return "redirect:/home";
        }

        userProfileService.saveOrUpdateProfile(principal.getId().toString(), profileRequest);

        DailyLogDto todayLog = dailyLogService.getTodayLog(principal.getId().toString());
        if (todayLog != null) {
            return "redirect:/daily-log/" + todayLog.getId();
        }
        return "redirect:/home";
    }
}

package app.web.dailyLog;

import app.models.dto.dailyLog.DailyLogDto;
import app.models.dto.dailyLog.WaterIntakeRequestDto;
import app.models.dto.user.UserDto;
import app.models.dto.userProfile.UserProfileRequestDto;
import app.models.entity.user.User;
import app.models.entity.userProfile.ActivityLevel;
import app.models.entity.userProfile.FitnessGoal;
import app.models.entity.userProfile.Gender;
import app.repository.user.UserRepository;
import app.service.dailyLog.DailyLogService;
import app.service.userProfile.UserProfileService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Controller
@RequestMapping("/daily-log")
public class DailyLogController {

    private final DailyLogService dailyLogService;
    private final UserProfileService userProfileService;

    public DailyLogController(DailyLogService dailyLogService, UserProfileService userProfileService) {
        this.dailyLogService = dailyLogService;
        this.userProfileService = userProfileService;
    }

    @GetMapping("/create")
    public String createLog() {
        String userId = "da56da89-db1e-4731-80c1-2650ac93da00";
        dailyLogService.createEmptyLog(userId);

        return "redirect:/home";
    }


    @GetMapping("/{id}")
    public ModelAndView openLog(@PathVariable String id) {

        DailyLogDto dailyLog =
                dailyLogService.getLogById(id);

        String userId = "da56da89-db1e-4731-80c1-2650ac93da00";

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("dashboard");
        modelAndView.addObject("dailyLog", dailyLog);
        modelAndView.addObject("waterRequest", WaterIntakeRequestDto.builder().build());
        modelAndView.addObject("profileRequest", UserProfileRequestDto.builder().build());
        modelAndView.addObject("profile", userProfileService.getByUserId(userId));
        modelAndView.addObject("goals", FitnessGoal.values());
        modelAndView.addObject("activityLevels", ActivityLevel.values());
        modelAndView.addObject("genders", Gender.values());
        return modelAndView;
    }

    @DeleteMapping("/delete/{id}")
    public String deleteLog(@PathVariable String id) {
        dailyLogService.deleteLog(id);
        return "redirect:/home";
    }


    @PostMapping("/{id}/water")
    public String addWater(@PathVariable String id,
                           @Valid @ModelAttribute WaterIntakeRequestDto request,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/daily-log/" + id;
        }
        dailyLogService.addWater(id, request);
        return "redirect:/daily-log/" + id;
    }
}

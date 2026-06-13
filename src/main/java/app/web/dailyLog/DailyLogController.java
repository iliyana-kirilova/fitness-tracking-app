package app.web.dailyLog;

import app.models.dto.dailyLog.DailyLogDto;
import app.models.dto.dailyLog.WaterIntakeRequestDto;
import app.models.dto.user.UserDto;
import app.models.dto.userProfile.UserProfileRequestDto;
import app.models.entity.userProfile.ActivityLevel;
import app.models.entity.userProfile.FitnessGoal;
import app.models.entity.userProfile.Gender;
import app.service.dailyLog.DailyLogService;
import app.service.user.UserService;
import app.service.userProfile.UserProfileService;
import jakarta.servlet.http.HttpSession;
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
    private final UserService  userService;

    public DailyLogController(DailyLogService dailyLogService, UserProfileService userProfileService, UserService userService) {
        this.dailyLogService = dailyLogService;
        this.userProfileService = userProfileService;
        this.userService = userService;
    }

    @GetMapping("/create")
    public String createLog(HttpSession httpSession) {
        UUID userId = (UUID)  httpSession.getAttribute("userId");
        dailyLogService.createEmptyLog(userId.toString());

        return "redirect:/home";
    }


    @GetMapping("/{id}")
    public ModelAndView openLog(@PathVariable String id, HttpSession httpSession) {
        UUID userId = (UUID)  httpSession.getAttribute("userId");
        UserDto user = userService.getById(userId);
        DailyLogDto dailyLog =
                dailyLogService.getLogById(id);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("dashboard");
        modelAndView.addObject("user", user);
        modelAndView.addObject("dailyLog", dailyLog);
        modelAndView.addObject("waterRequest", WaterIntakeRequestDto.builder().build());
        modelAndView.addObject("profileRequest", UserProfileRequestDto.builder().build());
        modelAndView.addObject("profile", userProfileService.getByUserId(userId.toString()));
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

package app.web.dailyLog;

import app.models.dto.dailyLog.DailyLogDto;
import app.models.dto.user.UserDto;
import app.models.entity.user.User;
import app.repository.user.UserRepository;
import app.service.dailyLog.DailyLogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Controller
@RequestMapping("/daily-log")
public class DailyLogController {

    private final DailyLogService dailyLogService;

    public DailyLogController(DailyLogService dailyLogService) {
        this.dailyLogService = dailyLogService;
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

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("dashboard");
        modelAndView.addObject("dailyLog", dailyLog);
        return modelAndView;
    }

    @DeleteMapping("/delete/{id}")
    public String deleteLog(@PathVariable String id) {
        dailyLogService.deleteLog(id);
        return "redirect:/home";
    }

}

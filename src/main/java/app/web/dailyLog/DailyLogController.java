package app.web.dailyLog;

import app.models.dto.dailyLog.DailyLogDto;
import app.service.dailyLog.DailyLogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/daily-log")
public class DailyLogController {

    private final DailyLogService dailyLogService;

    public DailyLogController(DailyLogService dailyLogService) {
        this.dailyLogService = dailyLogService;
    }

    @GetMapping("/create")
    public String createLog() {

        dailyLogService.createEmptyLog();

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

}

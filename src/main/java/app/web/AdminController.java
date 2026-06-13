package app.web;

import app.service.dailyLog.DailyLogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final DailyLogService dailyLogService;

    public AdminController(DailyLogService dailyLogService) {
        this.dailyLogService = dailyLogService;
    }

    @GetMapping("/logs")
    public ModelAndView getAllLogs() {
        ModelAndView modelAndView = new ModelAndView("admin-logs");
        modelAndView.addObject("logs", dailyLogService.getAllLogs());
        return modelAndView;
    }
}

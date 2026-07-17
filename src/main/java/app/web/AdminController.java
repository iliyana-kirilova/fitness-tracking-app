package app.web;

import app.service.dailyLog.DailyLogService;
import app.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final DailyLogService dailyLogService;
    private final UserService userService;

    public AdminController(DailyLogService dailyLogService, UserService userService) {
        this.dailyLogService = dailyLogService;
        this.userService = userService;
    }

    @GetMapping("/logs")
    public ModelAndView getAllLogs() {
        ModelAndView modelAndView = new ModelAndView("admin-logs");
        modelAndView.addObject("logs", dailyLogService.getAllLogs());
        return modelAndView;
    }

    @GetMapping("/users")
    public ModelAndView getAllUsers() {
        ModelAndView mav = new ModelAndView("admin-users");
        mav.addObject("users", userService.getAllUsers());
        return mav;
    }

    @PutMapping("/users/{id}/status")
    public String switchStatus(@PathVariable UUID id) {
        userService.switchStatus(id);
        return "redirect:/admin/users";
    }

    @PutMapping("/users/{id}/role")
    public String switchRole(@PathVariable UUID id) {
        userService.switchRole(id);
        return "redirect:/admin/users";
    }
}

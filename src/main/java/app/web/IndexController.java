package app.web;

import app.models.dto.user.UserDto;
import app.models.dto.user.UserLoginRequest;
import app.models.dto.user.UserRegisterRequest;
import app.models.entity.user.Country;
import app.service.dailyLog.DailyLogService;
import app.service.user.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Controller
public class IndexController {

    private final UserService userService;
    private final DailyLogService dailyLogService;

    public IndexController(UserService userService, DailyLogService dailyLogService) {
        this.userService = userService;
        this.dailyLogService = dailyLogService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public ModelAndView getLoginPage() {
        UserLoginRequest userLoginRequest = UserLoginRequest.builder().build();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        modelAndView.addObject("userLoginRequest", userLoginRequest);
        return modelAndView;
    }

    @PostMapping("/login")
    public ModelAndView login(@Valid @ModelAttribute UserLoginRequest userLoginRequest,
                              BindingResult bindingResult,
                              HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("login");
            return modelAndView;
        }

        UserDto user = userService.login(userLoginRequest);
        httpSession.setAttribute("userId", user.getId());
        return new ModelAndView("redirect:/home");
    }

    @GetMapping("/register")
    public ModelAndView getRegisterPage() {
        UserRegisterRequest userRegisterRequest = UserRegisterRequest.builder().build();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("register");
        modelAndView.addObject("userRegisterRequest", userRegisterRequest);
        modelAndView.addObject("countries", Country.values());
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView register(@Valid @ModelAttribute UserRegisterRequest userRegisterRequest,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("register");
            return modelAndView;
        }

        userService.register(userRegisterRequest);
        return new ModelAndView("redirect:/login");
    }

    @GetMapping("/home")
    public ModelAndView getHomePage(HttpSession httpSession) {
        UUID userUUID = (UUID) httpSession.getAttribute("userId");
        UserDto user = userService.getById(userUUID);


        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("user", user);
        modelAndView.addObject("logs", dailyLogService.getAllLogs(userUUID.toString()));
        modelAndView.addObject("todayLog", dailyLogService.getTodayLog(userUUID.toString()));

        return modelAndView;
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.invalidate();
        return "redirect:/";
    }

}

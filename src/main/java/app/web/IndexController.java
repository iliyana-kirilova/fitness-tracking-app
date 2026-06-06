package app.web;

import app.models.dto.user.UserLoginRequest;
import app.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    private final UserService userService;

    public IndexController(UserService userService) {
        this.userService = userService;
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
    public ModelAndView login(@ModelAttribute UserLoginRequest userLoginRequest) {
        userService.login(userLoginRequest);
        return new ModelAndView("redirect:/dashboard");
    }

    @GetMapping("/register")
    public String getRegisterPage() {
        return "register";
    }

    @GetMapping("/dashboard")
    public ModelAndView getHomePage() {
        return new ModelAndView("dashboard");
    }

}

package app.web;

import app.models.dto.user.UserDto;
import app.models.dto.user.UserLoginRequest;
import app.models.dto.user.UserRegisterRequest;
import app.models.entity.user.Country;
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
        UserDto user = userService.login(userLoginRequest);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");
        modelAndView.addObject("user", user);
        return modelAndView;
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
    public ModelAndView register(@ModelAttribute UserRegisterRequest userRegisterRequest) {
        userService.register(userRegisterRequest);
        return new ModelAndView("redirect:/login");
    }

    @GetMapping("/home")
    public ModelAndView getHomePage() {
        return new ModelAndView("home");
    }

}

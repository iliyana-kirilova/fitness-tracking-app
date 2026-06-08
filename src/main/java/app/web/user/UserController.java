package app.web.user;

import app.models.dto.user.UserDto;
import app.models.dto.user.UserEditDto;
import app.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}/profile")
    public ModelAndView profile(@PathVariable  String id) {
        UserDto user = userService.getById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("profile-edit");
        modelAndView.addObject("user", user);

        return modelAndView;
    }

    @PutMapping("/{id}/profile/edit")
    public ModelAndView profile(@PathVariable String id, @ModelAttribute UserEditDto userEditDto) {
        UserDto updatedUser = userService.update(id, userEditDto);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");
        modelAndView.addObject("user", updatedUser);

        return modelAndView;

    }
}

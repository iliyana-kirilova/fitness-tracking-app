package app.web.meal;

import app.models.dto.meal.MealDto;
import app.models.dto.meal.MealRequestDto;
import app.models.dto.user.UserDto;
import app.models.entity.meal.MealType;
import app.service.meal.MealService;
import app.service.user.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Controller
@RequestMapping("/meals")
public class MealController {

    private final MealService mealService;
    private final UserService userService;

    public MealController(MealService mealService, UserService userService) {
        this.mealService = mealService;
        this.userService = userService;
    }

    @GetMapping("/add")
    public ModelAndView getAddMealPage(@RequestParam String logId, HttpSession httpSession) {
        UUID userId = (UUID) httpSession.getAttribute("userId");
        UserDto user = userService.getById(userId);


        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("meal");
        modelAndView.addObject("user", user);
        modelAndView.addObject("mealRequest", MealRequestDto.builder().build());
        modelAndView.addObject("mealTypes", MealType.values());
        modelAndView.addObject("logId", logId);
        modelAndView.addObject("editMode", false);
        return modelAndView;
    }

    @PostMapping("/add")
    public ModelAndView addMeal(@RequestParam String logId,
                          @Valid @ModelAttribute("mealRequest") MealRequestDto mealRequest,
                          BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("meal");
            modelAndView.addObject("mealRequest", mealRequest); // запазва въведените данни
            modelAndView.addObject("mealTypes", MealType.values());
            modelAndView.addObject("logId", logId);
            modelAndView.addObject("editMode", false);
            return modelAndView;
        }
        mealService.addMealToLog(logId, mealRequest);
        return  new ModelAndView("redirect:/daily-log/" + logId);
    }

    @GetMapping("/edit/{id}")
    public ModelAndView getEditMealForm(@PathVariable String id) {
        MealDto meal = mealService.getById(id);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("meal");
        modelAndView.addObject("mealRequest", meal);
        modelAndView.addObject("mealTypes", MealType.values());
        modelAndView.addObject("editMode", true);
        modelAndView.addObject("mealId", id);
        modelAndView.addObject("logId", meal.getDailyLogId());
        return modelAndView;
    }

    @PutMapping ("/edit/{id}")
    public ModelAndView editMeal(@PathVariable String id,
                           @Valid @ModelAttribute ("mealRequest")  MealRequestDto mealRequest,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            MealDto meal = mealService.getById(id);
            ModelAndView modelAndView = new ModelAndView("meal");
            modelAndView.addObject("mealRequest", mealRequest);
            modelAndView.addObject("mealTypes", MealType.values());
            modelAndView.addObject("editMode", true);
            modelAndView.addObject("mealId", id);
            modelAndView.addObject("logId", meal.getDailyLogId());
            return modelAndView;
        }
        String logId = mealService.updateMeal(id, mealRequest);
        return new ModelAndView("redirect:/daily-log/" + logId);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteMeal(@PathVariable String id) {
        String logId = mealService.deleteMeal(id);
        return "redirect:/daily-log/" + logId;
    }
}

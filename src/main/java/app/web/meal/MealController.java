package app.web.meal;

import app.models.dto.meal.MealDto;
import app.models.dto.meal.MealRequestDto;
import app.models.entity.meal.MealType;
import app.service.meal.MealService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/meals")
public class MealController {

    private final MealService mealService;

    public MealController(MealService mealService) {
        this.mealService = mealService;
    }

    @GetMapping("/add")
    public ModelAndView getAddMealPage(@RequestParam String logId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("meal");
        modelAndView.addObject("mealRequest", MealRequestDto.builder().build());
        modelAndView.addObject("mealTypes", MealType.values());
        modelAndView.addObject("logId", logId);
        modelAndView.addObject("editMode", false);
        return modelAndView;
    }

    @PostMapping("/add")
    public String addMeal(@RequestParam String logId,
                          @Valid @ModelAttribute MealRequestDto mealRequest,
                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/meals/add?logId=" + logId;
        }
        mealService.addMealToLog(logId, mealRequest);
        return "redirect:/daily-log/" + logId;
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
    public String editMeal(@PathVariable String id,
                           @Valid @ModelAttribute MealRequestDto mealRequest,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/meals/edit/" + id;
        }
        String logId = mealService.updateMeal(id, mealRequest);
        return "redirect:/daily-log/" + logId;
    }

    @DeleteMapping("/delete/{id}")
    public String deleteMeal(@PathVariable String id) {
        String logId = mealService.deleteMeal(id);
        return "redirect:/daily-log/" + logId;
    }
}

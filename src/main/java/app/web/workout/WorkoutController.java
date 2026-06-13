package app.web.workout;

import app.models.dto.user.UserDto;
import app.models.dto.workout.WorkoutDto;
import app.models.dto.workout.WorkoutRequestDto;
import app.models.entity.workout.WorkoutType;
import app.service.user.UserService;
import app.service.workout.WorkoutService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Controller
@RequestMapping("/workouts")
public class WorkoutController {

    private final WorkoutService workoutService;
    private final UserService userService;

    public WorkoutController(WorkoutService workoutService, UserService userService) {
        this.workoutService = workoutService;
        this.userService = userService;
    }

    @GetMapping("/add")
    public ModelAndView getAddWorkoutForm(@RequestParam String logId, HttpSession httpSession) {
        UUID userId = (UUID) httpSession.getAttribute("userId");
        UserDto user = userService.getById(userId);

        ModelAndView modelAndView= new ModelAndView();
        modelAndView.setViewName("workout");
        modelAndView.addObject("user", user);
        modelAndView.addObject("workoutRequest", WorkoutRequestDto.builder().build());
        modelAndView.addObject("workoutTypes", WorkoutType.values());
        modelAndView.addObject("logId", logId);
        modelAndView.addObject("editMode", false);
        return modelAndView;
    }

    @PostMapping("/add")
    public ModelAndView addWorkout(@RequestParam String logId,
                             @Valid @ModelAttribute ("workoutRequest") WorkoutRequestDto workoutRequest,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("workout");
            modelAndView.addObject("workoutRequest", workoutRequest);
            modelAndView.addObject("workoutTypes", WorkoutType.values());
            modelAndView.addObject("logId", logId);
            modelAndView.addObject("editMode", false);
            return modelAndView;
        }
        workoutService.addWorkoutToLog(logId, workoutRequest);
        return new ModelAndView("redirect:/daily-log/" + logId);
    }

    @GetMapping("/edit/{id}")
    public ModelAndView getEditWorkoutForm(@PathVariable String id) {

        WorkoutDto workout = workoutService.getById(id);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("workout");
        modelAndView.addObject("workoutRequest", workout);
        modelAndView.addObject("workoutTypes", WorkoutType.values());
        modelAndView.addObject("editMode", true);
        modelAndView.addObject("workoutId", id);
        modelAndView.addObject("logId", workout.getDailyLogId());
        return modelAndView;
    }

    @PostMapping("/edit/{id}")
    public ModelAndView editWorkout(@PathVariable String id,
                              @Valid @ModelAttribute ("workoutRequest") WorkoutRequestDto workoutRequest,
                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            WorkoutDto workout = workoutService.getById(id);
            ModelAndView modelAndView = new ModelAndView("workout");
            modelAndView.addObject("workoutRequest", workoutRequest);
            modelAndView.addObject("workoutTypes", WorkoutType.values());
            modelAndView.addObject("editMode", true);
            modelAndView.addObject("workoutId", id);
            modelAndView.addObject("logId", workout.getDailyLogId());
            return modelAndView;
        }
        String logId = workoutService.updateWorkout(id, workoutRequest);
        return new ModelAndView("redirect:/daily-log/" + logId);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteWorkout(@PathVariable String id) {
        String logId = workoutService.deleteWorkout(id);
        return "redirect:/daily-log/" + logId;
    }
}

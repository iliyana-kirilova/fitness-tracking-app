package app.web.workout;

import app.models.dto.workout.WorkoutDto;
import app.models.dto.workout.WorkoutRequestDto;
import app.models.entity.workout.WorkoutType;
import app.service.workout.WorkoutService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/workouts")
public class WorkoutController {

    private final WorkoutService workoutService;

    public WorkoutController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    @GetMapping("/add")
    public ModelAndView getAddWorkoutForm(@RequestParam String logId) {
        ModelAndView modelAndView= new ModelAndView();
        modelAndView.setViewName("workout");
        modelAndView.addObject("workoutRequest", WorkoutRequestDto.builder().build());
        modelAndView.addObject("workoutTypes", WorkoutType.values());
        modelAndView.addObject("logId", logId);
        modelAndView.addObject("editMode", false);
        return modelAndView;
    }

    @PostMapping("/add")
    public String addWorkout(@RequestParam String logId,
                             @Valid @ModelAttribute WorkoutRequestDto workoutRequest,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/workouts/add?logId=" + logId;
        }
        workoutService.addWorkoutToLog(logId, workoutRequest);
        return "redirect:/daily-log/" + logId;
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
    public String editWorkout(@PathVariable String id,
                              @Valid @ModelAttribute WorkoutRequestDto workoutRequest,
                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/workouts/edit/" + id;
        }
        String logId = workoutService.updateWorkout(id, workoutRequest);
        return "redirect:/daily-log/" + logId;
    }

    @DeleteMapping("/delete/{id}")
    public String deleteWorkout(@PathVariable String id) {
        String logId = workoutService.deleteWorkout(id);
        return "redirect:/daily-log/" + logId;
    }
}

package app.service.meal;

import app.mapper.meal.MealMapper;
import app.models.dto.meal.MealDto;
import app.models.dto.meal.MealRequestDto;
import app.models.entity.dailyLog.DailyLog;
import app.models.entity.meal.Meal;
import app.repository.dailyLog.DailyLogRepository;
import app.repository.meal.MealRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MealService {
    private final MealRepository  mealRepository;
    private final DailyLogRepository dailyLogRepository;

    public MealService(MealRepository mealRepository, DailyLogRepository dailyLogRepository) {
        this.mealRepository = mealRepository;
        this.dailyLogRepository = dailyLogRepository;
    }

    public void addMealToLog(String logId, MealRequestDto mealRequestDto) {
        DailyLog log = findLogById(logId);

        Meal meal = Meal.builder()
                .name(mealRequestDto.getName())
                .type(mealRequestDto.getType())
                .calories(mealRequestDto.getCalories())
                .protein(mealRequestDto.getProtein())
                .carbs(mealRequestDto.getCarbs())
                .fats(mealRequestDto.getFats())
                .dailyLog(log)
                .build();

        mealRepository.save(meal);

        recalculateLogTotals(log);
    }

    public MealDto getById(String id) {
        return mealRepository.findById(UUID.fromString(id))
                .map(MealMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Meal not found with id: " + id));

    }

    public String updateMeal(String mealId, MealRequestDto mealRequestDto) {
        Meal meal = mealRepository.findById(UUID.fromString(mealId))
                .orElseThrow(() -> new RuntimeException("Meal not found with id: " + mealId));

        meal.setName(mealRequestDto.getName());
        meal.setType(mealRequestDto.getType());
        meal.setCalories(mealRequestDto.getCalories());
        meal.setProtein(mealRequestDto.getProtein());
        meal.setCarbs(mealRequestDto.getCarbs());
        meal.setFats(mealRequestDto.getFats());

        mealRepository.save(meal);
        recalculateLogTotals(meal.getDailyLog()); //recalculate after update
        return meal.getDailyLog().getId().toString();
    }

    public String deleteMeal(String mealId) {
        Meal meal = mealRepository.findById(UUID.fromString(mealId))
                .orElseThrow(() -> new RuntimeException("Meal not found: " + mealId));

        String logId = meal.getDailyLog().getId().toString();
        DailyLog log = meal.getDailyLog();

        mealRepository.delete(meal);
        recalculateLogTotals(log); //recalculate after deletion
        return logId;
    }

    //this method recalculates the total calories, protein, carbs, and fats for a given daily log by summing up the values
    // from all meals associated with that log. After calculating the totals, it updates the daily log and saves it back to the repository.
    private void recalculateLogTotals(DailyLog log) {
        List <Meal> meals = mealRepository.findAllByDailyLogId(log.getId());

        int totalCalories = meals.stream().mapToInt(Meal::getCalories).sum();
        double totalProtein = meals.stream().mapToDouble(Meal::getProtein).sum();
        double totalCarbs   = meals.stream().mapToDouble(Meal::getCarbs).sum();
        double totalFats    = meals.stream().mapToDouble(Meal::getFats).sum();

        log.setCaloriesConsumed(totalCalories);
        log.setProteinConsumed(totalProtein);
        log.setCarbsConsumed(totalCarbs);
        log.setFatsConsumed(totalFats);

        dailyLogRepository.save(log);

    }

    private DailyLog findLogById(String logId) {
        return dailyLogRepository.findById(UUID.fromString(logId))
                .orElseThrow(() -> new RuntimeException("Daily log not found with id: " + logId));
    }
}

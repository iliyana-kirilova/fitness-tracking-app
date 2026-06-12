package app.service;

import app.models.dto.userProfile.UserProfileDto;
import app.models.entity.userProfile.ActivityLevel;
import app.models.entity.userProfile.FitnessGoal;
import app.models.entity.userProfile.Gender;
import org.springframework.stereotype.Service;

import static app.models.entity.userProfile.ActivityLevel.*;

@Service
public class CaloriesCalculatorService {

    public int calculateTargetCalories(UserProfileDto userProfileDto) {
        double bmr;

        //calculate base bmr;
        if(userProfileDto.getGender() == Gender.MALE){
            bmr= 10 * userProfileDto.getWeight()
                    + 6.25 * userProfileDto.getHeight()
                    - 5 * userProfileDto.getAge()
                    +5;

        }else {
            bmr = 10 * userProfileDto.getWeight()
                    + 6.25 * userProfileDto.getHeight()
                    - 5 * userProfileDto.getAge()
                    - 161;
        }

        //multiply by activity level
        double tdee = bmr * getActivityMultiplier(userProfileDto.getActivityLevel());

        return (int) Math.round(applyGoalAdjustment(tdee, userProfileDto.getGoal()));
    }

    private double applyGoalAdjustment(double tdee, FitnessGoal goal) {
        return switch (goal) {
            case MAINTAIN     -> tdee;
            case LOSE_WEIGHT  -> tdee - 500;  //deficiency 500 kcal
            case GAIN_WEIGHT  -> tdee + 300;  //surplus 300 kcal
        };
    }

    private double getActivityMultiplier(ActivityLevel activityLevel) {
        return switch (activityLevel){
            case SEDENTARY         -> 1.2;
            case LIGHTLY_ACTIVE    -> 1.375;
            case MODERATELY_ACTIVE -> 1.55;
            case VERY_ACTIVE       -> 1.725;
        };
    }

    public int calculateTargetWater(UserProfileDto userProfileDto) {
        return (int) Math.round(userProfileDto.getWeight() * 35);
    }

    public int calculateTargetProtein(int targetCalories) {
        return (int) Math.round((targetCalories * 0.30) / 4);
    }

    public int calculateTargetCarbs(int targetCalories) {

        return (int) Math.round((targetCalories * 0.40) / 4);
    }

    public int calculateTargetFats(int targetCalories) {
        return (int) Math.round((targetCalories * 0.30) / 9);
    }
}

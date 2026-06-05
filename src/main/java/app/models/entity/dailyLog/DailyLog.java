package app.models.entity.dailyLog;

import app.models.entity.meal.Meal;
import app.models.entity.user.User;
import app.models.entity.workout.Workout;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "daily_log")
public class DailyLog {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private LocalDate logDate;

    @Column(nullable = false)
    private Integer waterIntake;
    @Column(nullable = false)
    private Integer caloriesConsumed;
    @Column(nullable = false)
    private Double proteinConsumed;
    @Column(nullable = false)
    private Double carbsConsumed;
    @Column(nullable = false)
    private Double fatsConsumed;

    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dailyLog")
    private List<Meal> mealsList  = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dailyLog")
    private List<Workout> workoutList = new ArrayList<>();

}

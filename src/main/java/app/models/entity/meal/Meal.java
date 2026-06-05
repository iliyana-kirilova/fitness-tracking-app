package app.models.entity.meal;

import app.models.entity.dailyLog.DailyLog;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "meal")
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private MealType type;

    @Column(nullable = false)
    private Integer calories;
    @Column(nullable = false)
    private Double protein;
    @Column(nullable = false)
    private Double carbs;
    @Column(nullable = false)
    private Double fats;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "daily_log_id")
    private DailyLog dailyLog;


}

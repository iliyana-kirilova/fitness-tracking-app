package app.models.entity.workout;

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
@Table(name = "workout")
public class Workout {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private WorkoutType type;

    @Column(nullable = false)
    private Integer duration;

    @Column(nullable = false)
    private Integer caloriesBurned;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "daily_log_id")
    private DailyLog dailyLog;




}

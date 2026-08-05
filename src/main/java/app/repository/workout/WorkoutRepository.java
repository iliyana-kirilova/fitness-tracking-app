package app.repository.workout;

import app.models.entity.workout.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, UUID> {
    List<Workout> findAllByDailyLogId(UUID dailyLogId);

    int countByDailyLog_User_IdAndDailyLog_LogDateGreaterThanEqual(UUID userId, LocalDate startOfWeek);
}

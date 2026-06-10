package app.repository.dailyLog;

import app.models.entity.dailyLog.DailyLog;
import app.models.entity.user.User;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DailyLogRepository extends JpaRepository<DailyLog, UUID> {
    Optional<DailyLog> findByUser_IdAndLogDate(UUID userId, LocalDate logDate);

    List<DailyLog> findAllByOrderByLogDateDesc();


}

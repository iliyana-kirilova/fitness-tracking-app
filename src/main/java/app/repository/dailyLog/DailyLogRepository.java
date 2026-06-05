package app.repository.dailyLog;

import app.models.entity.dailyLog.DailyLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DailyLogRepository extends JpaRepository<DailyLog, UUID> {
}

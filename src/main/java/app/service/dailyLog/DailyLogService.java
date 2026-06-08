package app.service.dailyLog;

import app.mapper.dailyLog.DailyLogMapper;
import app.models.dto.dailyLog.DailyLogDto;
import app.models.entity.dailyLog.DailyLog;
import app.repository.dailyLog.DailyLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class DailyLogService {

    private final DailyLogRepository dailyLogRepository;

    public DailyLogService(DailyLogRepository dailyLogRepository) {
        this.dailyLogRepository = dailyLogRepository;
    }

    public DailyLogDto createEmptyLog() {

        DailyLog emptyLog = DailyLog.builder()
                .logDate(LocalDate.now())
                .waterIntake(0)
                .caloriesConsumed(0)
                .proteinConsumed(0.0)
                .carbsConsumed(0.0)
                .fatsConsumed(0.0)
                .build();

        DailyLog savedLog = dailyLogRepository.save(emptyLog);

        return DailyLogMapper.toDailyLogDto(savedLog);
    }

    public List<DailyLogDto> getAllLogs() {

        return dailyLogRepository.findAll()
                .stream()
                .map(DailyLogMapper::toDailyLogDto)
                .toList();
    }


    public DailyLogDto getLogById(String id) {
        DailyLog dailyLog = dailyLogRepository
                .findById(UUID.fromString(id))
                .orElseThrow(() ->
                        new RuntimeException(
                                "Daily log not found with id: " + id));

        return DailyLogMapper.toDailyLogDto(dailyLog);
    }


    public DailyLogDto saveLog(DailyLogDto dailyLogDto) {
        DailyLog entity = DailyLogMapper.toDailyLogEntity(dailyLogDto);

        DailyLog savedEntity = dailyLogRepository.save(entity);
        return DailyLogMapper.toDailyLogDto(savedEntity);
    }


    public void deleteLog(String id) {
        dailyLogRepository.deleteById(
                UUID.fromString(id)
        );
    }
}

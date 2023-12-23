package app.repositories;

import org.springframework.stereotype.Repository;
import app.models.ActivityLog;

@Repository
public interface ActivityLogRepository extends ModelRepository<ActivityLog, Integer> { }
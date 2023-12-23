package app.services.implementation;

import app.single_point_access.RepositorySinglePointAccess;
import org.springframework.stereotype.Repository;
import app.repositories.ActivityLogRepository;
import app.services.ActivityLogService;
import app.models.ActivityLog;
import java.util.List;

@Repository
public class ActivityLogServiceImpl implements ActivityLogService {
    private ActivityLogRepository activityRepository = RepositorySinglePointAccess.getActivityRepository();

    @Override public ActivityLog save(ActivityLog entity)      { return activityRepository.save(entity);   }
    @Override public List<ActivityLog> findAll()               { return activityRepository.findAll();      }
    @Override public ActivityLog findByKey(String key)         { return activityRepository.findByKey(key); }
    @Override public ActivityLog findById(Integer id)          { return activityRepository.findById(id);   }
    @Override public ActivityLog update(ActivityLog entity)    { return activityRepository.update(entity); }
    @Override public boolean delete(ActivityLog entity)        { return activityRepository.delete(entity); } 
}

package app.repositories.implementation;

import org.springframework.stereotype.Repository;
import app.configuration.HibernateConfiguration;
import app.repositories.ActivityLogRepository;
import javax.persistence.NoResultException;
import org.hibernate.SessionFactory;
import javax.persistence.TypedQuery;
import org.hibernate.Transaction;
import app.models.ActivityLog;
import org.hibernate.Session;
import java.util.List;

@Repository
public class ActivityLogRepositoryImpl implements ActivityLogRepository {
    @Override public ActivityLog save(ActivityLog activity) {
        SessionFactory sessionFactory  = HibernateConfiguration.getSessionFactory();
        Session currentSession = sessionFactory.openSession();
        Transaction transaction = currentSession.beginTransaction();

        Integer idOfActivity = (Integer)currentSession.save(activity);
        transaction.commit();
        currentSession.close();

        return findById(idOfActivity);
    }

    @Override public ActivityLog update(ActivityLog activity) {
        SessionFactory sessionFactory  = HibernateConfiguration.getSessionFactory();
        Session currentSession = sessionFactory.openSession();
        Transaction transaction = currentSession.beginTransaction();

        Integer idOfActivity = activity.getId();
        currentSession.saveOrUpdate(activity);
        transaction.commit();
        return findById(idOfActivity);
    }

    @SuppressWarnings("unchecked")
    @Override public ActivityLog findById(Integer id) {
        SessionFactory sessionFactory  = HibernateConfiguration.getSessionFactory();
        Session currentSession = sessionFactory.openSession();
        Transaction transaction = currentSession.beginTransaction();

        TypedQuery<ActivityLog> query = currentSession.getNamedQuery("findActivityById");
        query.setParameter("id", id);

        ActivityLog activityLog;
        try {
            activityLog = query.getSingleResult();
        } catch(NoResultException exception) {
            activityLog = null;
        }

        transaction.commit();
        currentSession.close();

        return activityLog;
    }

    @SuppressWarnings("unchecked")
    @Override public ActivityLog findByKey(String key) {
        SessionFactory sessionFactory  = HibernateConfiguration.getSessionFactory();
        Session currentSession = sessionFactory.openSession();
        Transaction transaction = currentSession.beginTransaction();

        TypedQuery<ActivityLog> query = currentSession.getNamedQuery("findActivityByKey");
        query.setParameter("key", key);

        ActivityLog activityLog;
        try {
            activityLog = query.getSingleResult();
        } catch(NoResultException exception) {
            activityLog = null;
        }

        transaction.commit();
        currentSession.close();

        return activityLog;
    }

    @SuppressWarnings("unchecked")
    @Override public List<ActivityLog> findAll() {
        SessionFactory sessionFactory  = HibernateConfiguration.getSessionFactory();
        Session currentSession = sessionFactory.openSession();
        Transaction transaction = currentSession.beginTransaction();

        TypedQuery<ActivityLog> query = currentSession.getNamedQuery("findAllActivities");
        List<ActivityLog> activities =  query.getResultList();
        
        transaction.commit();
        currentSession.close();

        return activities;
    }

    @Override public boolean delete(ActivityLog activity) {
        Session currentSession  = HibernateConfiguration.getSessionFactory().openSession();
        Transaction transaction = currentSession.beginTransaction();

        Integer id = activity.getId();
        currentSession.delete(activity);

        transaction.commit();
        currentSession.clear();

        return findById(id) == null;
    }
}

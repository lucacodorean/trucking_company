package app.repositories.implementation;

import java.util.List;
import app.models.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import app.repositories.UserRepository;
import javax.persistence.NoResultException;
import app.configuration.HibernateConfiguration;

@Repository
public class UserRepositoryImpl implements UserRepository {
    
    @Override public User save(User user) {
        Session currentSession  = HibernateConfiguration.getSessionFactory().openSession();
        Transaction transaction = currentSession.beginTransaction();
        Integer idOnSavedUser = (Integer)currentSession.save(user);
        transaction.commit();
        currentSession.close();
        return findById(idOnSavedUser);
    }

    @Override public User update(User user) {
        Session currentSession  = HibernateConfiguration.getSessionFactory().openSession();
        Transaction transaction = currentSession.beginTransaction();

        Integer idOfUpdatedUser = user.getId();
        transaction.commit();
        currentSession.saveOrUpdate(user);
        return findById(idOfUpdatedUser);
    }

    @SuppressWarnings("unchecked")
    @Override public User findById(Integer id) {
        Session currentSession  = HibernateConfiguration.getSessionFactory().openSession();
        Transaction transaction = currentSession.beginTransaction();

        TypedQuery<User> query = currentSession.getNamedQuery("findUserById");
        query.setParameter("id", id);

        User user;
        try {
            user = (User)query.getSingleResult();
        } catch(NoResultException exception) {
            user = null;
        }

        transaction.commit();
        currentSession.close();

        return user;
    }

    @SuppressWarnings("unchecked")
    @Override public User findByKey(String key) {
        Session currentSession  = HibernateConfiguration.getSessionFactory().openSession();
        Transaction transaction = currentSession.beginTransaction();

        TypedQuery<User> query = currentSession.getNamedQuery("findUserByKey");
        query.setParameter("key", key);

        User user;
        try {
            user = query.getSingleResult();
        } catch(NoResultException exception) {
            user = null;
        }

        transaction.commit();
        currentSession.close();

        return user;
    }

    @SuppressWarnings("unchecked")
    @Override public List<User> findAll() {
        Session currentSession  = HibernateConfiguration.getSessionFactory().openSession();
        Transaction transaction = currentSession.beginTransaction();

        TypedQuery<User> query = currentSession.getNamedQuery("findAllUsers");
        List<User> users = query.getResultList();
        
        transaction.commit();
        currentSession.close();

        return users;
    }

    @Override public boolean delete(User user) {
        Session currentSession  = HibernateConfiguration.getSessionFactory().openSession();
        Transaction transaction = currentSession.beginTransaction();

        Integer id = user.getId();
        currentSession.delete(user);

        transaction.commit();
        currentSession.clear();

        return findById(id) == null;
    }
}
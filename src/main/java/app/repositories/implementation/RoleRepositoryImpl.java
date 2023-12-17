package app.repositories.implementation;

import java.util.List;

import app.models.Role;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import org.hibernate.SessionFactory;
import app.repositories.RoleRepository;
import javax.persistence.NoResultException;
import app.configuration.HibernateConfiguration;

@Repository
public class RoleRepositoryImpl implements RoleRepository{
    @Override public Role save(Role role) {
        SessionFactory sessionFactory  = HibernateConfiguration.getSessionFactory();
        Session currentSession = sessionFactory.openSession();
        Transaction transaction = currentSession.beginTransaction();

        Integer idOfRole = (Integer)currentSession.save(role);
        transaction.commit();
        currentSession.close();

        return findById(idOfRole);
    }

    @Override public Role update(Role role) {
        SessionFactory sessionFactory  = HibernateConfiguration.getSessionFactory();
        Session currentSession = sessionFactory.openSession();
        Transaction transaction = currentSession.beginTransaction();

        Integer idOfRole = role.getId();
        currentSession.saveOrUpdate(role);
        transaction.commit();
        return findById(idOfRole);
    }

    @SuppressWarnings("unchecked")
    @Override public Role findById(Integer id) {
        SessionFactory sessionFactory  = HibernateConfiguration.getSessionFactory();
        Session currentSession = sessionFactory.openSession();
        Transaction transaction = currentSession.beginTransaction();

        TypedQuery<Role> query = currentSession.getNamedQuery("findRoleById");
        query.setParameter("id", id);

        Role role;
        try {
            role = query.getSingleResult();
        } catch(NoResultException exception) {
            role = null;
        }

        transaction.commit();
        currentSession.close();

        return role;
    }

    @SuppressWarnings("unchecked")
    @Override public Role findByKey(String key) {
        SessionFactory sessionFactory  = HibernateConfiguration.getSessionFactory();
        Session currentSession = sessionFactory.openSession();
        Transaction transaction = currentSession.beginTransaction();

        TypedQuery<Role> query = currentSession.getNamedQuery("findRoleByKey");
        query.setParameter("key", key);

        Role role;
        try {
            role = query.getSingleResult();
        } catch(NoResultException exception) {
            role = null;
        }

        transaction.commit();
        currentSession.close();

        return role;
    }

    @SuppressWarnings("unchecked")
    @Override public List<Role> findAll() {
        SessionFactory sessionFactory  = HibernateConfiguration.getSessionFactory();
        Session currentSession = sessionFactory.openSession();
        Transaction transaction = currentSession.beginTransaction();

        TypedQuery<Role> query = currentSession.getNamedQuery("findAllUsers");
        List<Role> roles =  query.getResultList();
        
        transaction.commit();
        currentSession.close();

        return roles;
    }

    @Override public boolean delete(Role role) {
        Session currentSession  = HibernateConfiguration.getSessionFactory().openSession();
        Transaction transaction = currentSession.beginTransaction();

        Integer id = role.getId();
        currentSession.delete(role);

        transaction.commit();
        currentSession.clear();

        return findById(id) == null;
    }
}

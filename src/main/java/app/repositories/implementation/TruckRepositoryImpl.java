package app.repositories.implementation;

import java.util.List;
import app.models.Truck;
import org.hibernate.Session;
import org.hibernate.Transaction;
import javax.persistence.TypedQuery;
import org.hibernate.SessionFactory;
import app.repositories.TruckRepository;
import javax.persistence.NoResultException;
import app.configuration.HibernateConfiguration;
import org.springframework.stereotype.Repository;

@Repository
public class TruckRepositoryImpl implements TruckRepository {
 @Override public Truck save(Truck truck) {
        SessionFactory sessionFactory  = HibernateConfiguration.getSessionFactory();
        Session currentSession = sessionFactory.openSession();
        Transaction transaction = currentSession.beginTransaction();

        Integer idOfTruck = (Integer)currentSession.save(truck);
        transaction.commit();
        currentSession.close();

        return findById(idOfTruck);
    }

    @Override public Truck update(Truck truck) {
        SessionFactory sessionFactory  = HibernateConfiguration.getSessionFactory();
        Session currentSession = sessionFactory.openSession();
        Transaction transaction = currentSession.beginTransaction();

        Integer idOfTruck = truck.getId();
        currentSession.saveOrUpdate(truck);
        transaction.commit();
        return findById(idOfTruck);
    }

    @SuppressWarnings("unchecked")
    @Override public Truck findById(Integer id) {
        SessionFactory sessionFactory  = HibernateConfiguration.getSessionFactory();
        Session currentSession = sessionFactory.openSession();
        Transaction transaction = currentSession.beginTransaction();

        TypedQuery<Truck> query = currentSession.getNamedQuery("findTruckById");
        query.setParameter("id", id);

        Truck truck;
        try {
            truck = query.getSingleResult();
        } catch(NoResultException exception) {
            truck = null;
        }

        transaction.commit();
        currentSession.close();

        return truck;
    }

    @SuppressWarnings("unchecked")
    @Override public Truck findByKey(String key) {
        SessionFactory sessionFactory  = HibernateConfiguration.getSessionFactory();
        Session currentSession = sessionFactory.openSession();
        Transaction transaction = currentSession.beginTransaction();

        TypedQuery<Truck> query = currentSession.getNamedQuery("findTruckByKey");
        query.setParameter("key", key);

        Truck truck;
        try {
            truck = query.getSingleResult();
        } catch(NoResultException exception) {
            truck = null;
        }

        transaction.commit();
        currentSession.close();

        return truck;
    }

    @SuppressWarnings("unchecked")
    @Override public List<Truck> findAll() {
        SessionFactory sessionFactory  = HibernateConfiguration.getSessionFactory();
        Session currentSession = sessionFactory.openSession();
        Transaction transaction = currentSession.beginTransaction();

        TypedQuery<Truck> query = currentSession.getNamedQuery("findAllTrucks");
        List<Truck> trucks =  query.getResultList();
        
        transaction.commit();
        currentSession.close();

        return trucks;
    }

    @Override public boolean delete(Truck truck) {
        Session currentSession  = HibernateConfiguration.getSessionFactory().openSession();
        Transaction transaction = currentSession.beginTransaction();

        Integer id = truck.getId();
        currentSession.delete(truck);

        transaction.commit();
        currentSession.clear();

        return findById(id) == null;
    }
}
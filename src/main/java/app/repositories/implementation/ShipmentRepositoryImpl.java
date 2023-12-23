package app.repositories.implementation;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import app.configuration.HibernateConfiguration;
import app.models.Shipment;
import app.repositories.ShipmentRepository;

@Repository
public class ShipmentRepositoryImpl implements ShipmentRepository {
    @Override public Shipment save(Shipment shipment) {
        SessionFactory sessionFactory  = HibernateConfiguration.getSessionFactory();
        Session currentSession = sessionFactory.openSession();
        Transaction transaction = currentSession.beginTransaction();

        Integer idOfShipment = (Integer)currentSession.save(shipment);
        transaction.commit();
        currentSession.close();

        return findById(idOfShipment);
    }

    @Override public Shipment update(Shipment shipment) {
        SessionFactory sessionFactory  = HibernateConfiguration.getSessionFactory();
        Session currentSession = sessionFactory.openSession();
        Transaction transaction = currentSession.beginTransaction();

        Integer idOfShipment = shipment.getId();
        currentSession.saveOrUpdate(shipment);
        transaction.commit();
        return findById(idOfShipment);
    }

    @SuppressWarnings("unchecked")
    @Override public Shipment findById(Integer id) {
        SessionFactory sessionFactory  = HibernateConfiguration.getSessionFactory();
        Session currentSession = sessionFactory.openSession();
        Transaction transaction = currentSession.beginTransaction();

        TypedQuery<Shipment> query = currentSession.getNamedQuery("findShipmentById");
        query.setParameter("id", id);

        Shipment shipment;
        try {
            shipment = query.getSingleResult();
        } catch(NoResultException exception) {
            shipment = null;
        }

        transaction.commit();
        currentSession.close();

        return shipment;
    }

    @SuppressWarnings("unchecked")
    @Override public Shipment findByKey(String key) {
        SessionFactory sessionFactory  = HibernateConfiguration.getSessionFactory();
        Session currentSession = sessionFactory.openSession();
        Transaction transaction = currentSession.beginTransaction();

        TypedQuery<Shipment> query = currentSession.getNamedQuery("findShipmentByKey");
        query.setParameter("key", key);

        Shipment shipment;
        try {
            shipment = query.getSingleResult();
        } catch(NoResultException exception) {
            shipment = null;
        }

        transaction.commit();
        currentSession.close();

        return shipment;
    }

    @SuppressWarnings("unchecked")
    @Override public List<Shipment> findAll() {
        SessionFactory sessionFactory  = HibernateConfiguration.getSessionFactory();
        Session currentSession = sessionFactory.openSession();
        Transaction transaction = currentSession.beginTransaction();

        TypedQuery<Shipment> query = currentSession.getNamedQuery("findAllShipments");
        List<Shipment> shipments =  query.getResultList();
        
        transaction.commit();
        currentSession.close();

        return shipments;
    }

    @Override public boolean delete(Shipment shipment) {
        Session currentSession  = HibernateConfiguration.getSessionFactory().openSession();
        Transaction transaction = currentSession.beginTransaction();

        Integer id = shipment.getId();
        currentSession.delete(shipment);

        transaction.commit();
        currentSession.clear();

        return findById(id) == null;
    }
}

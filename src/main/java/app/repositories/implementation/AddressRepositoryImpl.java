package app.repositories.implementation;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import app.configuration.HibernateConfiguration;
import app.models.Address;
import app.repositories.AddressRepository;

@Repository
public class AddressRepositoryImpl implements AddressRepository {
      @Override public Address save(Address address) {
        Session currentSession  = HibernateConfiguration.getSessionFactory().openSession();
        Transaction transaction = currentSession.beginTransaction();
        Integer idOnSavedAddress = (Integer)currentSession.save(address);
        transaction.commit();
        currentSession.close();
        return findById(idOnSavedAddress);
    }

    @Override public Address update(Address address) {
        Session currentSession  = HibernateConfiguration.getSessionFactory().openSession();
        Transaction transaction = currentSession.beginTransaction();

        Integer idOfUpdatedAddress = address.getId();
        currentSession.saveOrUpdate(address);
        transaction.commit();
        currentSession.close();
        return findById(idOfUpdatedAddress);
    }

    @SuppressWarnings("unchecked")
    @Override public Address findById(Integer id) {
        Session currentSession  = HibernateConfiguration.getSessionFactory().openSession();
        Transaction transaction = currentSession.beginTransaction();

        TypedQuery<Address> query = currentSession.getNamedQuery("findAddressById");
        query.setParameter("id", id);

        Address address;
        try {
            address = query.getSingleResult();
        } catch(NoResultException exception) {
            address = null;
        }

        transaction.commit();
        currentSession.close();

        return address;
    }

    @SuppressWarnings("unchecked")
    @Override public Address findByKey(String key) {
        Session currentSession  = HibernateConfiguration.getSessionFactory().openSession();
        Transaction transaction = currentSession.beginTransaction();

        TypedQuery<Address> query = currentSession.getNamedQuery("findAddressByKey");
        query.setParameter("key", key);

        Address address;
        try {
            address = query.getSingleResult();
        } catch(NoResultException exception) {
            address = null;
        }

        transaction.commit();
        currentSession.close();

        return address;
    }

    @SuppressWarnings("unchecked")
    @Override public List<Address> findAll() {
        Session currentSession  = HibernateConfiguration.getSessionFactory().openSession();
        Transaction transaction = currentSession.beginTransaction();

        TypedQuery<Address> query = currentSession.getNamedQuery("findAllAddresses");
        List<Address> addresses = query.getResultList();
        
        transaction.commit();
        currentSession.close();

        return addresses;
    }

    @Override public boolean delete(Address address) {
        Session currentSession  = HibernateConfiguration.getSessionFactory().openSession();
        Transaction transaction = currentSession.beginTransaction();

        Integer id = address.getId();
        currentSession.delete(address);

        transaction.commit();
        currentSession.clear();

        return findById(id) == null;
    }
}

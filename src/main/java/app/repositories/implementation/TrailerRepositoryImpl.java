package app.repositories.implementation;

import org.springframework.stereotype.Repository;
import app.configuration.HibernateConfiguration;
import javax.persistence.NoResultException;
import app.repositories.TrailerRepository;
import org.hibernate.SessionFactory;
import javax.persistence.TypedQuery;
import org.hibernate.Transaction;
import org.hibernate.Session;
import app.models.Trailer;
import java.util.List;

@Repository
public class TrailerRepositoryImpl implements TrailerRepository {
    @Override public Trailer save(Trailer trailer) {
        SessionFactory sessionFactory  = HibernateConfiguration.getSessionFactory();
        Session currentSession = sessionFactory.openSession();
        Transaction transaction = currentSession.beginTransaction();

        Integer idOfTrailer = (Integer)currentSession.save(trailer);
        transaction.commit();
        currentSession.close();

        return findById(idOfTrailer);
    }

    @Override public Trailer update(Trailer trailer) {
        SessionFactory sessionFactory  = HibernateConfiguration.getSessionFactory();
        Session currentSession = sessionFactory.openSession();
        Transaction transaction = currentSession.beginTransaction();

        Integer idOfTrailer = trailer.getId();
        currentSession.saveOrUpdate(trailer);
        transaction.commit();
        return findById(idOfTrailer);
    }

    @SuppressWarnings("unchecked")
    @Override public Trailer findById(Integer id) {
        SessionFactory sessionFactory  = HibernateConfiguration.getSessionFactory();
        Session currentSession = sessionFactory.openSession();
        Transaction transaction = currentSession.beginTransaction();

        TypedQuery<Trailer> query = currentSession.getNamedQuery("findTrailerById");
        query.setParameter("id", id);

        Trailer trailer;
        try {
            trailer = query.getSingleResult();
        } catch(NoResultException exception) {
            trailer = null;
        }

        transaction.commit();
        currentSession.close();

        return trailer;
    }

    @SuppressWarnings("unchecked")
    @Override public Trailer findByKey(String key) {
        SessionFactory sessionFactory  = HibernateConfiguration.getSessionFactory();
        Session currentSession = sessionFactory.openSession();
        Transaction transaction = currentSession.beginTransaction();

        TypedQuery<Trailer> query = currentSession.getNamedQuery("findTrailerByKey");
        query.setParameter("key", key);

        Trailer trailer;
        try {
            trailer = query.getSingleResult();
        } catch(NoResultException exception) {
            trailer = null;
        }

        transaction.commit();
        currentSession.close();

        return trailer;
    }

    @SuppressWarnings("unchecked")
    @Override public List<Trailer> findAll() {
        SessionFactory sessionFactory  = HibernateConfiguration.getSessionFactory();
        Session currentSession = sessionFactory.openSession();
        Transaction transaction = currentSession.beginTransaction();

        TypedQuery<Trailer> query = currentSession.getNamedQuery("findAllTrailers");
        List<Trailer> trailers =  query.getResultList();
        
        transaction.commit();
        currentSession.close();

        return trailers;
    }

    @Override public boolean delete(Trailer trailer) {
        Session currentSession  = HibernateConfiguration.getSessionFactory().openSession();
        Transaction transaction = currentSession.beginTransaction();

        Integer id = trailer.getId();
        currentSession.delete(trailer);

        transaction.commit();
        currentSession.clear();

        return findById(id) == null;
    }
}

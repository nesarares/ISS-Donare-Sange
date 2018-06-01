package donation.persistence.repository;

import donation.model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class Repository {

    private static SessionFactory factory = new Configuration()
            .configure()
            .addAnnotatedClass(User.class)
            .addAnnotatedClass(BloodComponentQuantity.class)
            .addAnnotatedClass(BloodRequest.class)
            .addAnnotatedClass(BloodTransfusionCenterProfile.class)
            .addAnnotatedClass(MedicalQuestionnaire.class)
            .addAnnotatedClass(DoctorProfile.class)
            .addAnnotatedClass(Donation.class)
            .addAnnotatedClass(DonorProfile.class)
            .buildSessionFactory();

    public Repository() {
    }

    public static <T> void add(Class<T> classz, T obj) {

        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        session.save(obj);
        tx.commit();

        session.close();
    }

    public static <T> Optional<T> get(Class<T> classz, Integer key) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        T obj = session.get(classz, key);
        tx.commit();

        session.close();
        if (obj == null)
            return Optional.empty();
        else
            return Optional.of(obj);
    }

    public static <T> void delete(Class<T> classz, T obj) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        session.delete(obj);
        tx.commit();

        session.close();
    }

    public static <T> void update(Class<T> classz, T oldObject, T newObject) {

        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(newObject);
        transaction.commit();
        session.close();

    }

    public static <T> boolean exists(Class<T> classz, T object) {

        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        T obj = session.get(classz, object.hashCode());
        tx.commit();

        session.close();
        if (obj == null)
            return false;
        else
            return true;
    }

    public static <T> List<T> getAll(final Class<T> classz) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        List<T> all = session.createCriteria(classz).list();
        tx.commit();

        session.close();
        return all;
    }

    public static <T> Optional<T> findObj(Class<T> classz, Predicate<T> predicate) {
        List<T> storage = getAll(classz);
        for (T object : storage)
            if (predicate.test(object))
                return Optional.of(object);
        return Optional.empty();
    }

    public static <T> List<T> filterAll(Class<T> classz, Predicate<T> predicate) {
        List<T> filtered = new ArrayList<>();
        List<T> storage = getAll(classz);
        for (T object : storage)
            if (predicate.test(object))
                filtered.add(object);
        return filtered;
    }

    public static Session makeSession() {
        return factory.openSession();
    }
}

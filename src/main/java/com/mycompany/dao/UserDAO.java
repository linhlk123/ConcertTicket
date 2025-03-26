package com.mycompany.dao;

import com.mycompany.Model.User;

import lombok.RequiredArgsConstructor;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class UserDAO {
    private final SessionFactory sessionFactory;

    @SuppressWarnings("deprecation")
    public User save(User user) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(user);
        transaction.commit();
        session.close();
        return user;
    }

    public List<User> listAll() {
        Session session = sessionFactory.openSession();
        List<User> users = session.createQuery("from User", User.class).list();
        session.close();
        return users;
    }

    public Optional<User> findById(Long id) {
        Session session = sessionFactory.openSession();
        User user = session.get(User.class, id);
        session.close();
        return Optional.ofNullable(user);
    }

    public Optional<User> findByEmail(String email) {
        Session session = sessionFactory.openSession();
        User user = session.createQuery("from User u where u.email = :email", User.class)
                            .setParameter("email", email)
                            .uniqueResult();
        session.close();
        return Optional.ofNullable(user);
    }

    @SuppressWarnings("deprecation")
    public Long countById(Long id) {
        Session session = sessionFactory.openSession();
        Long count = (Long) session.createQuery("select count(u.id) from User u where u.id = :id")
                                   .setParameter("id", id)
                                   .uniqueResult();
        session.close();
        return count;
    }


    public User getUserById(Long id) {
        Session session = sessionFactory.openSession();
        User user = session.get(User.class, id);
        session.close();
        return user;
    }

    @SuppressWarnings("deprecation")
    public void deleteById(Long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        User user = session.get(User.class, id);
        if (user != null) {
            session.delete(user);
        }
        transaction.commit();
        session.close();
    }

    public List<User> findAll() {
        Session session = sessionFactory.openSession();
        List<User> users = session.createQuery("from User", User.class).list();
        session.close();
        return users;
    }
    
}

package com.mycompany.dao;

import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.mycompany.Model.Ticket;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class TicketDAO {
    private final SessionFactory sessionFactory;

    @SuppressWarnings("deprecation")
    public Ticket save(Ticket ticket) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(ticket);
        transaction.commit();
        session.close();
        return ticket;
    }

    public Optional<Ticket> findById(Long id) {
        Session session = sessionFactory.openSession();
        Ticket ticket = session.get(Ticket.class, id);
        session.close();
        return Optional.ofNullable(ticket);
    }

    @SuppressWarnings("deprecation")
    public Long countById(Long id) {
        Session session = sessionFactory.openSession();
        Long count = (Long) session.createQuery("select count(u.id) from Ticket u where u.id = :id")
                                   .setParameter("id", id)
                                   .uniqueResult();
        session.close();
        return count;
    }


    /*public Ticket get(Long id) {
        Session session = sessionFactory.openSession();
        Ticket Ticket = session.get(Ticket.class, id);
        session.close();
        return Ticket;
    }*/

    @SuppressWarnings("deprecation")
    public void deleteById(Long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Ticket ticket = session.get(Ticket.class, id);
        if (ticket != null) {
            session.delete(ticket);
        }
        transaction.commit();
        session.close();
    }

    public List<Ticket> findAll() {
        Session session = sessionFactory.openSession();
        List<Ticket> tickets = session.createQuery("from Ticket", Ticket.class).list();
        session.close();
        return tickets;
    }

    public Optional<Ticket> findByTicketName(String ticketName) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Ticket t where t.ticketName = :name", Ticket.class)
                          .setParameter("name", ticketName)
                          .uniqueResultOptional();
        }
    }

    @SuppressWarnings("deprecation")
    public void saveAll(List<Ticket> tickets) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        for (Ticket ticket : tickets) {
            session.saveOrUpdate(ticket);
        }
        transaction.commit();
        session.close();
    }
    
}

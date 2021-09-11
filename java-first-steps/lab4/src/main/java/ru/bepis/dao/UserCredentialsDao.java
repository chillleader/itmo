package ru.bepis.dao;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import ru.bepis.model.Token;
import ru.bepis.model.UserCredentials;
import ru.bepis.util.HibernateSessionFactory;

@Stateless
public class UserCredentialsDao implements UserCredentialsDaoInterface {
  public UserCredentials findByUsername (String username) {
    return HibernateSessionFactory.getSessionFactory().openSession().get(UserCredentials.class, username);
  }

  @SuppressWarnings("Duplicates")
  public void save(UserCredentials user) {
    Session session = HibernateSessionFactory.getSessionFactory().openSession();
    Transaction tx1 = session.beginTransaction();
    session.save(user);
    tx1.commit();
    session.close();
  }

  public void update(UserCredentials user) {
    Session session = HibernateSessionFactory.getSessionFactory().openSession();
    Transaction tx1 = session.beginTransaction();
    session.update(user);
    tx1.commit();
    session.close();
  }

  public void delete(UserCredentials user) {
    Session session = HibernateSessionFactory.getSessionFactory().openSession();
    Transaction tx1 = session.beginTransaction();
    session.delete(user);
    tx1.commit();
    session.close();
  }

  public Token findTokenByUsername(String username) {
    return HibernateSessionFactory.getSessionFactory().openSession().get(Token.class, username);
  }

  @SuppressWarnings("Duplicates")
  public void updateToken(Token token) {
    try {
      Session session = HibernateSessionFactory.getSessionFactory().openSession();
      Transaction tx1 = session.beginTransaction();
      session.update(token);
      tx1.commit();
      session.close();
    } catch (Exception e) {
      Session session = HibernateSessionFactory.getSessionFactory().openSession();
      Transaction tx1 = session.beginTransaction();
      session.save(token);
      tx1.commit();
      session.close();
    }
  }

  public Token findToken(String token) {
    try {
      Query query= HibernateSessionFactory.getSessionFactory().openSession().
          createQuery("from Token where token=:name");
      query.setParameter("name", token);
      Token category = (Token) query.uniqueResult();
      return category;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}

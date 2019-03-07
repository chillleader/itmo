package ru.bepis.dao;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.validation.constraints.Size;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import ru.bepis.model.UserCredentials;
import ru.bepis.model.Verdict;
import ru.bepis.util.HibernateSessionFactory;

@Stateless
public class VerdictDao implements VerdictDaoInterface{

  @Override
  @SuppressWarnings("unchecked")
  public List<Verdict> findAll() {
    try {
      return HibernateSessionFactory.getSessionFactory().openSession().createCriteria(Verdict.class).list();
    } catch (Exception e) {
      return new ArrayList<>();
    }
  }

  @Override
  @SuppressWarnings("Duplicates")
  public void add(Verdict verdict) {
    Session session = HibernateSessionFactory.getSessionFactory().openSession();
    Transaction tx1 = session.beginTransaction();
    session.save(verdict);
    tx1.commit();
    session.close();
  }

  @Override
  public void removeAll() {
    String stringQuery = "DELETE FROM Verdict";
    Query query = HibernateSessionFactory.getSessionFactory().openSession().
        createSQLQuery("DELETE FROM verdicts");
    query.executeUpdate();
  }
}

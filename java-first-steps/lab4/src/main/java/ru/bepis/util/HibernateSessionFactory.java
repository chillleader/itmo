package ru.bepis.util;

import ru.bepis.model.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactory {
  private static SessionFactory sessionFactory;

  private HibernateSessionFactory() {}

  public static SessionFactory getSessionFactory() {
    if (sessionFactory == null) {
/*      try {*/
        Configuration configuration = new Configuration().configure();
        configuration.addAnnotatedClass(UserCredentials.class);
        configuration.addAnnotatedClass(Token.class);
        configuration.addAnnotatedClass(Verdict.class);
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        sessionFactory = configuration.buildSessionFactory(builder.build());

      /*} catch (Exception e) {
        System.out.println("Исключение!" + e);
      }*/
    }
    return sessionFactory;
  }
}
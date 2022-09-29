package repository;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenceManager {

  private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("java_was_2022");

  private PersistenceManager() {}

  public static EntityManagerFactory getEntityManagerFactory() {
    return emf;
  }
}

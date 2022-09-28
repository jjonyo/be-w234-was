package repository;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenceManager {

  private static final String PERSISTENCE_NAME = "java_was_2022";
  private static final PersistenceManager instance = new PersistenceManager();
  private EntityManagerFactory emf;

  private PersistenceManager() {}

  public static PersistenceManager getInstance() {
    return instance;
  }

  public EntityManagerFactory getEntityManagerFactory() {
    if (emf == null) {
      createEntityManagerFactory();
    }

    return emf;
  }

  private void createEntityManagerFactory() {
    emf = Persistence.createEntityManagerFactory(PERSISTENCE_NAME);
  }
}

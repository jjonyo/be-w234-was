package repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;

import static org.assertj.core.api.Assertions.*;

class PersistenceManagerTest {

  @Test
  @DisplayName("EntityMangerFactory는 싱글톤이여야 한다.")
  void getEntityManagerFactoryTest() {
    EntityManagerFactory emf = PersistenceManager.getEntityManagerFactory();
    EntityManagerFactory emf2 = PersistenceManager.getEntityManagerFactory();

    assertThat(emf).isEqualTo(emf2);
  }

}
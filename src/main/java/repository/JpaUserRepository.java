package repository;

import exception.UserException;
import model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;

public class JpaUserRepository implements UserRepository {

  private final EntityManagerFactory emf = PersistenceManager.getEntityManagerFactory();

  @Override
  public Optional<User> save(User user) {
    if (findByUserId(user.getUserId()).isPresent()) {
      throw new UserException("이미 존재하는 유저Id입니다. id:" + user.getUserId());
    }

    EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    try {
      tx.begin();
      em.persist(user);
      tx.commit();
    } catch (Exception e) {
      tx.rollback();
      throw new UserException("유저 생성에 실패했습니다.");
    } finally {
      em.close();
    }

    return Optional.of(user);
  }

  @Override
  public Optional<User> findByUserId(String userId) {
    EntityManager em = emf.createEntityManager();

    Optional<User> result = em.createQuery("SELECT u FROM User u where u.userId = :userId", User.class)
            .setParameter("userId", userId)
            .getResultStream()
            .findAny();

    em.close();
    return result;
  }

  @Override
  public List<User> findAll() {
    EntityManager em = emf.createEntityManager();

    List<User> result = em.createQuery("SELECT u FROM User u", User.class)
            .getResultList();

    em.close();
    return result;
  }

  @Override
  public boolean delete(User user) {
    EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    try {
      tx.begin();
      em.remove(user);
      tx.commit();
    } catch (Exception e) {
      tx.rollback();
      return false;
    } finally {
      em.close();
    }

    return true;
  }
}

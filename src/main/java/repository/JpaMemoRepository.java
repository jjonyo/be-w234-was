package repository;

import exception.MemoException;
import model.Memo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;

public class JpaMemoRepository implements MemoRepository {
  private final EntityManagerFactory emf = PersistenceManager.getEntityManagerFactory();

  @Override
  public Optional<Memo> save(Memo memo) {
    EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    try {
      tx.begin();
      em.persist(memo);
      tx.commit();
    } catch (Exception e) {
      tx.rollback();
      throw new MemoException("메모 생성에 실패했습니다.");
    } finally {
      em.close();
    }

    return Optional.of(memo);
  }

  @Override
  public Optional<Memo> findById(String id) {
    EntityManager em = emf.createEntityManager();

    Optional<Memo> result = em.createQuery("SELECT m FROM Memo m where m.id = :id", Memo.class)
            .setParameter("id", id)
            .getResultStream()
            .findAny();

    em.close();
    return result;
  }

  @Override
  public List<Memo> findAll() {
    EntityManager em = emf.createEntityManager();

    List<Memo> result = em.createQuery("SELECT m FROM Memo m", Memo.class)
            .getResultList();

    em.close();
    return result;
  }

  @Override
  public boolean delete(Memo memo) {
    //TODO
    return false;
  }
}

package repository;

import model.Memo;

import java.util.List;
import java.util.Optional;

public interface MemoRepository {
  Optional<Memo> save(Memo memo);

  Optional<Memo> findById(String id);

  List<Memo> findAll();

  boolean delete(Memo memo);
}

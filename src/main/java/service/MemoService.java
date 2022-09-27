package service;

import controller.dto.CreateMemoDto;
import model.Memo;
import repository.JpaMemoRepository;
import repository.MemoRepository;

public class MemoService {
  private static final MemoService instance = new MemoService();
  private final MemoRepository memoRepository;

  private MemoService() {
    this.memoRepository = new JpaMemoRepository();
  }

  public static MemoService getInstance() {
    return instance;
  }

  public void saveMemo(CreateMemoDto createMemoDto) {
    Memo memo = Memo.of(
            createMemoDto.getAuthor(),
            createMemoDto.getTitle(),
            createMemoDto.getContents()
    );

    memoRepository.save(memo);
  }
}

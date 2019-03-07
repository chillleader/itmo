package ru.bepis.dao;

import java.util.List;
import javax.ejb.Local;
import ru.bepis.model.Verdict;

@Local
public interface VerdictDaoInterface {

  List<Verdict> findAll();
  void add(Verdict verdict);
  void removeAll();

}

package sample;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.Script;
import org.seasar.doma.Insert;
import org.seasar.doma.Update;

import org.seasar.doma.jdbc.Result;
import java.util.List;
import java.util.Optional;

@Dao(config = AppConfig.class)
public interface EmpDao {
  @Script
  void create();

  @Select
  Optional<Emp> selectById(ID<Emp> id);

  @Select
  List<Emp> selectAll();

  @Insert
  Result<Emp> insert(Emp emp);

  @Update
  Result<Emp> update(Emp emp);

}
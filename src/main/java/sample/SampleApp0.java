package sample;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.seasar.doma.jdbc.Result;

public class SampleApp0 {
  private static final EmpDao dao = new EmpDaoImpl();

  public static void main(String[] args) {
    AppConfig.singleton().getTransactionManager().required(() -> {
      // create table & insert
      dao.create();
      List<Result<Emp>> inserted = Stream.of(
        new Emp(ID.of(-1), "scott", 10, -1),
        new Emp(ID.of(-1), "allen", 20, -1)
      ).map(
        dao::insert
      ).collect(Collectors.toList());
      System.out.println(inserted);
      // idが2のEmpのageを +1
      final Optional<Result<Emp>> updated =
        dao
          .selectById(ID.of(2))
          .map(emp ->
            dao.update(emp.grawOld())
          );
      System.out.println(updated);
      final List<Emp> list = dao.selectAll();
      list.forEach(System.out::println);
      // =>
      //   Emp(id=ID(1), name=scott, age=10, version=1)
      //   Emp(id=ID(2), name=allen, age=21, version=2)
    });
  }
}

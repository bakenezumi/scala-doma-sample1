package sample

import java.util.Optional
import org.seasar.doma.jdbc.Result

object SampleApp1 extends App {
  lazy val dao: EmpDao = new EmpDaoImpl()

  AppConfig.singleton.getTransactionManager.required({ () =>
    // create table & insert
    dao.create()
    val inserted = Seq(
      new Emp(ID.of(-1), "scott", 10, -1),
      new Emp(ID.of(-1), "allen", 20, -1)
    ).map {
      dao.insert
    }
    println(inserted)
    // idが2のEmpのageを +1
    val updated: Optional[Result[Emp]] = // Optionalは型推論効かない
      dao
        .selectById(ID.of(2))
        .map { emp =>
          dao.update(emp.grawOld)
        }
    println(updated)
    val list = dao.selectAll()
    list.forEach(println)
    // =>
    //   Emp(id=ID(1), name=scott, age=10, version=1)
    //   Emp(id=ID(2), name=allen, age=21, version=2)
  }: Runnable)
}

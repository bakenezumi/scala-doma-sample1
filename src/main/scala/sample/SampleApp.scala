package sample

import java.util.Optional
import org.seasar.doma.jdbc.Result

object SampleApp extends App {
  lazy val dao: EmpDao = new EmpDaoImpl()
  lazy val tx = AppConfig.singleton.getTransactionManager
  lazy val NOT_ASSIGNED_EMP_ID = ID.of[Emp](-1)
  lazy val INITIAL_VERSION = -1

  tx.required({ () =>
    dao.create() // create table
    val emp1 = new Emp(NOT_ASSIGNED_EMP_ID, "scott", 10, INITIAL_VERSION)
    dao.insert(emp1)
    val emp2 = new Emp(NOT_ASSIGNED_EMP_ID, "allen", 20, INITIAL_VERSION)
    dao.insert(emp2)
    // idが2のEmpのageを +1
    val updated: Optional[Result[Emp]] = // 型推論がうまく効かないので指定する必要がある
      dao
        .selectById(ID.of(2))
        .map { emp =>
          dao.update(emp.grawOld())
        }
    println(updated) // => Optional[Result(entity=Emp(id=ID(2), name=allen, age=21, version=2), count=1)]
    val list = dao.selectAll()
    list.forEach(println)
  // =>
  //   Emp(id=ID(1), name=scott, age=10, version=1)
  //   Emp(id=ID(2), name=allen, age=21, version=2)
  }: Runnable)

}

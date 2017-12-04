package sample

import java.util.Optional
import org.seasar.doma.jdbc.Result

object SampleApp1 extends App {
  lazy val dao: EmpDao = new EmpDaoImpl()
  lazy val tx = AppConfig.singleton.getTransactionManager
  lazy val NOT_ASSIGNED_EMP_ID = ID.of[Emp](-1)
  lazy val INITIAL_VERSION = -1

  tx.required({ () =>
    dao.create() // create table
    Seq(
      new Emp(NOT_ASSIGNED_EMP_ID, "scott", 10, INITIAL_VERSION),
      new Emp(NOT_ASSIGNED_EMP_ID, "allen", 20, INITIAL_VERSION)
    ).map {
      dao.insert
    }
    // idが2のEmpのageを +1
    val updated: Optional[Result[Emp]] = // JavaのOptionalを利用する場合型推論が効かないので明示する
      dao
        .selectById(ID.of(2))
        .map { emp =>
          dao.update(emp.grawOld)
        }
    println(updated) // => Optional[Result(entity=Emp(id=ID(2), name=allen, age=21, version=2), count=1)]
    val list = dao.selectAll()
    list.forEach(println)
  // =>
  //   Emp(id=ID(1), name=scott, age=10, version=1)
  //   Emp(id=ID(2), name=allen, age=21, version=2)
  }: Runnable)

}
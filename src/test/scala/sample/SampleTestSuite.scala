package sample

import org.scalatest.FunSuite
import SampleApp._

class SampleTestSuite extends FunSuite with org.scalatest.BeforeAndAfterAll {

  override def beforeAll(): Unit = {
    tx.required({ () =>
      dao.create()
    }: Runnable)
  }

  test("insert & select") {
    tx.required({ () =>
      tx.setRollbackOnly()
      val result = dao.insert(new Emp(NOT_ASSIGNED_EMP_ID, "foo", 10, -1))
      val selected = dao.selectById(result.getEntity.id)
      assert(selected.get == new Emp(ID.of(1), "foo", 10, 1))
    }: Runnable)
  }
}

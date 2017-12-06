package sample;

import org.seasar.doma.*;
import java.io.Serializable;

@Entity(immutable = true)
public class Emp implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @SequenceGenerator(sequence = "emp_id_seq")
  final ID<Emp> id;
  final String name;
  final int age;
  @Version
  final int version;

  public Emp(ID<Emp> id, String name, int age, int version) {
    this.id = id;
    this.name = name;
    this.age = age;
    this.version = version;
  }

  /* 年齢 +1 */
  public Emp grawOld() {
    return new Emp(id, name, age + 1, version);
  }

  @Override
  public boolean equals(Object obj) {
    if(obj instanceof Emp) {
      Emp emp = ((Emp) obj);
      return emp.id.equals(id) && emp.name.equals(name) && emp.age == age && emp.version == version;
    } else {
      return false;
    }
  }

  @Override
  public String toString() {
    return (String.format("Emp(id=%s, name=%s, age=%d, version=%d)", id, name, age, version));
  }

}

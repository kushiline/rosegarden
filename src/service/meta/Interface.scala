package izabyl.rosegarden
import scala.collection.mutable.Map
import scala.collection.mutable.HashMap

trait AbstractMethod

case class Method[Service, Return, Parameters](val impl: (Service, Parameters) => Return) extends AbstractMethod {

}

abstract class Interface[Service] {
  private val methods: Map[String, AbstractMethod] = new HashMap

  def method[Return, Parameters](name: String)(impl: (Service, Parameters) => Return):Unit = {
    if(methods.contains(name)) {
      throw new IllegalArgumentException("Duplicate method name " + name)
    }

    methods.put(name, Method(impl))
  }

  def client: Client[Service] = {
    //...
  }

  def server: Server[Service] = {
    //...
  }
}

class Client[Service]

class DemoService {
  val map = new HashMap[String, DemoService]
  val interface = new Interface[DemoService]{
    method("do something"){(_, _) => ()}
  }
}



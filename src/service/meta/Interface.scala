package izabyl.rosegarden
import scala.collection.mutable.Map
import scala.collection.mutable.HashMap
import scala.reflect.ClassTag

trait AbstractMethodSignature {
  def canCallWith(t: Any): Boolean
}

case class MethodSignature[Service, Return, Parameters](val impl: (Service, Parameters) => Return) extends AbstractMethodSignature {
  override def canCallWith(t: Any) = canCallWithInner(t)

  def canCallWithInner[Parameters : ClassTag](t: Any) = t match {
    case p: Parameters => true
    case _ => false
  }
}


abstract class Interface[Service] {
  val methods: Map[String, AbstractMethodSignature] = new HashMap

  def method[Return, Parameters](name: String)(impl: (Service, Parameters) => Return):Unit = {
    if(methods.contains(name)) {
      throw new IllegalArgumentException("Duplicate method name " + name)
    }

    methods.put(name, MethodSignature(impl))
  }
}

class Client[Service](val interface: Interface[Service]) {
  var connection: ClientConnection

  def call[Return, Parameters](method: String, parameters: Parameters): Return = {
    val signature = interface.methods.get(method).getOrElse(throw new NoSuchMethodException)
    if (!signature.canCallWith(parameters)) {
      throw new InvalidParameterTypeException
    }

    val result = connection.forward(method, parameters)
    return result.asInstanceOf[Return]
  }
}

trait ClientConnection {
  def forward[Parameters](method: String, p: Parameters): Any
}

class LocalClientConnection[Service](val service: Server[Service]) extends ClientConnection{
  override def forward[Parameters](method: String, parameters: Parameters): Any = {
    return service.handle(method, parameters)
  }
}


trait Server[Service] {
  def handle(method: String, parameters: Any): Any
}


class DemoService {
  val map = new HashMap[String, DemoService]
  val interface = new Interface[DemoService]{
    method("do something"){(_, _) => ()}
  }
}

class NoSuchMethodException extends RuntimeException
class InvalidParameterTypeException extends RuntimeException

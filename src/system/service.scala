package com.izabyl.rosegarden.system;

trait Interface
trait Method[R] extends Interface
// Each service will declare an type that extends this
// case classes will extend that type
// server will pattern match on those case classes
// clients will construct them

trait Service[T <: Interface] {
  def name: String
}

trait Server[T <: Interface] {
  protected def untypedHandle[M <: Method[_] with T](call: M): Any

  def handle[M <: Method[R] with T, R](call: M): R = {
    val r = untypedHandle(call)
    return r.asInstanceOf[R]
  }
}

trait Client[T <: Interface]

trait SingleBackend[T <: Interface] extends Service[T]{
  protected def backend: Server[T]
}

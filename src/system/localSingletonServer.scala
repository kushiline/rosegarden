package com.izabyl.rosegarden.system;

trait LocalSingletonServer[T <: Interface] extends SingleBackend[T]{
  protected def untypedHandle[M <: Method[_] with T](call: M): Any
  private val outerThis = this

  protected object backend extends Server[T] {
    protected def untypedHandle[M <: Method[_] with T](call: M): Any = {
      return outerThis.untypedHandle(call)
    }
  }
}

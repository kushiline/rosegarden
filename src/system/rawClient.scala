package com.izabyl.rosegarden.system;

trait RawClient[T <: Interface] extends SingleBackend[T] {
  object rawClient extends Client[T] {
    def apply[M <: Method[R] with T, R](v: M): R = {
      backend.handle[M, R](v)
    }
  }
}

package com.izabyl.rosegarden.storage;

import com.izabyl.rosegarden.system._;
import com.typesafe.scalalogging.LazyLogging;
import scala.collection.mutable.HashMap;


sealed trait StorageInterface extends Interface
final case class Get(k: String) extends StorageInterface with Method[Option[String]]
final case class Put(k: String, v: String) extends StorageInterface with Method[Nothing]

object Storage extends Service[StorageInterface] 
with LocalSingletonServer[StorageInterface] 
with RawClient[StorageInterface]
with LazyLogging{
  val name = "storage"
  val transient = new HashMap[String, String]()

  protected def untypedHandle[M <: Method[_] with StorageInterface](call: M) = {
    call match {
      case Get(key) => transient.get(key)
      case Put(key, value) => transient(key) = value
    }
  }
}

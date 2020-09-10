package com.izabyl.rosegarden.config;

import com.izabyl.rosegarden.system._;
import scala.collection.mutable.HashMap;
import java.nio.file.Paths

sealed trait ConfigInterface extends Interface
case object LocalDBPath extends ConfigInterface with Method[String]
case object Domain extends ConfigInterface with Method[String]

trait ConfigProvider[+T]{
  def get: T
}

class HardcodedConfigDefinition[T](val get: T) extends ConfigProvider[T]
class RuntimeConfigDefinition[T](val generate: () => T) extends ConfigProvider[T]{
  def get = generate()
}

object Config extends Service[ConfigInterface]
with LocalSingletonServer[ConfigInterface]
with RawClient[ConfigInterface]{
  val name = "config"
  val configs = new HashMap[ConfigInterface, ConfigProvider[Any]]

  define(Domain, new HardcodedConfigDefinition("alpha"))
  define(LocalDBPath, new RuntimeConfigDefinition[String](() => {
    val domain = Config.rawClient[Domain.type, String](Domain)
    Paths.get("~", ".rosegarden", domain, "localdb").toString
  }))


  def define[T](config: ConfigInterface with Method[T], value: ConfigProvider[T]) = {
    if(configs.contains(config)){
      throw new IllegalArgumentException("Duplicate config definition for " + config)
    }else{
      configs.put(config, value)
    }
  }

  protected def untypedHandle[M <: Method[_] with ConfigInterface](call: M) = {
    configs.getOrElse(call, 
      throw new IllegalArgumentException(s"No config definition exists for $call in this context")).get
  }
}


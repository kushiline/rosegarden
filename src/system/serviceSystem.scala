package com.izabyl.rosegarden.system;

import com.typesafe.scalalogging.LazyLogging;
import scala.collection.mutable.HashMap;

object ServiceSystem extends LazyLogging {
  val services = new HashMap[String, Service[_]]

  def register(s: Service[_]) = {
    println(s)
    println(s.name)
    if(services.contains(s.name)){
      throw new DuplicateRegistrationException(s.name)
    }

    services(s.name) = s
    logger.info("registered " + s.name + " service");
  }
}

class DuplicateRegistrationException(s: String) extends IllegalArgumentException(s)

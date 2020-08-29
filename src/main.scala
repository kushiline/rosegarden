package com.izabyl.rosegarden;

import com.typesafe.scalalogging.LazyLogging;
import com.izabyl.rosegarden.system.ServiceSystem;
import com.izabyl.rosegarden.storage._;

object Main extends App with LazyLogging {
  logger.info("Starting");
  Storage.rawClient(Put("some key", "some value"))
  logger.info(Storage.rawClient[Get, Option[String]](Get("some key")).get)
  logger.info("Done");
}

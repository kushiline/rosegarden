package izabyl.rosegarden

import izabyl.rosegarden.Service
import scala.collection.mutable.Seq
import scala.collection.mutable.HashMap
import scala.collection.parallel.CollectionConverters._

import com.typesafe.scalalogging.Logger

class ServiceSystem {

  val services = new HashMap[String, Service].par

  def add(s: Service) = {
    services(s.name) = new ServiceWrapper(s)
  }

  def run = {
    services.values.map(s => s.init)
    services.values.map(s => s.run)
  }
}

class ServiceWrapper(private val inner: Service) extends Service {
  val logger = Logger(name)

  override def name = inner.name
  override def version = inner.version

  override def init = {
    logger.info("Initializing")
    inner.init
  }

  override def run = {
    logger.info("Running")
    inner.run
  }
}

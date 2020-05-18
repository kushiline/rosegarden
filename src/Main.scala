package izaybl.rosegarden

import izabyl.rosegarden.service.todo.Todo
import izabyl.rosegarden.service.storage.Storage
import izabyl.rosegarden.ServiceSystem

object Rosegarden extends App {
  val system = new ServiceSystem

  registerServices
  system.run

  def registerServices = {
    system.add(new Todo)
    system.add(new Storage)
  }
}

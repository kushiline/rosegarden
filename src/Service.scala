package izabyl.rosegarden

trait Service {
  def name = this.getClass.getSimpleName
  def version = "1"

  def init = ()
  def run = ()
  def stop = ()
}


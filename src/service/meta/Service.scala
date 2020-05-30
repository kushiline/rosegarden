package izabyl.rosegarden

trait Service {
  def name = this.getClass.getSimpleName

  def init = ()
  def run = ()
  def stop = ()
}


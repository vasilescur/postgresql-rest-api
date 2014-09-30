package object models {

  type Date = java.util.Date

  def DB = play.api.db.DB

  def Logger = play.api.Logger

  implicit def current = play.api.Play.current

  implicit def global = scala.concurrent.ExecutionContext.Implicits.global

}
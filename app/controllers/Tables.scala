package controllers

import play.api.mvc.{Action, Controller}
import models.Table
import play.api.libs.json.{JsValue, Json}
import scala.concurrent.Future

/**
 * Created by Engin Yoeyen on 23/09/14.
 */
object Tables extends Controller{

  def getTables = Action.async {
    import  models.Table.tableFormat
    import models._
    dao.DAO.getTables map {
      case a:List[Table] => Ok(Json.toJson(a))
      case _ => NoContent
    }
  }

  def getTableColumns(name:String) = Action.async  {
    dao.DAO.getTableContentByName(dao.QueryBuilder.columns(name)) map {
      case a:JsValue => Ok(a)
      case _ => BadRequest("Bad request wrong table name or connection error")
    }
  }

  def getTableContentByName(name:String) = Action.async {
    dao.DAO.getTableContentByName(dao.QueryBuilder.select(name)) map {
      case a:JsValue => Ok(a)
      case _ => BadRequest("Bad request wrong table name or connection error")
    }
  }

  def query = Action.async(parse.json) { request =>
    (request.body \ "query").asOpt[String].map { q =>
        dao.DAO.getTableContentByName(q) map {
          case a:JsValue => Ok(a)
          case _ => BadRequest("Bad request wrong table name or connection error")
        }
    }.getOrElse {
      Future.successful( BadRequest("Missing parameter [query]") )
    }
  }

}

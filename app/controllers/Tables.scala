package controllers

import play.api.mvc._
import models.Table
import play.api.libs.json.{JsValue, Json}
import scala.concurrent.Future
import dao.{QueryBuilder => Q, DAO}

/**
 * Created by Engin Yoeyen on 23/09/14.
 */
object Tables extends Controller{

  def getTables = Action.async {
    import  models.Table.tableFormat
    DAO.getTables map {
      case a:List[Table] => Ok(Json.toJson(a))
      case _ => NoContent
    }
  }

  def query = Action.async(parse.json) { request =>
    (request.body \ "query").asOpt[String].map { q =>
      DAO.execute(q) map {
        case a:JsValue => Ok(a)
        case _ => BadRequest("Bad request wrong table name or connection error")
      }
    }.getOrElse {
      Future.successful( BadRequest("Missing parameter [query]") )
    }
  }


  def getTableColumns(name:String) =  execute(Q.columns(name))

  def getTableContentByName(name:String) = execute(Q.select(name))

  def filterByColumn(name:String, column:String, filter:String) = execute(Q.selectWhere(name,column,filter))


  def execute(query:String) = Action.async {
    DAO.execute(query) map {
      case a:JsValue => Ok(a)
      case _ => BadRequest("Bad request wrong table name or connection error")
    }
  }





}

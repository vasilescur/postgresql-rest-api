package controllers


import play.api.mvc._
import dao.{QueryBuilder => Q, DAO}
import models.{Row, Table,Error => Err}
import play.api.libs.json.{JsValue, Json}
import scala.concurrent.Future

object Application extends Controller {

  def index = Action  {
    Ok("")
  }

  def getTableColumns(name:String) =  execute(Q.columns(name))

  def getTableContentByName(name:String) = execute(Q.select(name))

  def filterByColumn(name:String, column:String, filter:String) = execute(Q.selectWhere(name,column,filter))

  def search(name:String, column:String, text:String, language:Option[String]) = execute(Q.search(name,column,text,language))


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
        case a:List[Row] => {
          val newList = a.flatMap(b => List(b.toJson()))
          if( newList.size ==  0){
            NoContent
          }else{
            Ok(Json.toJson(newList))
          }
        }
        case a:Err => BadRequest(Json.toJson(a))
        case _ => BadRequest("Bad request wrong table name or connection error")
      }
    }.getOrElse {
      Future.successful( BadRequest("Missing parameter [query]") )
    }
  }





  def execute(query:String) = Action.async {
    DAO.execute(query) map {
      case a:List[Row] => {
        val newList = a.flatMap(b => List(b.toJson()))
        if( newList.size ==  0){
           NoContent
        }else{
          Ok(Json.toJson(newList))
        }
      }
      case a:Err => BadRequest(Json.toJson(a))
      case _ => BadRequest("Bad request wrong table name or connection error")
    }
  }

}
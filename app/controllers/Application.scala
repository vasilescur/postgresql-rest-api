package controllers



import play.api.mvc._
import dao.{QueryBuilder => Q, DAO}
import models.{Row, Table,Error => Err}
import play.api.libs.json.{JsValue, Json}
import scala.concurrent.Future

import play.api.libs.json._
import play.api.Logger
import scala.concurrent.ExecutionContext.Implicits.global

object Application extends Controller {

  def index = Action {
    Ok("")
  }

  def getTables = Action.async {
    import  models.Table.tableFormat
    DAO.getTables map {
      case a:List[Table] => Ok(Json.toJson(a))
      case _ => NoContent
    }
  }

  def  getTableContent(name:String) = Action.async{ implicit request =>
    parser(name) {
      model => {
        execute(Q.build(model))
      }
    }
  }

  def filterByColumn(name:String, column:String, filter:String) = Action.async{ implicit request =>
    parser(name,Some(column),Some(filter)) {
      model => {
        execute(Q.build(model))
      }
    }
  }



  def query = Action.async(parse.json) { request =>
    (request.body \ "query").asOpt[String].map { q =>
      execute(q)
    }.getOrElse {
      Future.successful( BadRequest("Missing parameter [query]") )
    }
  }



  def parser[A, U](name:String,column:Option[String]=None, filter:Option[String]=None)(callback: models.Request => Future[SimpleResult])(implicit request: play.api.mvc.Request[A]): Future[SimpleResult]  = {
    val function: Option[String] = request.getQueryString("f")
    val limit: Option[String] = request.getQueryString("limit")
    val offset: Option[String] = request.getQueryString("offset")
    val by: Option[String] = request.getQueryString("by")
    val order: Option[String] = request.getQueryString("order")
    val language: Option[String] = request.getQueryString("language")
    val field: Option[String] = request.getQueryString("field")

    val req = models.Request(name,func=function,limit=limit,offset=offset,by=by,order=order,column=column,filter= filter,language=language, field=field)

    Q.validate(req) match{
      case true => callback(req)
      case false => Future.successful(BadRequest("This function does not exist"))
    }
  }


  def execute(query:String) = {
    DAO.execute(query) map {
      case a:List[Row] => {
        val newList = a.flatMap(b => List(b.toJson()))
        if( newList.size ==  0){
          NoContent
        }else{
          Ok(Json.toJson(newList))
        }
      }
      case e:Err => BadRequest(Json.toJson(e))
      case _ => BadRequest("Bad request wrong table name or connection error")
    }
  }


}


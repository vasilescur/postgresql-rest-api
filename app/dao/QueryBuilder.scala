package dao

import java.io.Serializable
import models.Request
import play.api.Logger

/**
 * Created by Engin Yoeyen on 30/09/14.
 */
object QueryBuilder {

  val FUNC_SEARCH = "search"
  val FUNC_COLUMNS = "columns"


  val defaultFunctions  = List("count", "min","max","avg","sum", "length", "md5")
  val allFunctions  =  FUNC_COLUMNS :: FUNC_SEARCH :: defaultFunctions

  def allowedFunction(f:String) = allFunctions.contains(f)

  def isDefaultFunction(f:String) = defaultFunctions.contains(f)

  def validate(req:Request) = {
    val validFunction = req.func match {
      case Some(s) => allowedFunction(s)
      case _ => true //return true if there is no function
    }
    validFunction
  }


  val tables = "SELECT table_name FROM information_schema.tables WHERE table_schema = 'public'; "

  def columns(name: String)  =  s"SELECT column_name, data_type FROM information_schema.columns WHERE table_schema = 'public' AND table_name = '$name' ;"


  def build(req:Request)  = {
    req.func match{
      case Some(FUNC_COLUMNS) => columns(req.name)
      case _ => {
        val list: List[Serializable] = List(
          select(req.name, req.field, req.func),
          whereWithSearch(req),
          orderBy(req.by),
          req.order,
          limitBy(req.limit),
          offsetBy(req.offset),
          ";"
        )
        merge(list)
      }
    }
  }

  private def select(name: String, field:Option[String], func:Option[String]) = {
    val fieldFunc = func match {
      case Some(s) if (isDefaultFunction(s)) => Some(s"$s(${field.getOrElse("*")})")
      case _ => Some(field.getOrElse("*"))
    }

    val list = List("SELECT", fieldFunc, "FROM", name)
    merge(list)
  }



  private def orderBy(by:Option[String] = None) = {
    by match {
      case Some(s) => Some(s"ORDER BY $s")
      case _ => None
    }
  }

  private def limitBy(limit:Option[String] = None) = {
    limit match {
      case Some(s) => Some(s"limit $s")
      case _ => None
    }
  }

  private def offsetBy(offset:Option[String] = None) = {
    offset match {
      case Some(s) => Some(s"offset $s")
      case _ => None
    }
  }

  private def search(column:String, text:String, language:Option[String] = None) = {
    language match {
      case Some(lan) =>  s"WHERE to_tsvector('$lan', $column) @@ to_tsquery('$lan', '$text')"
      case _ =>  s"WHERE to_tsvector($column) @@ to_tsquery('$text')"
    }
  }

  private def whereWithSearch(req:Request) = {
    req.func match {
      case Some(FUNC_SEARCH) => search(req.column.get, req.filter.get, req.language)
      case _ => where(req.column, req.filter)

    }
  }

  private def where(column:Option[String] = None, filter:Option[String] = None) = {
    for {
      c <- column
      f <- filter
    } yield s"WHERE $c = '$f'"
  }


  private def merge(list: List[Serializable]):String =   {
    list.filter(_ != None)
      .map(_  match {
      case Some(s) => s
      case s:String => s
    }).mkString(" ")
  }


}

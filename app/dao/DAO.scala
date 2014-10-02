package dao

import models._
import anorm._
import play.api.libs.json.{Json, JsObject}

import scala.concurrent.Future

// IMPORTANT import this to have the required tools in your scope
import play.api.libs.json._
// imports required functional generic structures
import play.api.libs.functional.syntax._
import play.api.libs.json.Writes._
import models.Row

/**
 * Created by Engin Yoeyen on 30/09/14.
 */
object DAO {

  def getTables() = Future {
    DB.withConnection { implicit connection =>
      SQL(QueryBuilder.tables).as(models.Table.table *)
    }
  }




  def execute(statement:String) = Future {
    val list = DB.withConnection { implicit connection =>
      val values  = SQL(statement)

      try{
        val jsonList = values().map ( row => {
          val rowData = new models.Row()
          for (columnList <- row.metaData.ms) {
            rowData.updateDynamic(columnList.column.alias.get)(extractValue(columnList.clazz,row,columnList.column.alias.get))
          }
          rowData
        }
        ).toList

        jsonList
      }catch{
        case e:Exception => {
          Logger.error(e.getMessage)
          models.Error(e.getMessage)
        }
      }

    }
    list
  }

  private def extractValue(clazz:String, row:SqlRow, value:String): Any = {
    clazz match {
      case "java.lang.Integer" => row[Int](value)
      case "java.lang.String" => row[String](value)
      case "java.lang.Long" => row[Long](value)
      case "java.sql.Date" =>  row[Date](value)
      case _ => throw new RuntimeException("Type not found.")
    }
  }


}

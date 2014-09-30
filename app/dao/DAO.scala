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


/**
 * Created by Engin Yoeyen on 30/09/14.
 */
object DAO {

  def getTables() = Future {
    DB.withConnection { implicit connection =>
      SQL(QueryBuilder.tables).as(models.Table.table *)
    }
  }





  def getTableContentByName(statement:String) = Future {
    val list = DB.withConnection { implicit connection =>

      val values  = SQL(statement)

      try{

        val jsonList = values().map (row =>{

          val columnList = row.metaData.ms

          var jsonObject: JsObject = Json.obj()
          for(m <- columnList){
            val clazz = m.clazz match {
              case "java.lang.Integer" => Json.obj(  m.column.alias.get -> row[Int](m.column.alias.get) )
              case "java.lang.String" => Json.obj(  m.column.alias.get -> row[String](m.column.alias.get) )
              case "java.lang.Long" => Json.obj(   m.column.alias.get -> row[Long](m.column.alias.get) )
              case "java.sql.Date" => Json.obj(  m.column.alias.get -> row[Date](m.column.alias.get) )
              case _ => Json.obj(   m.column.alias.get -> row[String](m.column.alias.get))
            }

            jsonObject = jsonObject.++(clazz)

          }
          jsonObject

        }

        ).toList
        Json.toJson(jsonList)

      }catch{
        case e:Exception => {
          Logger.error(e.getMessage)
          None
        }
      }

    }
    list
  }

}

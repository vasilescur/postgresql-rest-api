package models

import anorm.SqlParser._
import utils.Config

// IMPORTANT import this to have the required tools in your scope
import play.api.libs.json._
// imports required functional generic structures
import play.api.libs.functional.syntax._
import play.api.libs.json.Writes._

/**
 * Created by Engin Yoeyen on 23/09/14.
 */

case class Table( table_name : String )

object Table {

  implicit val jsonFormat = Json.format[Table]

  def tableUnlift(test: Table): Option[ (String, String,String)] =
    Some(test.table_name,
      Config.baseUrl+"/table/"+test.table_name,
      Config.baseUrl+"/table/"+test.table_name+"?f=columns"
    )

  implicit val tableFormat = (
    (__ \ "name").write[String] and
      (__ \ "data").write[String] and
      (__ \ "columns").write[String]
    )(unlift(tableUnlift))


  val table =
    str("table_name") map {
      table_name =>
        Table(table_name)
    }

}
package models

import play.api.libs.json.Json

/**
 * Created by Engin Yoeyen on 03/10/14.
 */
case class Error(msg:String)

object Error {
  implicit val jsonFormat = Json.format[Error]
}

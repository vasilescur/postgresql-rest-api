package models


import play.api.libs.json.{JsObject, JsValue, Json}

/**
 * Created by Engin Yoeyen on 02/10/14.
 */
class Row {

  private var map = Map.empty[String, Any]

  def select(name: String) = {
    map get name getOrElse Logger.error("method not found")
  }


  def update(name: String)(value: Any) {
    map += name -> value
  }

  def hasElement = map.size > 0

  override def toString = toJson().toString()

  def toJson(): Option[JsObject] = {
    if(hasElement){
      val list = for ((k,v) <- map) yield Json.obj( k -> anyWriter(v))
      Some(list.reduce(_ ++ _))
    }else{
      None
    }
  }

  private def anyWriter(a:Any): JsValue = {
    a match {
      case a: Double => Json.toJson(a)
      case a: Float => Json.toJson(a)
      case a: Long => Json.toJson(a)
      case a: Int => Json.toJson(a)
      case a: String => Json.toJson(a)
      case a: Boolean => Json.toJson(a)
      case _ => throw new RuntimeException("Type not serializable.")
    }
  }




}


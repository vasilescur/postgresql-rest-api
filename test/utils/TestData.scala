package utils

import play.api.libs.json.{JsValue, Json}

/**
 * Created by Engin Yoeyen on 02/10/14.
 */
object TestData {

  val jsonObject1 = Json.obj(
    "id" -> 7808,
    "title" -> "The Shining",
    "author_id" -> 4156,
    "subject_id" -> 9
  )

  val jsonObject2 = Json.obj(
    "id" -> 4513,
    "title" -> "Dune",
    "author_id" -> 1866,
    "subject_id" -> 15
  )

  val jsonArray  = Json.arr(jsonObject1, jsonObject2)
  val jsonList  = List(Some(jsonObject1), Some(jsonObject2))
}

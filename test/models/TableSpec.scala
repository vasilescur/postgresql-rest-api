package models

import test._
import play.api.libs.json.{JsValue,  Json}

/**
 * Created by Engin Yoeyen on 22/09/14.
 */
class TableSpec extends Specification{

  "Table.getTableContentByName" should {

    "return some table content" in new App {
      val json: JsValue = Json.arr(
        Json.obj(
          "id" -> 7808,
          "title" -> "The Shining",
          "author_id" -> 4156,
          "subject_id" -> 9
        ),
        Json.obj(
          "id" -> 7809,
          "title" -> "Franklin in the Dark",
          "author_id" -> 4159,
          "subject_id" -> 2
        )
      )

      val result = dao.DAO.execute(dao.QueryBuilder.select("books")) map {
        case a: JsValue  => {
          a mustEqual json
          success
        }
        case _ => failure("This table should exist")
      }
      sync { result }
    }

    "return None if there is no result" in new App {
      val result = dao.DAO.execute(dao.QueryBuilder.select("catalogs")) map {
        case a: JsValue  => failure("There should be no table named catalogs")
        case _  => success
      }
      sync { result }
    }
  }


  "Table.getTables" should {
    "return list of tables" in new App {
      val result = dao.DAO.getTables  map {
        case list:List[Table] => list.exists( i => i.table_name == "books") mustEqual true
        case _ =>  failure("Could not fetch the tables")
      }
      sync { result }
    }
  }

}

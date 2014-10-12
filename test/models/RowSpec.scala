package models

import org.specs2.mutable.Specification
import utils.TestData
import play.api.libs.json.JsObject
import java.sql.{Time, Timestamp}
import com.ning.http.util.DateUtil
import scala.Option

/**
 * Created by Engin Yoeyen on 02/10/14.
 */
class RowSpec extends Specification {



  "Row " should {
    val row = new Row()

    "return false if has no element" in {
      row.hasElement mustEqual false
    }

    "return None from toJson" in {
      row.toJson() mustEqual None
    }

    "add new value" in {
      row.update("id")(7808)
      row.update("title")("The Shining")
      row.update("author_id")(4156)
      row.update("subject_id")(9)

      row.select("id")         mustEqual 7808
      row.select("title")      mustEqual "The Shining"
      row.select("author_id")  mustEqual 4156
      row.select("subject_id") mustEqual 9
    }

    "return true if has any element" in {
      row.hasElement mustEqual true
    }

    "return valid json" in {
      row.toJson() mustEqual Some(TestData.jsonObject1)
    }
  }


  "Row " should {

    "convert all data types into valid json" in {
      val row = new Row()

      row.update("c_smallint")(32767)
      row.update("c_integer")(2147483647)
      row.update("c_bigint")(1E+30)
      row.update("c_decimal")(4.99)
      row.update("c_decimal")(29999.99)
      row.update("c_character")("ab")
      row.update("c_character")("ab")
      row.update("c_text")("text")
      row.update("c_varchar")("var")
      row.update("c_boolean")(true)

      row.update("c_timestamp")(new Timestamp(System.currentTimeMillis()))
      row.update("c_date")(new Date(System.currentTimeMillis()))
      row.update("c_time")(new Time(System.currentTimeMillis()))

      row.toJson() must beSome[JsObject]
    }

  }
}

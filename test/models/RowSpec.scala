package models

import org.specs2.mutable.Specification
import utils.TestData

/**
 * Created by Engin Yoeyen on 02/10/14.
 */
class RowSpec extends Specification {

  val row = new Row()

  "Row " should {

    "return false if has no element" in {
      row.hasElement mustEqual false
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
}

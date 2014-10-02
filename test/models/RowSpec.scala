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
      row.updateDynamic("id")(7808)
      row.updateDynamic("title")("The Shining")
      row.updateDynamic("author_id")(4156)
      row.updateDynamic("subject_id")(9)

      row.selectDynamic("id")         mustEqual 7808
      row.selectDynamic("title")      mustEqual "The Shining"
      row.selectDynamic("author_id")  mustEqual 4156
      row.selectDynamic("subject_id") mustEqual 9
    }

    "return true if has any element" in {
      row.hasElement mustEqual true
    }

    "return valid json" in {
      row.toJson() mustEqual Some(TestData.jsonObject1)
    }

  }
}

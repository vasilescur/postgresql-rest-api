package dao

import org.specs2.mutable.Specification
import test.App
import play.api.test.FakeRequest
import play.api.test.Helpers._

/**
 * Created by Engin Yoeyen on 30/09/14.
 */
class QueryBuilderSpec  extends Specification {



  "QueryBuilder" should {
    "build select statement" in {
      QueryBuilder.select("tableName") mustEqual "SELECT * FROM tableName ;"
    }

    "build column selection statement" in {
      QueryBuilder.columns("tableName") mustEqual "SELECT column_name, data_type FROM information_schema.columns WHERE table_schema = 'public' AND table_name = 'tableName' ;"
    }




  }

}

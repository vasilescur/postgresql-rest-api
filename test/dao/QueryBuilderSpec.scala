package dao

import org.specs2.mutable.Specification

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

    "build statement with where" in {
      QueryBuilder.selectWhere("tableName", "id","2") mustEqual "SELECT * FROM tableName WHERE id = '2' ;"
    }
  }

}

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

    "build search statement without language" in {
      QueryBuilder.search("tableName", "title","python") mustEqual "SELECT * FROM tableName WHERE to_tsvector(title) @@ to_tsquery('python') ;"
    }

    "build search statement with language" in {
      QueryBuilder.search("tableName", "title","python", Some("english")) mustEqual "SELECT * FROM tableName WHERE to_tsvector('english', title) @@ to_tsquery('english', 'python') ;"
    }
  }

}

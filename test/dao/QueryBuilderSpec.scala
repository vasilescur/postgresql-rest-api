package dao

import org.specs2.mutable.Specification
import models.Request

/**
 * Created by Engin Yoeyen on 30/09/14.
 */
class QueryBuilderSpec  extends Specification {

  "QueryBuilder" should {
    "build select statement" in {
      QueryBuilder.build(Request("tableName")) mustEqual "SELECT * FROM tableName ;"
    }

    "build column selection statement" in {
      QueryBuilder.columns("tableName") mustEqual "SELECT column_name, data_type FROM information_schema.columns WHERE table_schema = 'public' AND table_name = 'tableName' ;"
    }

    "build statement with where" in {
      val req = Request("tableName", column=Some("id"),filter=Some("2"))
      QueryBuilder.build(req) mustEqual "SELECT * FROM tableName WHERE id = '2' ;"
    }

    "build search statement without language" in {
      val req = Request("tableName", func=Some("search"), column=Some("title"), filter=Some("python"))
      QueryBuilder.build(req) mustEqual "SELECT * FROM tableName WHERE to_tsvector(title) @@ to_tsquery('python') ;"
    }

    "build search statement with language" in {
      val req = Request("tableName", func=Some("search"), column=Some("title"),filter=Some("python"), language = Some("english"))
      QueryBuilder.build(req) mustEqual "SELECT * FROM tableName WHERE to_tsvector('english', title) @@ to_tsquery('english', 'python') ;"
    }

    "build ORDER BY statement " in {
      val req = Request("tableName", by=Some("columnName"))
      QueryBuilder.build(req) mustEqual "SELECT * FROM tableName ORDER BY columnName ;"
    }

    "build ORDER BY with ASC&DESC statement " in {
      val reqAsc = Request("tableName", by=Some("columnName"),order = Some("ASC"))
      val reqDesc = Request("tableName", by=Some("columnName"),order = Some("DESC"))

      QueryBuilder.build(reqAsc) mustEqual "SELECT * FROM tableName ORDER BY columnName ASC ;"
      QueryBuilder.build(reqDesc) mustEqual "SELECT * FROM tableName ORDER BY columnName DESC ;"
    }

    "build limit statement " in {
      val req = Request("tableName", limit=Some("10"))
      QueryBuilder.build(req) mustEqual "SELECT * FROM tableName limit 10 ;"
    }

    "build limit&offset statement " in {
      val req = Request("tableName", limit=Some("10"), offset=Some("20"))
      QueryBuilder.build(req) mustEqual "SELECT * FROM tableName limit 10 offset 20 ;"
    }

    "build limit & offset & order by statement " in {
      val req = Request("tableName", limit=Some("10"), offset=Some("20"), by=Some("columnName"), order = Some("ASC"))
      QueryBuilder.build(req) mustEqual "SELECT * FROM tableName ORDER BY columnName ASC limit 10 offset 20 ;"
    }


    "build function statement" in {
      val req = Request("tableName", func = Some("count"))
      QueryBuilder.build(req) mustEqual "SELECT count(*) FROM tableName ;"
    }

    "build function statement2" in {
      val req = Request("tableName", column=Some("id"),filter=Some("2"), func = Some("count"))
      QueryBuilder.build(req) mustEqual "SELECT count(*) FROM tableName WHERE id = '2' ;"
    }

  }
}

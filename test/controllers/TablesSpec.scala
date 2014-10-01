package controllers

import test.{Specification, App}
import play.api.test.FakeRequest
import play.api.test.Helpers._
import play.api.libs.json.Json


/**
 * Created by Engin Yoeyen on 23/09/14.
 */
class TablesSpec extends Specification {

  "GET /tables" should {
    "return list of tables" in new App {
      val request = FakeRequest(GET, "/tables" )
      val response = route(request).get
      status(response) mustEqual OK
    }
  }

  "GET /table/:name" should {
    "return table content" in new App {
      val request = FakeRequest(GET, "/table/books" )
      val response = route(request).get
      status(response) mustEqual OK
    }

    "return Bad request when table does not exist" in new App {
      val request = FakeRequest(GET, "/table/wrongTableName" )
      val response = route(request).get
      status(response) mustEqual BAD_REQUEST
    }
  }


  "GET /table/:name/columns" should {
    "return list of columns" in new App {
      val request = FakeRequest(GET, "/table/books/columns" )
      val response = route(request).get
      status(response) mustEqual OK
    }

    "return Bad request when table does not exist" in new App {
      val request = FakeRequest(GET, "/table/wrongTableName/columns" )
      val response = route(request).get
      status(response) mustEqual BAD_REQUEST
    }
  }


  "POST /query" should {
    val validJson = Json.obj( "query" -> "SELECT * FROM books")
    val wrongQuery = Json.obj( "query" -> "SELECT * FROM wrongTable")

    "make a valid query" in new App {
      val request = FakeRequest(POST, "/query" ).withJsonBody(validJson)
      val response = route(request).get
      status(response) mustEqual OK
    }

    "return bad request when sql statement is wrong" in new App {
      val request = FakeRequest(POST, "/query" ).withJsonBody(wrongQuery)
      val response = route(request).get
      status(response) mustEqual BAD_REQUEST
    }

    "return bad request when there is no parameter" in new App {
      val request = FakeRequest(POST, "/query" )
      val response = route(request).get
      status(response) mustEqual BAD_REQUEST
    }
  }



  "GET /table/:name/:row/:filter " should {
    "return list of rows based on request" in new App {
      val request = FakeRequest(GET, "/table/books/id/7809" )
      val response = route(request).get
      status(response) mustEqual OK
    }

    "return Bad request when table does not exist" in new App {
      val request = FakeRequest(GET, "/table/books2/id/7809" )
      val response = route(request).get
      status(response) mustEqual BAD_REQUEST
    }
    /* FIXED
    "return Bad request when column does not exist" in new App {
      val request = FakeRequest(GET, "/table/books/id2/7809" )
      val response = route(request).get
      status(response) mustEqual BAD_REQUEST
    }
*/
  }




}

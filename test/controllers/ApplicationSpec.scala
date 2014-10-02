package controllers

import test._

import play.api.test._
import play.api.test.Helpers._
import play.api.libs.json.Json


class ApplicationSpec extends Specification {

  "GET /" should {
    "load the application" in new App {
      val request = FakeRequest(GET, "/")
      val response = route(request).get
      status(response) mustEqual OK
    }
  }

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
  }


  "POST /query" should {
    val validJson = Json.obj( "query" -> "SELECT * FROM books")
    val validJsonNoData = Json.obj( "query" -> "SELECT * FROM books WHERE id = '2' ")
    val wrongQuery = Json.obj( "query" -> "SELECT * FROM wrongTable")

    "make a valid query" in new App {
      val request = FakeRequest(POST, "/query" ).withJsonBody(validJson)
      val response = route(request).get
      status(response) mustEqual OK
    }

    "return BAD_REQUEST (400) when sql statement is wrong" in new App {
      val request = FakeRequest(POST, "/query" ).withJsonBody(wrongQuery)
      val response = route(request).get
      status(response) mustEqual BAD_REQUEST
    }

    "return BAD_REQUEST (400) when there is no parameter" in new App {
      val request = FakeRequest(POST, "/query" )
      val response = route(request).get
      status(response) mustEqual BAD_REQUEST
    }

    "NO_CONTENT (204) when there is none" in new App {
      val request = FakeRequest(POST, "/query" ).withJsonBody(validJsonNoData)
      val response = route(request).get
      status(response) mustEqual NO_CONTENT
    }
  }





  "GET /table/:name/:row/:filter " should {
    "return list of rows based on request" in new App {
      val request = FakeRequest(GET, "/table/books/id/7808" )
      val response = route(request).get
      status(response) mustEqual OK
    }

    "return NO_CONTENT (204) when there is no content as result of execution" in new App {
      val request = FakeRequest(GET, "/table/books/id/2" )
      val response = route(request).get
      status(response) mustEqual NO_CONTENT
    }


    "return Bad request when table does not exist" in new App {
      val request = FakeRequest(GET, "/table/books2/id/7809" )
      val response = route(request).get
      status(response) mustEqual BAD_REQUEST
    }

    "return Bad request when column does not exist" in new App {
      val request = FakeRequest(GET, "/table/books/id2/7809" )
      val response = route(request).get
      status(response) mustEqual BAD_REQUEST
    }

  }

}
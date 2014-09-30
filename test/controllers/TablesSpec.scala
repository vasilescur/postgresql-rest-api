package controllers

import test.{Specification, App}
import play.api.test.FakeRequest
import play.api.test.Helpers._


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
  }






}

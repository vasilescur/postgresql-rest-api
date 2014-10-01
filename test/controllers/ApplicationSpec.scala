package controllers

import test._

import play.api.test._
import play.api.test.Helpers._


class ApplicationSpec extends Specification {

  "GET /" should {

    "load the application" in new App {
      val request = FakeRequest(GET, "/")
      val response = route(request).get
      status(response) mustEqual OK
    }
  }
}
package controllers

import models.{ClientResponse, Summary}
import org.junit.runner.RunWith
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.AnyContentAsEmpty
import play.api.test.Helpers._
import play.api.test._
import services.TouringService

@RunWith(classOf[JUnitRunner])
class HomeControllerSpec extends Specification with Mockito {

  val titleJson: JsValue = Json.toJson(Summary("Truecaller Touring Test!"))

  "HomeController GET" should {

    "render the appSummary resource from a new instance of controller" in new WithApplication {
      val controller = new HomeController(stubControllerComponents(), mock[TouringService])
      val summary = controller.appSummary().apply(getRequest("/summary"))

      status(summary) must beEqualTo(OK)
      contentType(summary) must beSome("application/json")
      val resultJson = contentAsJson(summary)
      resultJson must beEqualTo(titleJson)
    }

    "render the appSummary resource from the application" in new WithApplication {
      val controller = app.injector.instanceOf[HomeController]
      val summary = controller.appSummary().apply(getRequest("/summary"))

      status(summary) must beEqualTo(OK)
      contentType(summary) must beSome("application/json")
      val resultJson = contentAsJson(summary)
      resultJson must beEqualTo(titleJson)
    }

    "render the appSummary resource from the router" in new WithApplication {
      val request = getRequest("/api/summary")
      val summary = route(app, request).get

      status(summary) must beEqualTo(OK)
      contentType(summary) must beSome("application/json")
      val resultJson = contentAsJson(summary)
      resultJson must beEqualTo(titleJson)
    }

    "create a new client" in new WithApplication {
      val cpuService = mock[TouringService]
      cpuService.createClient(anyString) returns ClientResponse(42)
      val controller = new HomeController(stubControllerComponents(), cpuService)
      val newClientResponse = controller.newClientId().apply(getRequest("/newClientId"))

      status(newClientResponse) must beEqualTo(OK)
      contentType(newClientResponse) must beSome("application/json")
      val resultJson = contentAsJson(newClientResponse)
      resultJson must beEqualTo(Json.toJson(ClientResponse(42)))
      there was one(cpuService).createClient("Foobar")
    }
  }

  private def getRequest(uri: String): FakeRequest[AnyContentAsEmpty.type] = FakeRequest(GET, uri).withHeaders("User-Agent" -> "Foobar")

}

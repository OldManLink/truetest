package controllers

import helpers.Move.O
import models.{BoardDescription, Row, Square, SquareDescription, Summary, Tour, TourRequest, TourResponse}
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
  val tourRequest: TourRequest = TourRequest(BoardDescription(1, 1), SquareDescription(0,0))

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

    "get the tours for a tourRequest" in new WithApplication {
      val touringService = mock[TouringService]
      val tourResponse: TourResponse = TourResponse(Seq(Tour(42, 0, 0, List(O))))
      touringService.getTours(any[TourRequest]()) returns tourResponse
      val controller = new HomeController(stubControllerComponents(), touringService)

      val getTours = controller.getTours().apply(
        postJsonRequest("/getTours").withJsonBody(Json.toJson(tourRequest))
      )

      status(getTours) must beEqualTo(OK)
      contentType(getTours) must beSome("application/json")
      val resultJson = contentAsJson(getTours)
      resultJson must beEqualTo(Json.toJson(tourResponse))
      there was one(touringService).getTours(any[TourRequest]())
    }
  }

  private def getRequest(uri: String): FakeRequest[AnyContentAsEmpty.type] = FakeRequest(GET, uri).withHeaders("User-Agent" -> "Foobar")

  private def postJsonRequest(uri: String): FakeRequest[AnyContentAsEmpty.type] = FakeRequest(POST, uri)
    .withHeaders("User-Agent" -> "Foobar", "Content-Type" -> "application/json")
}

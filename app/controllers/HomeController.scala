package controllers

import javax.inject._
import models.{Summary, TourRequest}
import play.api.libs.json.Json
import play.api.mvc._
import services.TouringService

@Singleton
class HomeController @Inject()(cc: ControllerComponents, touringService: TouringService) extends AbstractController(cc) {

  def appSummary: Action[AnyContent] = Action {
    Ok(Json.toJson(Summary("Truecaller Touring Test!")))
  }

  def getTours: Action[AnyContent] = Action {
    request =>
      val tourRequest = request.body.asJson.get.as[TourRequest]
      Ok(Json.toJson(touringService.getTours(tourRequest)))
  }
}

package controllers

import javax.inject._
import models.Summary
import play.api.libs.json.Json
import play.api.mvc._
import services.TouringService

@Singleton
class HomeController @Inject()(cc: ControllerComponents, cpuService: TouringService) extends AbstractController(cc) {

  def appSummary: Action[AnyContent] = Action {
    Ok(Json.toJson(Summary("Truecaller Touring Test!")))
  }

  def newClientId: Action[AnyContent] = Action {
    request =>
      Ok(Json.toJson(cpuService.createClient(request.headers("User-Agent"))))
  }
}

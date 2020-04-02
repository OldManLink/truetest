package services

import models.{Square, Tour, TourRequest, TourResponse}
import play.api.Logging
import play.api.libs.json.Json

trait TouringService {
  def getTours(tourRequest: TourRequest): TourResponse
}

class TouringServiceImpl extends TouringService with Logging {

  override def getTours(tourRequest: TourRequest): TourResponse = {
    val tours = Seq(
      Tour(0, "Tour 0", Seq((1,2),(4, 1),(3, 7),(7, 3))),
      Tour(1, "Tour 1", Seq((0,0),(2, 2),(4, 4),(6, 6),(8, 8)))
    )
    logger.info(s"Created tours for ${Json.toJson(tourRequest)}: ${Json.toJson(tours)}")
    TourResponse(tours)
  }
}

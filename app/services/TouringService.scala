package services

import helpers.TouringHelper
import models.{TourRequest, TourResponse}
import play.api.Logging
import play.api.libs.json.Json

trait TouringService {
  def getTours(tourRequest: TourRequest): TourResponse
}

class TouringServiceImpl extends TouringService with Logging {

  override def getTours(tourRequest: TourRequest): TourResponse = {

    val tours = TouringHelper(tourRequest).getTours

    logger.info(s"Created ${tours.size} tours for ${Json.toJson(tourRequest)}")
    TourResponse(tours)
  }
}

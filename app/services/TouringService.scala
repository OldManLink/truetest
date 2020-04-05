package services

import models.{TourRequest, TourResponse}
import play.api.Logging
import play.api.libs.json.Json

trait TouringService {
  def getTours(tourRequest: TourRequest): TourResponse
}

class TouringServiceImpl extends TouringService with StrategyService with Logging {

  override def getTours(tourRequest: TourRequest): TourResponse = {

    val searchStrategy = getStrategy(tourRequest)

    val tours = searchStrategy.getTours

    logger.info(s"Created ${tours.size} tours for ${Json.toJson(tourRequest)}, using ${searchStrategy.getClass.getSimpleName}")
    TourResponse(tours)
  }
}

package services

import helpers.TouringHelper
import models.{Board, Square, TourRequest, TourResponse}
import play.api.Logging
import play.api.libs.json.Json

trait TouringService {
  def getTours(tourRequest: TourRequest): TourResponse
}

class TouringServiceImpl extends TouringService with Logging {

  override def getTours(tourRequest: TourRequest): TourResponse = {
    val helper = TouringHelper(Board(tourRequest.board), Square(tourRequest.startingSquare))
    val tours = helper.getTours(tourRequest.max)

    logger.info(s"Created ${tours.size} tours for ${Json.toJson(tourRequest)}")
    TourResponse(tours)
  }
}

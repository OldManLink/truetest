package models

import play.api.libs.json.{Json, OFormat}

case class TourRequest(board: BoardDescription, startSquare: SquareDescription, max: Int)

object TourRequest {

  implicit val tourRequestFormat: OFormat[TourRequest] = Json.format[TourRequest]

  def apply(board: (Int, Int), square: (Int, Int) = (0, 0), max: Int = 1): TourRequest = {
    TourRequest(BoardDescription(board._1, board._2), SquareDescription(square._1, square._2), max)
  }
}

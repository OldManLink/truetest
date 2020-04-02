package models

import play.api.libs.json.{Json, OFormat}

case class TourResponse(tours: Seq[Tour])

object TourResponse {
  implicit val tourResponseFormat: OFormat[TourResponse] = Json.format[TourResponse]
}

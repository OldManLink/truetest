package models

import play.api.libs.json.{Json, OFormat}

case class SquareDescription(row: Int, column: Int)

object SquareDescription {
  implicit val boardFormat: OFormat[SquareDescription] = Json.format[SquareDescription]
}

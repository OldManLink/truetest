package models

import play.api.libs.json.{Json, OFormat}

case class BoardDescription(rows: Int, columns: Int)

object BoardDescription {
  implicit val boardFormat: OFormat[BoardDescription] = Json.format[BoardDescription]
}

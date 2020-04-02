package models

import play.api.libs.json.{Json, OFormat}

case class Row(index: Int, squares: Seq[Square])

object Row {
  implicit val rowFormat: OFormat[Row] = Json.format[Row]
}
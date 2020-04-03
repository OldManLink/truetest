package models

import play.api.libs.json.{Json, OFormat}

case class Row(index: Int, squares: Seq[Square]) {

  def getSquare(column: Int): Option[Square] = squares.find(_.columnIndex == column)
}

object Row {

  implicit val rowFormat: OFormat[Row] = Json.format[Row]
}
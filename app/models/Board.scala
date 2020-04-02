package models

import play.api.libs.json.{Json, OFormat}

case class Board(rows: Seq[Row])

object Board {
  implicit val boardFormat: OFormat[Board] = Json.format[Board]
}
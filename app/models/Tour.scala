package models

import helpers.Move
import play.api.libs.json.{Json, OFormat}

case class Tour(id: Int, startRow: Int, startColumn: Int, moves: List[Move], endCoords: (Int, Int))

object Tour {

  implicit val tourFormat: OFormat[Tour] = Json.format[Tour]
}
package models

import play.api.libs.json.{Json, OFormat}

case class Square(index: Int, rowIndex: Int, visited: Boolean = false) {
  def asVisited: Square = this.copy(visited = true)
}

object Square {
  implicit val squareFormat: OFormat[Square] = Json.format[Square]
}
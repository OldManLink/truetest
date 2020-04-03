package models

import play.api.libs.json.{Json, OFormat}

case class Row(index: Int, squares: Seq[Square]) {

  def visit(square: Square): Row = copy(squares = squares map { sq =>
    if (sq samePosition square) sq.asVisited else  sq
  })

  def getSquare(column: Int): Option[Square] = squares.find(_.columnIndex == column)

  def includes(square: Square): Boolean = squares.exists(sq => sq.samePosition(square))

  def allVisited: Boolean = squares.forall(_.visited)
}

object Row {

  implicit val rowFormat: OFormat[Row] = Json.format[Row]
}
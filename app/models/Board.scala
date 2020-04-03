package models

import play.api.libs.json.{Json, OFormat}

case class Board(rows: Seq[Row]) {

  def getSquare(row: Int, column: Int): Option[Square] = {
    rows.find(_.index == row).flatMap(_.getSquare(column))
  }

  def visit(square: Square): Board = {
    if(square.rowIndex < rows.size && square.columnIndex < rows.head.squares.size) {
      copy(rows = rows.map { row =>
        if (!row.includes(square)) row else row visit(square)
      })
    } else throw new IndexOutOfBoundsException("Requested row/column out of bounds")
  }

  def allVisited: Boolean = rows.forall(_.allVisited)
}

object Board {

  def apply(desc: BoardDescription): Board =
    Board(
      0 until desc.rows map { rowIndex =>
        Row(
          rowIndex, 0 until desc.columns map { colIndex =>
            Square(SquareDescription(rowIndex, colIndex))
          })
      })

  implicit val boardFormat: OFormat[Board] = Json.format[Board]
}
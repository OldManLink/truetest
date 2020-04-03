package models

import helpers.Move
import helpers.Step
import play.api.libs.json.{Json, OFormat}

case class Square(rowIndex: Int, columnIndex: Int, visited: Boolean = false) {

  def samePosition(square: Square): Boolean = this.rowIndex == square.rowIndex && this.columnIndex == square.columnIndex

  def allSteps(board: Board): List[Step] = {
    Move.validMoves.map(move => move.destination(board, this)).toList
  }

  def possibleSteps(board: Board): List[Step] = allSteps(board).filter(_.legal)

  def unvisitedSteps(board: Board): List[Step] = possibleSteps(board).filterNot(_.square.exists(_.visited))

  def asVisited: Square = copy(visited = true)
}

object Square {

  def apply(desc: SquareDescription): Square = {
    Square(desc.row, desc.column)
  }

  implicit val squareFormat: OFormat[Square] = Json.format[Square]
}
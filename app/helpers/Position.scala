package helpers

import models.{Board, Square, Tour}

case class Position(square: Square, board: Board, history: List[Move]){

  def done: Boolean = board.allVisited

  def toTour(id: Int, startingSquare: Square): Tour = Tour(id, startingSquare.rowIndex, startingSquare.columnIndex, history.reverse)
}

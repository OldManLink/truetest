package helpers

import models.{Board, Square, Tour}

case class Position(square: Square, history: List[Move], visited: List[Int] = Nil) {

  lazy val coords: (Int, Int) = (square.rowIndex, square.columnIndex)

  def completes(board: Board): Boolean = {
    history.size == board.squareCount - 1
  }

  def toTour(id: Int, startingSquare: Square): Tour = Tour(id, startingSquare.rowIndex, startingSquare.columnIndex, history.reverse, coords)
}

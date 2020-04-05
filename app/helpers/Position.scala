package helpers

import models.{Board, Square, Tour}

case class Position(square: Square, history: List[Move], visited: List[Int] = Nil) {

  lazy val coords: (Int, Int) = square.asTuple

  def completes(board: Board): Boolean = {
    history.size == board.squareCount
  }

  def nextSteps: Seq[Step] = square.possibleSteps.filterNot(step => visited.contains(step.square.boardIndex))
    .sorted(UnvisitedOrdering(visited))

  def toTour(id: Int, startSquare: Square): Tour = Tour(id, startSquare.rowIndex, startSquare.columnIndex, history.reverse.drop(1), coords)

  case class UnvisitedOrdering(visited: Seq[Int]) extends Ordering[Step] {
    @Override
    override def compare(x: Step, y: Step): Int = x.unVisitedSize(visited) compare y.unVisitedSize(visited)
  }
}

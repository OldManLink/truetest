package helpers

import models.{Board, Square, Tour}

case class TouringHelper(board: Board, startingSquare: Square) {

  def getTours: Seq[Tour] = finishedTours match {
    case Stream() => Nil
    case stream => stream.toList.zipWithIndex.map { case (p, i) => p.toTour(i, startingSquare) }
  }

  def positionStream(position: Position): Stream[Position] = {
    position.square.unvisitedSteps(position.board).map { step =>
      val next = step.square.get
      Position(next, position.board.visit(next), step.move :: position.history)
    }.toStream
  }

  def from(initial: Stream[Position]): Stream[Position] = {
    if (initial.isEmpty) Stream empty
    else {
      val newPaths = for {
        position <- initial
        next <- positionStream(position)
      }
        yield next
      initial ++ from(newPaths)
    }
  }

  lazy val toursFromStart: Stream[Position] = from(List(Position(startingSquare, board, Nil)).toStream)

  lazy val finishedTours: Stream[Position] = toursFromStart.filter(position => position done)
}

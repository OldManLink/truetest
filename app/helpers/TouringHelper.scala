package helpers

import models.{Board, Square, Tour}

case class TouringHelper(board: Board, startingSquare: Square) {

  def getTours(max: Int): Seq[Tour] = finishedTours match {
    case Stream() => Nil
    case stream => (stream take max).toList.zipWithIndex.map { case (pos, id) =>
      pos.toTour(id, startingSquare)
    }
  }

  def toursStream(position: Position): Stream[Position] = {
    position.square.possibleSteps(board).filterNot(stepUnderTest =>
      stepUnderTest.square.exists(square => position.visited.contains(square.index(board))))
      .map { nextStep =>
        val nextSquare = nextStep.square.get
        Position(nextSquare, nextStep.move :: position.history, position.visited + nextSquare.index(board))
      }.toStream
  }

  def toursFrom(initial: Stream[Position]): Stream[Position] = {
    if (initial.isEmpty) Stream empty
    else {
      val newTours = for {
        position <- initial
        next <- toursStream(position)
      } yield next
      initial ++ toursFrom(newTours)
    }
  }

  lazy val toursFromStart: Stream[Position] = toursFrom(List(Position(startingSquare, Nil, Set(startingSquare.index(board)))).toStream)

  lazy val finishedTours: Stream[Position] = toursFromStart.filter(position => position.completes(board))
}

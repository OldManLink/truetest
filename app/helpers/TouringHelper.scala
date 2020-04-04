package helpers

import models.{Board, Square, Tour, TourRequest}

case class TouringHelper(tourRequest: TourRequest) {

  def getTours: Seq[Tour] = finishedTours match {
    case Stream() => Nil
    case stream => (stream take tourRequest.max).toList.zipWithIndex.map { case (pos, id) =>
      pos.toTour(id, startingSquare)
    }
  }

  def toursStream(position: Position): Stream[Position] = {
    position.square.possibleSteps.filterNot(stepUnderTest =>
      position.visited.contains(stepUnderTest.square.boardIndex)).sorted(UnvisitedOrdering(position.visited))
      .map { nextStep =>
        val nextSquare = nextStep.square
        Position(nextSquare, nextStep.move :: position.history, nextSquare.boardIndex :: position.visited )
      }.toStream
  }

  case class UnvisitedOrdering(visited: Seq[Int]) extends Ordering[Step] {
    @Override
    override def compare(x: Step, y: Step): Int = x.unVisitedSize(visited) compare y.unVisitedSize(visited)
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

  private def getBoard(request: TourRequest): Board = {
    ObjectFactory.getOptimisedBoard(tourRequest)
  }

  private def getStartingSquare(request: TourRequest): Square = {
    board.getSquare(request.startingSquare.row, request.startingSquare.column).get
  }

  private lazy val board = getBoard(tourRequest)

  private lazy val startingSquare = getStartingSquare(tourRequest)

  private lazy val toursFromStart: Stream[Position] = toursFrom(List(Position(startingSquare, Nil, List(startingSquare.boardIndex))).toStream)

  private lazy val finishedTours: Stream[Position] = toursFromStart.filter(position => position.completes(board))
}

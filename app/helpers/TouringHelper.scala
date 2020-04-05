package helpers

import models.{Tour, TourChunkRequest}

import scala.Function.tupled

case class TouringHelper(chunkRequest: TourChunkRequest) {

  def getTours: Seq[Tour] = finishedTours match {
    case Stream() => Nil
    case stream => (stream take chunkRequest.max).toList.zipWithIndex.map { case (pos, id) =>
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
    if (initial.isEmpty) Stream.empty
    else {
      val newTours = for {
        position <- initial
        next <- toursStream(position)
      } yield next
      initial ++ toursFrom(newTours)
    }
  }

  private lazy val board = ObjectFactory.getOptimisedBoard(chunkRequest)

  private lazy val startingSquare = tupled(board.getSquare _)(chunkRequest.startSquare).get

  private lazy val toursFromStart: Stream[Position] = toursFrom(List(Position(startingSquare, Nil, List(startingSquare.boardIndex))).toStream)

  private lazy val toursWithGoodEnd: Stream[Position] = toursFromStart.filter(pos => chunkRequest.chunkIDs.forall(!_.forbids(pos.coords)))

  private lazy val finishedTours: Stream[Position] = toursWithGoodEnd.filter(_.completes(board))
}

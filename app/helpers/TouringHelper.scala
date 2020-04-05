package helpers

import models.{Square, TourChunkRequest}

case class TouringHelper(chunkRequest: TourChunkRequest) {

  def getStartSquare: Square = startSquare

  def getTours: Seq[Position] = finishedTours match {
    case Stream() => Nil
    case stream => stream take chunkRequest.max
  }

  def toursStream(position: Position): Stream[Position] = {
    position.nextSteps.map { nextStep =>
      Position(nextStep.square, nextStep.move :: position.history, nextStep.square.boardIndex :: position.visited)
    }.toStream
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

  def isGoodEnd(pos: Position): Boolean = {
    chunkRequest.chunkIDs.size match {
      case 4 => true
      case _ => chunkRequest.chunkIDs.take(2) match {
        case first :: rest => !first.forbids(pos.coords, rest)
        case _ => throw new NoSuchElementException("Impossible situation, but this keeps the compiler free from warnings.")
      }
    }
  }

  private lazy val board = ObjectFactory.getOptimisedBoard(chunkRequest)

  private lazy val startSquare = board.getSquare(chunkRequest.startSquare.asTuple).get

  private lazy val startPosition = Position(startSquare, List(chunkRequest.previousMove), List(startSquare.boardIndex))

  private lazy val toursFromStart: Stream[Position] = toursFrom(List(startPosition).toStream)

  private lazy val toursWithGoodEnd: Stream[Position] = toursFromStart.filter(isGoodEnd)

  private lazy val finishedTours: Stream[Position] = toursWithGoodEnd.filter(_.completes(board))
}

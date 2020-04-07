package helpers

import models.{Square, Tour, TourChunkRequest, TourRequest}

trait TourSearchStrategy {

  def getTours: Seq[Tour]

  def getFirstTourChunkRequest(tourRequest: TourRequest): TourChunkRequest = TourChunkRequest(tourRequest, Move.O, None)

  def toTours(chunk: Seq[Position], startSquare: Square): Seq[Tour] = chunk.toList.zipWithIndex
    .map { case (pos, id) => pos.toTour(id, startSquare) }
}

case class NaiveStrategy(tourRequest: TourRequest) extends TourSearchStrategy {

  @Override
  override def getTours: Seq[Tour] = {
    val touringHelper = TouringHelper(getFirstTourChunkRequest(tourRequest))
    toTours(touringHelper.getTours, touringHelper.getStartSquare)
  }
}

case class DivideAndConquerStrategy(tourRequest: TourRequest) extends TourSearchStrategy {

  @Override
  override def getTours: Seq[Tour] = {
    val firstChunkRequest = getFirstTourChunkRequest(tourRequest)
    val firstTouringHelper = TouringHelper(firstChunkRequest)
    toTours({
      for {
        firstChunk <- firstTouringHelper.getTours
        secondChunkRequest <- getNextTourChunkRequest(firstChunk, firstChunkRequest)
        secondChunk <- TouringHelper(secondChunkRequest).getTours.headOption
        thirdChunkRequest <- getNextTourChunkRequest(secondChunk, secondChunkRequest)
        thirdChunk <- TouringHelper(thirdChunkRequest).getTours.headOption
        fourthChunkRequest <- getNextTourChunkRequest(thirdChunk, thirdChunkRequest)
        fourthChunk <- TouringHelper(fourthChunkRequest).getTours.headOption
        tour: Position <- assembleChunks(fourthChunkRequest, firstChunk, secondChunk, thirdChunk, fourthChunk)
      } yield tour
    }.take(tourRequest.max), startSquare)
  }

  def getNextStep(previousChunk: Position, previousRequest: TourChunkRequest): Step = {

    // Get current position relative to the full board
    val actualCoords = previousRequest.chunkID.toActual(previousChunk.square.asTuple)
    val currentSquare = fullBoard.getSquare(actualCoords).get

    // Get the visited squares from all the previous chunks
    val totalVisited = previousRequest.chunkIDs.flatMap(_.toActual(previousChunk.visited))

    // Get best possible escape direction, using the standard algorithm as the rest of the search
    previousChunk.copy(square = currentSquare, visited = totalVisited).nextSteps.head
  }

  def getNextTourChunkRequest(previousChunk: Position, previousRequest: TourChunkRequest): Option[TourChunkRequest] = {

    val nextStep = getNextStep(previousChunk, previousRequest)
    Some(TourChunkRequest(TourRequest((10, 10), nextStep.square.asTuple), nextStep.move, Some(previousRequest)))
  }

  def assembleChunks(lastChunkRequest: TourChunkRequest, first: Position, second: Position, third: Position, fourth: Position): Option[Position] = {
    val lastSquare: Square = fullBoard.getSquare(lastChunkRequest.chunkID.toActual(fourth.square.asTuple)).get
    Some(Position(lastSquare, fourth.history ::: third.history ::: second.history ::: first.history, Nil))
  }

  private lazy val fullBoard = ObjectFactory.boardFactory(tourRequest).build.getOptimisedBoard
  private lazy val startSquare = fullBoard.getSquare(tourRequest.startSquare.asTuple).get

}

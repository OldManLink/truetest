package helpers

import models.{Tour, TourChunkRequest, TourRequest}

trait TourSearchStrategy {

  def getTours: Seq[Tour]

  def getFirstTourChunkRequest(tourRequest: TourRequest): TourChunkRequest = TourChunkRequest(tourRequest)

}

case class NaiveStrategy(tourRequest: TourRequest) extends TourSearchStrategy {

  @Override
  override def getTours: Seq[Tour] = TouringHelper(getFirstTourChunkRequest(tourRequest)).getTours
}

case class DivideAndConquerStrategy(tourRequest: TourRequest) extends TourSearchStrategy {

  @Override
  override def getTours: Seq[Tour] = {
    val firstChunkRequest = getFirstTourChunkRequest(tourRequest)
    for {
      firstChunk <- TouringHelper(firstChunkRequest).getTours
      secondChunkRequest <- getNextTourChunkRequest(firstChunk, firstChunkRequest)
      secondChunk <- TouringHelper(secondChunkRequest).getTours.headOption
      thirdChunkRequest <- getNextTourChunkRequest(secondChunk, secondChunkRequest)
      thirdChunk <- TouringHelper(thirdChunkRequest).getTours.headOption
      fourthChunkRequest <- getNextTourChunkRequest(thirdChunk, thirdChunkRequest)
      fourthChunk <- TouringHelper(fourthChunkRequest).getTours.headOption
      tour: Tour <- assembleChunks(firstChunk, secondChunk, thirdChunk, fourthChunk)
    } yield tour
  }.take (tourRequest.max)


  def getNextCoords(lastCoords: (Int, Int), lastCIDs: List[ChunkID]): (Int, Int) = {

    // Get The current quadrant (head of lastCIDs)
    val currentQuadrant = lastCIDs.head

    // Get actual coords on the 10 x 10 byy asking the CID to add to lastCoords
//  val actualCoords = currentQuadrant.toActual(lastCoords)

    // Get possible escape directions, checking the CIDs

    // Pick the first direction and apply it to actual coords

    // Done!
    ???
  }

  def getNextTourChunkRequest(previousChunk: Tour, previousRequest: TourChunkRequest): Option[TourChunkRequest] = {

    val lastCords = previousChunk.endCoords
    val lastCIDs = previousRequest.chunkIDs
    val nextCoords = getNextCoords(lastCords, lastCIDs)
    Some(TourChunkRequest(TourRequest((10,10), nextCoords, previousRequest.max)))
  }

  def assembleChunks(firstChunk: Tour, secondChunk: Tour, thirdChunk: Tour, fourthChunk: Tour): Option[Tour] = Some(
    firstChunk.copy(
      moves = firstChunk.moves ::: secondChunk.moves ::: thirdChunk.moves ::: fourthChunk.moves,
      endCoords = fourthChunk.endCoords
    )
  )
}

package models

import helpers.{ChunkID, Move}
import helpers.ChunkID._

case class TourChunkRequest(tourRequest: TourRequest, previousMove: Move, previousRequest: Option[TourChunkRequest]) {

  val chunkID: ChunkID = ChunkID(tourRequest)
  val chunkIDs: List[ChunkID] = previousRequest.map(pr => chunkID :: pr.chunkIDs).getOrElse(List(chunkID))
  val startSquare: SquareDescription = chunkID.startSquare(tourRequest)
  val board: BoardDescription = chunkID.getBoard(tourRequest)
  val max: Int = tourRequest.max
}

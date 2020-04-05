package models

import helpers.ChunkID
import helpers.ChunkID._

case class TourChunkRequest(tourRequest: TourRequest, previousRequest: Option[TourChunkRequest] = None) {

  val chunkIDs: List[ChunkID] = previousRequest.map(pr => ChunkID(tourRequest) :: pr.chunkIDs).getOrElse(List(ChunkID(tourRequest)))
  val requestBoard: BoardDescription = tourRequest.board
  val cornerOffsets: (Int, Int) = (requestBoard.rows / 2, requestBoard.columns / 2)
  val requestSquare: SquareDescription = tourRequest.startingSquare
  val startSquare: (Int, Int) = (startingSquare.row, startingSquare.column)

  def board: BoardDescription = chunkIDs head match {
    case NullChunk => throw new IllegalStateException("Invalid chunk - programming error!")
    case Single => requestBoard
    case _ => requestBoard copy(rows = requestBoard.rows / 2, columns = requestBoard.columns / 2) // One quarter size
  }

  def startingSquare: SquareDescription = chunkIDs head match {
    case Single => requestSquare
    case BottomLeft => requestSquare
    case BottomRight => requestSquare.copy(column = requestSquare.column - cornerOffsets._2)
    case TopRight => requestSquare.copy(row = requestSquare.row - cornerOffsets._1, column = requestSquare.column - cornerOffsets._2)
    case TopLeft => requestSquare.copy(row = requestSquare.row - cornerOffsets._1)
    case _ => throw new IllegalStateException(s"Invalid chunk - $chunkIDs - programming error!")
  }

  def max: Int = chunkIDs head match {
    case NullChunk => throw new IllegalStateException("Invalid chunk - programming error!")
    case Single => tourRequest.max
    case _ => 10
  }
}

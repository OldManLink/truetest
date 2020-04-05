package helpers

import models.{BoardDescription, SquareDescription, TourRequest}
import utils.Extensions._

sealed abstract class ChunkID(val code: String, val offsets: (Int, Int), val factor: Int,
                              val forbiddenOrigin: (Int, Int), val forbiddenCorner: (Int, Int)) extends ChunkID.Value {

  def toActual(lastCoords: (Int, Int)):(Int, Int) = lastCoords + offsets

  def toActual(boardIndices: List[Int]): List[Int] = boardIndices.map(boardIndexToTuple(5)).map(toActual).map(tupleToBoardIndex(10))

  def boardIndexToTuple(size: Int)(index: Int): (Int, Int) = (index / size, index % size)

  def tupleToBoardIndex(size: Int)(tuple: (Int, Int)): Int = tuple._1 * size + tuple._2

  def forbids(coords: (Int, Int), previous: List[ChunkID]): Boolean = {
    (forbiddenOrigin :: previous.map(_.forbiddenOrigin)).min
      .corner((forbiddenCorner :: previous.map(_.forbiddenCorner)).max)
      .contains(coords)
  }

  def getBoard(request: TourRequest): BoardDescription = {
    BoardDescription(request.board.asTuple / factor)
  }

  def startSquare(request: TourRequest): SquareDescription = {
    SquareDescription(request.startSquare.asTuple - offsets)
  }
}

object ChunkID extends Enum[ChunkID] {

  def apply(tourRequest: TourRequest): ChunkID = {
    tourRequest.board match {
      case BoardDescription(5, 5) => Single
      case BoardDescription(10,10) => startingQuadrant(tourRequest)
      case _ => Single
    }
  }

  def startingQuadrant(request: TourRequest): ChunkID = {
    request.board.asTuple / 2 > request.startSquare.asTuple match {
      case (true, true) => BottomLeft
      case (true, false) => BottomRight
      case (false, true) => TopLeft
      case (false, false) => TopRight
    }
  }

  val NullChunk:   ChunkID = new ChunkID("Null",        (0, 0), 1, (0, 0), (0, 0)){}
  val Single:      ChunkID = new ChunkID("Single",      (0, 0), 1, (-1, -1), (-1, -1)){}
  val TopLeft:     ChunkID = new ChunkID("TopLeft",     (5, 0), 2, (3, 0), (4, 1)){}
  val TopRight:    ChunkID = new ChunkID("TopRight",    (5, 5), 2, (3, 3), (4, 4)){}
  val BottomLeft:  ChunkID = new ChunkID("BottomLeft",  (0, 0), 2, (0, 0), (1, 1)){}
  val BottomRight: ChunkID = new ChunkID("BottomRight", (0, 5), 2, (0, 3), (1, 4)){}
}
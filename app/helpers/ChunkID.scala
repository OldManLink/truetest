package helpers

import models.{BoardDescription, TourRequest}

sealed abstract class ChunkID(val code: String, val forbiddenEnd: Seq[(Int,Int)]) extends ChunkID.Value {

  def forbids(coords:(Int, Int)): Boolean = forbiddenEnd.contains(coords)
}

object ChunkID extends Enum[ChunkID] {

  def apply(tourRequest: TourRequest): ChunkID = {
    tourRequest.board match {
      case BoardDescription(5, 5) => Single
      case BoardDescription(10,10) => startingQuarter(tourRequest)
      case _ => Single
    }
  }

  def startingQuarter(request: TourRequest): ChunkID = {
    // Currently only supports chunks taken from 10 x 10, i.e. 5 x 5 quarters
    ((request.board.rows / 2 - request.startingSquare.row) > 0,
      (request.board.columns / 2 - request.startingSquare.column) > 0
    ) match {
      case (true, true) => BottomLeft
      case (true, false) => BottomRight
      case (false, true) => TopLeft
      case (false, false) => TopRight
    }
  }

  val NullChunk:   ChunkID = new ChunkID("Null",                                 Nil){}
  val Single:      ChunkID = new ChunkID("Single",                               Nil){}
  val TopLeft:     ChunkID = new ChunkID("TopLeft",     Seq((4,0),(4,1),(3,0),(3,1))){}
  val TopRight:    ChunkID = new ChunkID("TopRight",    Seq((4,4),(4,3),(3,4),(3,3))){}
  val BottomLeft:  ChunkID = new ChunkID("BottomLeft",  Seq((0,0),(0,1),(1,0),(1,1))){}
  val BottomRight: ChunkID = new ChunkID("BottomRight", Seq((0,4),(0,3),(1,4),(1,3))){}
}
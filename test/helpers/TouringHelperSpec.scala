package helpers

import helpers.ChunkID.{BottomLeft, BottomRight, TopLeft, TopRight}
import helpers.Move.{E, N, NE, NW, S, SE, SW, W}
import models.{Tour, TourChunkRequest, TourRequest}
import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TouringHelperSpec extends Specification {

  val firstRequest: TourChunkRequest = TourChunkRequest(TourRequest((10, 10), (1, 2), 5))
  val firstChunk = List(
    Tour(0, 1, 2, List(N, SE, SW, NW, E, SW, N, E, S, NW, NW, E, S, W, NE, SE, N, W, S, NE, W, S, E, NW), (2, 1)),
    Tour(1, 1, 2, List(N, SE, SW, NW, E, SW, N, E, S, NW, NW, E, SW, SE, W, N, E, SW, N, E, S, NW, SW, E), (1, 3)),
    Tour(2, 1, 2, List(N, SE, SW, NW, E, SW, N, E, S, NW, NW, S, NE, SE, N, W, S, NE, W, S, E, NW, NE, S), (1, 3)),
    Tour(3, 1, 2, List(N, SE, SW, NW, E, SW, N, E, S, NW, NW, S, E, N, SW, SE, W, N, E, SW, N, E, S, NW), (3, 2)),
    Tour(4, 1, 2, List(N, SE, SW, NW, E, SW, N, SE, N, SW, SE, W, N, E, SW, N, E, S, NW, SW, N, SE, SE, N), (3, 4))
  )

  val secondRequest: TourChunkRequest = TourChunkRequest(TourRequest((10, 10), (5, 1), 5), Some(firstRequest))
  val secondChunk = List(
    Tour(0, 0, 1, List(N, E, S, NW, NE, S, NW, S, NW, E, NW, S, NE, S, W, N, SE, N, SE, W, NE, W, S, E), (1, 3)),
    Tour(1, 0, 1, List(N, E, S, NW, NE, W, SE, W, NE, S, NW, S, E, N, SW, E, NW, S, NE, W, NE, W, S, E), (1, 3)),
    Tour(2, 0, 1, List(N, E, S, NW, NE, W, SE, W, SE, N, SE, W, NE, S, W, N, SE, N, SE, W, NE, W, S, E), (1, 3)),
    Tour(3, 0, 1, List(N, E, S, NW, SW, N, SE, N, SE, W, SE, N, SW, E, N, W, SE, W, SE, N, SW, N, E, S), (1, 3)),
    Tour(4, 0, 1, List(N, E, S, NW, SW, N, SE, N, SW, E, NW, E, S, W, NE, S, NW, E, SW, N, SW, N, E, S), (1, 3))
  )

  val thirdRequest: TourChunkRequest = TourChunkRequest(TourRequest((10, 10), (6, 6), 5), Some(secondRequest))
  val thirdChunk = List(
    Tour(0, 1, 1, List(N, E, S, NW, S, NE, W, NE, S, W, N, SE, SW, E, N, W, SE, N, SW, E, SW, N, E, S), (0, 4)),
    Tour(1, 1, 1, List(N, E, S, NW, S, NE, W, NE, W, S, E, NW, E, S, W, NE, W, NE, S, NW, E, S, W, NE), (2, 2)),
    Tour(2, 1, 1, List(N, E, S, NW, S, NE, W, NE, W, S, E, NW, E, SW, N, SW, E, SW, E, NW, SW, N, E, S), (0, 3)),
    Tour(3, 1, 1, List(N, E, S, NW, S, NE, W, NE, W, S, E, NW, E, SW, NW, E, S, W, NE, SE, W, NE, W, NE), (4, 2)),
    Tour(4, 1, 1, List(N, E, S, NW, S, NE, W, NE, W, S, E, NW, S, NE, W, NE, S, NE, S, NW, SW, N, E, S), (0, 3))
  )

  val fourthRequest: TourChunkRequest = TourChunkRequest(TourRequest((10, 10), (2, 9), 5), Some(thirdRequest))
  val fourthChunk = List(
    Tour(0, 2, 4, List(SW, N, SE, N, W, S, NE, S, NW, NE, W, S, E, NW, E, S, W, NE, W, NE, S, NW, S, NE), (2, 2)),
    Tour(1, 2, 4, List(SW, N, SE, N, W, S, NE, S, NW, NE, W, S, E, NW, E, SW, NW, S, NE, SE, W, NE, W, NE), (4, 2)),
    Tour(2, 2, 4, List(SW, N, SE, N, W, S, NE, S, W, N, SE, N, SW, E, SW, N, E, S, NW, NW, S, E, N, SW), (2, 1)),
    Tour(3, 2, 4, List(SW, N, SE, N, W, S, NE, W, SE, N, SW, E, SW, E, N, W, SE, W, N, E, SW, SE, W, NE), (2, 2)),
    Tour(4, 2, 4, List(SW, N, SE, N, W, S, NE, W, SE, NE, S, NW, SW, E, NW, NE, W, S, E, NW, S, NE, W, NE), (4, 2))
  )

  "TouringHelper" should {
    "get the bottom left tours for a 10 x 10 board" in {
      val request = firstRequest
      request.chunkIDs mustEqual List(BottomLeft)
      request.startSquare mustEqual(1, 2)
      request.max mustEqual 10
      val touringHelper = TouringHelper(request)

      val tours = touringHelper.getTours

      tours forall (t => !List(BottomLeft).forall(_.forbids(t.endCoords)))
      tours.take(5) must beEqualTo(firstChunk)
    }

    "get the top left tours for a 10 x 10 board, following on from the previous first tour" in {
      val request = secondRequest
      request.chunkIDs mustEqual List(TopLeft, BottomLeft)
      request.startSquare mustEqual(0, 1)
      request.max mustEqual 10
      val touringHelper = TouringHelper(request)

      val tours = touringHelper.getTours

      tours forall (t => !List(TopLeft, BottomLeft).forall(_.forbids(t.endCoords)))

      tours.take(5) must beEqualTo(secondChunk)
    }

    "get the top right tours for a 10 x 10 board, following on from the previous first tour" in {
      val request = thirdRequest
      request.chunkIDs mustEqual List(TopRight, TopLeft, BottomLeft)
      request.startSquare mustEqual(1, 1)
      request.max mustEqual 10
      val touringHelper = TouringHelper(request)

      val tours = touringHelper.getTours

      tours forall (t => !List(TopRight, TopLeft, BottomLeft).forall(_.forbids(t.endCoords)))

      tours.take(5) must beEqualTo(thirdChunk)
    }

    "get the bottom right tours for a 10 x 10 board, following on from the previous first tour" in {
      val request = fourthRequest
      request.chunkIDs mustEqual List(BottomRight, TopRight, TopLeft, BottomLeft)
      request.startSquare mustEqual(2, 4)
      request.max mustEqual 10
      val touringHelper = TouringHelper(request)

      val tours = touringHelper.getTours


      tours.take(5) must beEqualTo(fourthChunk)
    }

    "get the complete tours for a 10 x 10 board, by using the Divide and Conquer strategy" in {
      val request = firstRequest.tourRequest
      val searchStrategy = DivideAndConquerStrategy(request)
      searchStrategy.getNextTourChunkRequest(firstChunk.head, firstRequest) mustEqual (secondRequest)
      searchStrategy.getNextTourChunkRequest(secondChunk.head, secondRequest) mustEqual (thirdRequest)
      searchStrategy.getNextTourChunkRequest(thirdChunk.head, thirdRequest) mustEqual (fourthRequest)
      val tour = searchStrategy.assembleChunks(firstChunk.head, secondChunk.head, thirdChunk.head, fourthChunk.head)

      tour must beEqualTo(
        Tour(0, 1, 2,
          List(
            N, SE, SW, NW, E, SW, N, E, S, NW, NW, E, S, W, NE, SE, N, W, S, NE, W, S, E, NW,
            N, E, S, NW, NE, S, NW, S, NW, E, NW, S, NE, S, W, N, SE, N, SE, W, NE, W, S, E,
            N, E, S, NW, S, NE, W, NE, S, W, N, SE, SW, E, N, W, SE, N, SW, E, SW, N, E, S,
            SW, N, SE, N, W, S, NE, S, NW, NE, W, S, E, NW, E, S, W, NE, W, NE, S, NW, S, NE
          ), (2, 2)
        )
      )
    }.pendingUntilFixed("W.I.P.")
  }

}

package helpers

import helpers.ChunkID.{BottomLeft, BottomRight, TopLeft, TopRight}
import helpers.Move.{E, N, NE, NW, O, S, SE, SW, W}
import models.{SquareDescription, Tour, TourChunkRequest, TourRequest}
import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import org.specs2.specification.Scope
import services.StrategyService

@RunWith(classOf[JUnitRunner])
class TouringHelperSpec extends Specification {

  val firstRequest: TourChunkRequest = TourChunkRequest(TourRequest((10, 10), (1, 2), 5), Move.O, None)
  val firstChunk = List(
    Tour(0, 1, 2, List(O, N, SE, SW, NW, E, SW, N, E, S, NW, NW, E, S, W, NE, SE, N, W, S, NE, W, S, E, NW), (2, 1)),
    Tour(1, 1, 2, List(O, N, SE, SW, NW, E, SW, N, E, S, NW, NW, E, SW, SE, W, N, E, SW, N, E, S, NW, SW, E), (1, 3)),
    Tour(2, 1, 2, List(O, N, SE, SW, NW, E, SW, N, E, S, NW, NW, S, NE, SE, N, W, S, NE, W, S, E, NW, NE, S), (1, 3)),
    Tour(3, 1, 2, List(O, N, SE, SW, NW, E, SW, N, E, S, NW, NW, S, E, N, SW, SE, W, N, E, SW, N, E, S, NW), (3, 2)),
    Tour(4, 1, 2, List(O, N, SE, SW, NW, E, SW, N, SE, N, SW, SE, W, N, E, SW, N, E, S, NW, SW, N, SE, SE, N), (3, 4))
  )

  val secondRequest: TourChunkRequest = TourChunkRequest(TourRequest((10, 10), (5, 1), 5), Move.N, Some(firstRequest))
  val secondChunk = List(
    Tour(0, 0, 1, List(N, N, E, S, NW, NE, S, NW, S, NW, E, NW, S, NE, S, W, N, SE, N, SE, W, NE, W, S, E), (1, 3)),
    Tour(1, 0, 1, List(N, N, E, S, NW, NE, W, SE, W, NE, S, NW, S, E, N, SW, E, NW, S, NE, W, NE, W, S, E), (1, 3)),
    Tour(2, 0, 1, List(N, N, E, S, NW, NE, W, SE, W, SE, N, SE, W, NE, S, W, N, SE, N, SE, W, NE, W, S, E), (1, 3)),
    Tour(3, 0, 1, List(N, N, E, S, NW, SW, N, SE, N, SE, W, SE, N, SW, E, N, W, SE, W, SE, N, SW, N, E, S), (1, 3)),
    Tour(4, 0, 1, List(N, N, E, S, NW, SW, N, SE, N, SW, E, NW, E, S, W, NE, S, NW, E, SW, N, SW, N, E, S), (1, 3))
  )

  val thirdRequest: TourChunkRequest = TourChunkRequest(TourRequest((10, 10), (6, 6), 5), Move.E, Some(secondRequest))
  val thirdChunk = List(
    Tour(0, 1, 1, List(E, N, E, S, NW, S, NE, W, NE, S, W, N, SE, SE, N, W, S, NE, W, NE, S, NW, E, S, W), (0, 0)),
    Tour(1, 1, 1, List(E, N, E, S, NW, S, NE, W, NE, S, W, N, SE, SW, E, N, W, SE, N, SW, E, SW, N, E, S), (0, 4)),
    Tour(2, 1, 1, List(E, N, E, S, NW, S, NE, W, NE, S, NW, E, S, W, NE, W, NE, S, NW, E, S, W, NE, NW, S), (1, 0)),
    Tour(3, 1, 1, List(E, N, E, S, NW, S, NE, W, NE, W, S, E, NW, E, S, W, NE, W, NE, S, NW, E, S, W, NE), (2, 2)),
    Tour(4, 1, 1, List(E, N, E, S, NW, S, NE, W, NE, W, S, E, NW, E, S, NW, SW, E, N, W, SE, N, SW, E, SW), (0, 1))
  )

  val fourthRequest: TourChunkRequest = TourChunkRequest(TourRequest((10, 10), (2, 5), 5), Move.S, Some(thirdRequest))
  val fourthChunk = List(
    Tour(0, 2, 0, List(S, NE, SE, SW, N, SE, N, W, SE, SW, N, SE, W, N, E, SW, SE, W, NE, SE, N, SW, NW, E, SW), (1, 1)),
    Tour(1, 2, 0, List(S, NE, SE, SW, N, SE, N, W, SE, SW, E, NW, SW, N, SE, NE, W, SE, W, N, E, SW, SE, N, SW), (1, 1)),
    Tour(2, 2, 0, List(S, NE, SE, SW, N, SE, N, W, SE, SW, E, NW, SW, E, NW, NE, W, S, E, NW, E, SW, NW, E, SW), (1, 1)),
    Tour(3, 2, 0, List(S, NE, SE, SW, N, SE, N, W, S, NE, S, NW, NE, W, S, E, NW, E, SW, NW, S, NE, SE, W, NE), (2, 3)),
    Tour(4, 2, 0, List(S, NE, SE, SW, N, SE, N, W, S, NE, W, SE, NE, S, NW, SW, E, NW, NE, W, S, E, NW, S, NE), (2, 3))
  )

  "TouringHelper" should {
    "get the bottom left tours for a 10 x 10 board" in {
      val request = firstRequest
      request.chunkIDs mustEqual List(BottomLeft)
      request.startSquare mustEqual SquareDescription(1, 2)
      request.max mustEqual 10
      val touringHelper = TouringHelper(request)

      val tours = touringHelper.getTours

      tours forall (t => !List(BottomLeft).forall(_.forbids(t.square.asTuple, Nil)))
      tours.take(5).map(_.history.reverse) must beEqualTo(firstChunk.map(_.moves))
    }

    "get the top left tours for a 10 x 10 board, following on from the previous first tour" in {
      val request = secondRequest
      request.chunkIDs mustEqual List(TopLeft, BottomLeft)
      request.startSquare mustEqual SquareDescription(0, 1)
      request.max mustEqual 10
      val touringHelper = TouringHelper(request)

      val tours = touringHelper.getTours

      tours forall (t => !List(TopLeft, BottomLeft).forall(_.forbids(t.square.asTuple, List(BottomLeft))))
      tours.take(5).map(_.history.reverse) must beEqualTo(secondChunk.map(_.moves))
    }

    "get the top right tours for a 10 x 10 board, following on from the previous first tour" in {
      val request = thirdRequest
      request.chunkIDs mustEqual List(TopRight, TopLeft, BottomLeft)
      request.startSquare mustEqual SquareDescription(1, 1)
      request.max mustEqual 10
      val touringHelper = TouringHelper(request)

      val tours = touringHelper.getTours

      tours forall (t => !List(TopRight, TopLeft, BottomLeft).forall(_.forbids(t.square.asTuple, List(TopLeft))))
      tours.take(5).map(_.history.reverse) must beEqualTo(thirdChunk.map(_.moves))
    }

    "get the bottom right tours for a 10 x 10 board, following on from the previous first tour" in {
      val request = fourthRequest
      request.chunkIDs mustEqual List(BottomRight, TopRight, TopLeft, BottomLeft)
      request.startSquare mustEqual SquareDescription(2, 0)
      request.max mustEqual 10
      val touringHelper = TouringHelper(request)

      val tours = touringHelper.getTours

      tours.take(5).map(_.history.reverse) must beEqualTo(fourthChunk.map(_.moves))
    }

    "get the complete tours for a 10 x 10 board, by using the Divide and Conquer strategy" in new Scope with StrategyService {
      val request = firstRequest.tourRequest.copy(max = 1)
      val searchStrategy = getStrategy(request)
      searchStrategy.getClass mustEqual (classOf[DivideAndConquerStrategy])
      val tour = searchStrategy.getTours.head

      tour must beEqualTo(
        Tour(0, 1, 2,
          List(
            N, SE, SW, NW, E, SW, N, E, S, NW, NW, E, S, W, NE, SE, N, W, S, NE, W, S, E, NW,
            N, N, E, S, NW, NE, S, NW, S, NW, E, NW, S, NE, S, W, N, SE, N, SE, W, NE, W, S, E,
            NE, E, S, W, NE, NE, S, W, N, SE, W, NE, S, NE, S, W, N, SE, N, W, S, NE, S, NE, W,
            S, E, S, W, NE, S, W, N, SE, N, SE, W, NE, S, W, N, SE, SE, N, W, S, NE, W, SE, N),
          (3, 7))

      )
    }
  }

}

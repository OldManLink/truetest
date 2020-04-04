package helpers

import helpers.Move.{E, N, NE, NW, S, SE, SW, W}
import models.{Tour, TourRequest}
import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TouringHelperSpec extends Specification {

  val expectedTours: List[Tour] = List(
    Tour(0, 1, 2, List(N, SE, SW, NW, E, SW, N, E, S, NW, NW, E, S, W, NE, SE, N, W, S, NE, W, S, E, NW)),
    Tour(1, 1, 2, List(N, SE, SW, NW, E, SW, N, E, S, NW, NW, E, SW, SE, W, N, E, SW, N, E, S, NW, SW, E)),
    Tour(2, 1, 2, List(N, SE, SW, NW, E, SW, N, E, S, NW, NW, S, NE, SE, N, W, S, NE, W, S, E, NW, NE, S)),
    Tour(3, 1, 2, List(N, SE, SW, NW, E, SW, N, E, S, NW, NW, S, E, N, SW, SE, W, N, E, SW, N, E, S, NW)),
    Tour(4, 1, 2, List(N, SE, SW, NW, E, SW, N, SE, N, SW, SE, W, N, E, SW, N, E, S, NW, SW, N, SE, SE, N))
  )

  "TouringHelper" should {

    "get the tours for a board" in {
      val touringHelper = TouringHelper(TourRequest((5, 5), (1, 2), 5))

      val tours = touringHelper.getTours

      tours.size must beEqualTo(5)
      tours must beEqualTo(expectedTours)
    }
  }
}

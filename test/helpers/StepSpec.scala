package helpers

import helpers.Move._
import helpers.ObjectFactory.SquareResolver
import models.Square.ORIGIN
import models.TourRequest
import org.junit.runner.RunWith
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class StepSpec extends Specification with Mockito {

  "Step" should {

    "resolve its square correctly" in {
      val resolver = mock[SquareResolver]
      resolver.resolve returns ORIGIN

      Step(NW, resolver).square must beEqualTo(ORIGIN)
    }

    "report unvisited steps count" in {
      val board = ObjectFactory.getTestBoard(TourRequest((5, 5)))

      /**
        * Uncomment this line to see the board.
        * println(s"\n$board")
        *
        * Here is the selected test square:
        * {0, 4, index: 4 steps: [{N, {3, 4}},{W, {0, 1}},{NW, {2, 2}}]}
        *
        * Its last possible step points to this square:
        * {2, 2, index: 12 steps: [{NE, {4, 4}},{SE, {0, 4}},{SW, {0, 0}},{NW, {4, 0}}]},
        *
        * Its possible steps have squares with board index as follows:
        *                                24           4            0            20
        *
        * The expectation is that the first two steps will be filtered out, leaving 2 unvisited
        */

      board.getSquare(0, 4).get.possibleSteps.last.unVisitedSize(Seq(4, 13, 19, 24)) mustEqual 2
    }
  }
}

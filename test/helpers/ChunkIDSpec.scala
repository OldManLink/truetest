package helpers

import helpers.ChunkID._
import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ChunkIDSpec extends Specification {

  "ChunkID" should {

    "return the actual coordinates of a tuple" in {
      Single.toActual((4, 2)) mustEqual(4, 2)
      BottomLeft.toActual((4, 2)) mustEqual(4, 2)
      TopLeft.toActual((4, 2)) mustEqual(9, 2)
      TopRight.toActual((4, 2)) mustEqual(9, 7)
      BottomRight.toActual((4, 2)) mustEqual(4, 7)
    }

    "forbid the right tuples when requested" in {
      Single.forbids((0, 0), Nil) must beFalse

      BottomLeft.forbids((0, 0), Nil) must beTrue
      TopLeft.forbids((0, 0), Nil) must beFalse
      TopRight.forbids((0, 0), Nil) must beFalse
      BottomRight.forbids((0, 0), Nil) must beFalse

      BottomLeft.forbids((2, 1), Nil) must beFalse
      BottomLeft.forbids((2, 1), List(TopLeft)) must beTrue
    }

    "forbid the right tuples in combination" in {
      BottomLeft.forbids((2, 1), Nil) must beFalse
      TopLeft.forbids((2, 1), Nil) must beFalse
      TopLeft.forbids((2, 1), List(BottomLeft)) must beTrue
      BottomLeft.forbids((2, 1), List(TopLeft)) must beTrue

      TopLeft.forbids((4, 2), Nil) must beFalse
      TopRight.forbids((4, 2), Nil) must beFalse
      TopRight.forbids((4, 2), List(TopLeft)) must beTrue
      TopLeft.forbids((4, 2), List(TopRight)) must beTrue

      TopRight.forbids((2, 4), Nil) must beFalse
      BottomRight.forbids((2, 4), Nil) must beFalse
      BottomRight.forbids((2, 4), List(TopRight)) must beTrue
      TopRight.forbids((2, 4), List(BottomRight)) must beTrue

      BottomRight.forbids((1, 2), Nil) must beFalse
      BottomLeft.forbids((1, 2), Nil) must beFalse
      BottomLeft.forbids((1, 2), List(BottomRight)) must beTrue
      BottomRight.forbids((1, 2), List(BottomLeft)) must beTrue
    }
  }
}

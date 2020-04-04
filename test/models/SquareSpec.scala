package models

import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import utils.SquareTesting

@RunWith(classOf[JUnitRunner])
class SquareSpec extends Specification with SquareTesting {

  "Square" should {

    "know when it is blocked" in {
      Square(4, 2, 42, Nil).isBlocked must beTrue
    }
  }
}

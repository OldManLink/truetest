package utils

import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import Extensions._

@RunWith(classOf[JUnitRunner])
class ExtensionsSpec extends Specification {

  "Extensions" should {

    "enable addition of numeric tuples" in {
      (1, 2) + (3, 4) mustEqual((4, 6))
      (3.7, 7.3) + (7.3, 3.7) mustEqual((11.0, 11.0))
    }

    "enable subtraction of numeric tuples" in {
      (4, 6) - (3, 4) mustEqual((1, 2))
      (11.0, 11.0) - (7.3, 3.7) mustEqual((3.7, 7.3))
    }

    "enable division of numeric tuples" in {
      (4, 6) / 2  mustEqual((2, 3))
      (11.0, 11.0) / 2 mustEqual((5.5, 5.5))
      (11.0, 11.0) / 2.0 mustEqual((5.5, 5.5))
    }

    "enable comparison of numeric tuples" in {
      (4, 6) > (3, 4) mustEqual((true, true))
      (4, 6) < (3, 4) mustEqual((false, false))
      (0.0, 11.0) > (7.3, 3.7) mustEqual((false, true))
      (0.0, 11.0) < (7.3, 3.7) mustEqual((true, false))
    }

    "make ranges of tuples" in {
      (0, 0) corner (1, 1) mustEqual(Seq((0, 0), (0, 1), (1, 0), (1, 1)))
      (1, 2) corner (3, 4) mustEqual(Seq((1, 2), (1, 3), (1, 4), (2, 2), (2, 3), (2, 4), (3, 2), (3, 3), (3, 4)))
      ((3, 1) corner (10, 10)).max mustEqual((10, 10))
      ((3, 1) corner (10, 10)).min mustEqual((3, 1))
    }

    "enable comparison using notEqualTo" in {
      42 notEqualTo 24 must beTrue
      37 notEqualTo 37 must beFalse
      "Foo" notEqualTo("Bar") must beTrue
      "Kevin" notEqualTo("Kevin") must beFalse
    }
  }
}

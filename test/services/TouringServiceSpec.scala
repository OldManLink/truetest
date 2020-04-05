package services

import models.{TourRequest, TourResponse}
import org.junit.runner.RunWith
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TouringServiceSpec extends Specification with Mockito {

  "TouringService" should {

    "get no tours for a minimal board" in {
      val touringService = new TouringServiceImpl()

      val tourResponse = touringService.getTours(TourRequest((4, 4), (0, 0), 0))

      tourResponse.tours.size must beEqualTo(0)
      tourResponse must beEqualTo(TourResponse(Nil))
    }

    "get max tours for a 5x5 board, using the Naive strategy" in {
      val touringService = new TouringServiceImpl()

      val tourResponse = touringService.getTours(TourRequest((5, 5), (4, 2), 552))

      tourResponse.tours.size must beEqualTo(552)
    }

    "get 3 tours for a 10x10 board, using the Divide and Conquer strategy" in {
      val touringService = new TouringServiceImpl()

      val tourResponse = touringService.getTours(TourRequest((10, 10), (4, 2), 3))

      tourResponse.tours.size must beEqualTo(3)
    }

    "throw an exception for an unsupported 6x6 board" in {
      val touringService = new TouringServiceImpl()

      touringService.getTours(TourRequest((6, 6), (4, 2), 5)) must throwA[IllegalStateException]("Not implemented yet")

    }
  }
}

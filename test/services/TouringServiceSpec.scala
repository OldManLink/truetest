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
  }
}

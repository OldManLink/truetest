package services

import models.{Tour, TourRequest, TourResponse}
import org.junit.runner.RunWith
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TouringServiceSpec extends Specification with Mockito {

  val tours: Seq[Tour] = Seq(Tour(0, "Tour 0", Seq((2, 2),(4, 2))))

  "TouringService" should {

    "get the tours for a board" in {
      val cpuService = new TouringServiceImpl()

      val tourResponse = cpuService.getTours(TourRequest((5, 5), (2, 2)))

      tourResponse must beEqualTo(TourResponse(tours))
    }
  }
}

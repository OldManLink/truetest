package services

import helpers.Move._
import models.{Tour, TourRequest, TourResponse}
import org.junit.runner.RunWith
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TouringServiceSpec extends Specification with Mockito {

  val tours: Seq[Tour] = Seq(
      Tour(0,1,2,List(NE, S, NW, SW, N, E, S, NW, E, SW, NW, E, SW, N, SE, W, NE, SE, W)),
      Tour(1,1,2,List(NE, S, NW, SW, N, E, SW, E, NW, SW, E, NW, S, NE, W, SE, NE, W, SE)),
      Tour(2,1,2,List(NE, S, NW, SW, E, NW, E, SW, NW, E, SW, N, SE, W, NE, SE, W, NE, W)),
      Tour(3,1,2,List(NE, W, SE, W, NE, SE, W, NE, S, NW, E, SW, NW, E, SW, E, NW, SW, N)),
      Tour(4,1,2,List(NE, W, SE, W, NE, SE, W, NE, W, S, NE, SE, W, NE, W, SE, NE, W, SE))
  )

  "TouringService" should {

    "get the tours for a board" in {
      val touringService = new TouringServiceImpl()

      val tourResponse = touringService.getTours(TourRequest((4, 5), (1, 2), 5))

      tourResponse.tours.size must beEqualTo(5)
      tourResponse must beEqualTo(TourResponse(tours))
    }
  }
}

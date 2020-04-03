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
    Tour(0,1,2,List(NE, S, NW, SW, E, NW, E, SW, NW, E, SW, N, SE, W, NE, SE, W, NE, W, SE)),
    Tour(1,1,2,List(NE, SW, NW, E, SW, E, NW, SW, E, NW, S, NE, W, SE, NE, W, SE, W, NE, SE)),
    Tour(2,1,2,List(NE, SW, NW, E, SW, E, NW, SW, E, NW, S, E, NW, SW, E, NW, E, SW, NW, E)),
    Tour(3,1,2,List(NE, SW, NW, S, NE, SE, W, N, SE, W, NE, SE, W, NE, S, NW, E, SW, NW, E)),
    Tour(4,1,2,List(NE, SW, NW, S, NE, SE, W, NE, W, SE, NE, W, SE, N, SW, E, NW, SW, E, NW)),
    Tour(5,1,2,List(NE, W, SE, W, NE, SE, W, NE, S, NW, E, SW, NW, E, SW, E, NW, SW, N, SE)),
    Tour(6,1,2,List(NW, E, SW, E, NW, SW, E, NW, S, NE, W, SE, NE, W, SE, W, NE, SE, N, SW)),
    Tour(7,1,2,List(NW, SE, NE, S, NW, SW, E, N, SW, E, NW, SW, E, NW, S, NE, W, SE, NE, W)),
    Tour(8,1,2,List(NW, SE, NE, S, NW, SW, E, NW, E, SW, NW, E, SW, N, SE, W, NE, SE, W, NE)),
    Tour(9,1,2,List(NW, SE, NE, W, SE, W, NE, SE, W, NE, S, W, NE, SE, W, NE, W, SE, NE, W)),
    Tour(10,1,2,List(NW, SE, NE, W, SE, W, NE, SE, W, NE, S, NW, E, SW, NW, E, SW, E, NW, SW)),
    Tour(11,1,2,List(NW, S, NE, SE, W, NE, W, SE, NE, W, SE, N, SW, E, NW, SW, E, NW, E, SW))
  )

  "TouringService" should {

    "get the tours for a board" in {
      val touringService = new TouringServiceImpl()

      val tourResponse = touringService.getTours(TourRequest((4, 5), (1, 2)))

      tourResponse.tours.size must beEqualTo(12)
      tourResponse must beEqualTo(TourResponse(tours))
    }
  }
}

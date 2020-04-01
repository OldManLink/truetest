package services

import java.time.LocalDateTime

import models.ClientResponse
import org.junit.runner.RunWith
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TouringServiceSpec extends Specification with Mockito {

  "TouringService" should {
    val now = LocalDateTime.now

    "create a client" in {
      val cpuService = new TouringServiceImpl()

      val newClientId = cpuService.createClient("Foobar")

      newClientId must beEqualTo(ClientResponse(LocalDateTime.now.getDayOfYear))
    }
  }
}

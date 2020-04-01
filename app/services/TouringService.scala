package services

import java.time.LocalDateTime

import models.{Client, ClientCpuResponse, ClientResponse, CpuLog, CpuReport, LogResponse}
import play.api.Logging
import play.api.libs.json.Json

trait TouringService {
  def createClient(name: String): ClientResponse
}

class TouringServiceImpl extends TouringService with Logging {

  override def createClient(name: String): ClientResponse = {
    val newClient = LocalDateTime.now
    logger.info(s"Created client: ${Json.toJson(newClient)}")
    ClientResponse(newClient.getDayOfYear)
  }
}

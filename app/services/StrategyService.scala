package services

import helpers.{DivideAndConquerStrategy, NaiveStrategy, TourSearchStrategy}
import models.{BoardDescription, TourRequest}

trait StrategyService {

  def getStrategy(tourRequest: TourRequest): TourSearchStrategy = {

    tourRequest.board match {
      case BoardDescription(10, 10) => new DivideAndConquerStrategy(tourRequest)
      case BoardDescription(5, 5) => new NaiveStrategy(tourRequest)
      case BoardDescription(4, 4) => new NaiveStrategy(tourRequest) // Used in tests only
      case _ => throw new IllegalStateException("Not implemented yet")
    }
  }
}

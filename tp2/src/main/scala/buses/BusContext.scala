package buses

import scala.language.postfixOps

trait BusContext {
  val busCount: Int
  val seatsPerBus: Int
  val standingPerBus: Int
  val busCapacity: Int = seatsPerBus + standingPerBus
  val roundTripMins: Int
  val roundTripCost: Int

  def departCondition(paxWaiting: Int): Boolean

  def simulateShift(paxPerHour: Int): SimulationResult = {
    var availableBuses = busCount
    var paxWaiting = 0
    var standingPax = 0
    var totalCost = 0
    var totalPax = 0
    var maxWaiting = 0
    for (t <- 0 until 8 * 60) {
      maxWaiting = math.max(maxWaiting, paxWaiting)
      if (t % 60 == 0) {
        paxWaiting += paxPerHour
      }
      if (t % roundTripMins == 0 && availableBuses < busCount)
        availableBuses += 1
      while (departCondition(paxWaiting) && availableBuses > 0) {
        availableBuses -= 1
        totalCost += roundTripCost
        val toSit = math.min(paxWaiting, seatsPerBus)
        paxWaiting -= toSit
        val toStand = math.min(paxWaiting, standingPerBus)
        paxWaiting -= toStand
        standingPax += toStand
        totalPax += toSit + toStand
      }
    }
    new SimulationResult(totalCost, standingPax, maxWaiting)
  }

  // Returns total cost and total standing passengers
  def simulate(b: BusFrequency): SimulationResult = {
    Seq(b.morning, b.afternoon, b.night) map simulateShift reduce ((a, b) => a + b)
  }
}

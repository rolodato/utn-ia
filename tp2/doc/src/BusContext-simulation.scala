// Condición para determinar si un colectivo debe partir o no
def departCondition(paxWaiting: Int): Boolean

def simulateShift(paxPerHour: Int, minsToNextBus: Int): SimulationResult = {
  val returnTimes = ListBuffer.fill(busCount)(0)
  def availableBuses(t: Int) = returnTimes.count(_ <= t)
  var paxWaiting = 0
  var standingPax = 0
  var totalCost = 0
  var totalPax = 0
  var maxWaiting = 0
  for (t <- 0 until 8 * 60) {
    if (t % 60 == 0) {
      paxWaiting += paxPerHour
    }
    maxWaiting = math.max(maxWaiting, paxWaiting)
    while (departCondition(paxWaiting) && availableBuses(t) > 0) {
      val (_, i) = returnTimes.zipWithIndex.find{
        case (time, _) => time <= t
      }.get
      returnTimes(i) = math.max(t + roundTripMins, t + minsToNextBus)
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

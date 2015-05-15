package buses

import org.scalatest.FunSuite

class BusFrequencySuite extends FunSuite {
  implicit val busContext = new BusContext {
    val seatsPerBus: Int = 30
    val standingPerBus: Int = 15
    val afternoonFreq: Int = 20
    val busCount: Int = 5
    val roundTripMins: Int = 15
    val roundTripCost: Int = 5
    def departCondition(paxWaiting: Int): Boolean = paxWaiting > 0
  }
  val normalFrequencies = new BusFrequency(100, 200, 150)
  val smallFrequencies = new BusFrequency(1, 2, 3)

  test("sanity check for normal simulation results") {
    val result = busContext.simulate(normalFrequencies)
    println((result.totalCost, result.standingPax, result.maxWaiting))
    assert(result.totalCost > 0 && result.standingPax > 0 && result.maxWaiting > 0)
  }

  test("small frequencies will have no passengers waiting") {
    val result = busContext.simulate(smallFrequencies)
    assert(result.totalPax === 0)
  }
}

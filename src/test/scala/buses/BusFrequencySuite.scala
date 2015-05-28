package buses

import org.scalatest.FunSuite

class BusFrequencySuite extends FunSuite {
  trait CommonOptions {
    val seatsPerBus: Int = 30
    val standingPerBus: Int = 15
    val afternoonFreq: Int = 20
    val busCount: Int = 5
    val roundTripMins: Int = 15
    val roundTripCost: Int = 5
    def departCondition(paxWaiting: Int): Boolean = paxWaiting > 0
  }

  val busyContext = new BusContext with CommonOptions {
    val paxFreqPerShift: List[Int] = List(100, 130, 80)
  }

  val easyContext = new BusContext with CommonOptions {
    val paxFreqPerShift: List[Int] = List(1, 2, 3)
  }

  val normalFrequencies = new BusFrequency(100, 200, 150)(busyContext)
  val smallFrequencies = new BusFrequency(1, 2, 3)(easyContext)

  test("sanity check for normal simulation results") {
    val result = busyContext.simulate(normalFrequencies)
    println((result.totalCost, result.standingPax, result.maxWaiting))
    assert(result.totalCost > 0 && result.standingPax > 0 && result.maxWaiting > 0)
  }

  test("small frequencies will have no passengers waiting") {
    val result = easyContext.simulate(smallFrequencies)
    assert(result.totalPax === 0)
  }
}

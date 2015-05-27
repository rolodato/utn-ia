package buses

class SimulationResult(val totalCost: Int,
                       val standingPax: Int,
                       val maxWaiting: Int) {
  def +(that: SimulationResult) = new SimulationResult(
    this.totalCost + that.totalCost,
    this.standingPax + that.standingPax,
    this.maxWaiting + that.maxWaiting
  )

  def totalPax: Int = standingPax + maxWaiting

  def fitness: Double = 1 / (totalCost + standingPax + maxWaiting)
}

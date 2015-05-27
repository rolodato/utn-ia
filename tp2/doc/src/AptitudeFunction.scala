class BusFrequency(val morning: Int,
                   val afternoon: Int,
                   val night: Int)(implicit ctx: BusContext) extends Gene
{
  lazy val fitness: Double = ctx.simulate(this).fitness
}

trait BusContext {
  def simulate(b: BusFrequency): SimulationResult = {
    val results = Seq(b.morning, b.afternoon, b.night) map simulateShift
    results reduce ((a, b) => a + b)
  }
}

class SimulationResult(val totalCost: Int,
                       val standingPax: Int,
                       val maxWaiting: Int) {
  def +(that: SimulationResult) = new SimulationResult(
    this.totalCost + that.totalCost,
    this.standingPax + that.standingPax,
    this.maxWaiting + that.maxWaiting
  )

  def fitness: Double = 1 / (totalCost + standingPax + maxWaiting)
}

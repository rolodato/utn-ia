class BusFrequency(val morning: Int,
                   val afternoon: Int,
                   val night: Int)(implicit ctx: BusContext) extends Gene
{
  lazy val fitness: Double = ctx.simulate(this).fitness
}

trait BusContext {
  def simulate(b: BusFrequency): SimulationResult = {
    val busFreqs = Seq(b.morning, b.afternoon, b.night)
    val paxFreqs = (0 until 2).map(paxFreqPerShift(_))
    val freqs = busFreqs zip paxFreqs
    val results = freqs map { case (b, p) => simulateShift (b, p)}
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

  def fitness: Double = 100000 / (totalCost + standingPax + maxWaiting)
}

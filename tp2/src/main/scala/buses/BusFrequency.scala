package buses

import rolodato.genetics.Individual

class BusFrequency(val morning: Int,
                   val afternoon: Int,
                   val night: Int)(implicit ctx: BusContext) extends Individual[Int] {
  def string: List[Int] = List(morning, afternoon, night)

  def fitness: Double = ctx.simulate(this).fitness
}

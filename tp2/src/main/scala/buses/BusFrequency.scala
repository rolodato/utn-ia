package buses

import rolodato.genetics.Gene

class BusFrequency(val morning: Int,
                   val afternoon: Int,
                   val night: Int)(implicit ctx: BusContext) extends Gene {
  val string: List[Int] = List(morning, afternoon, night)

  lazy val fitness: Double = ctx.simulate(this).fitness

  def copy(newString: List[Int]): Gene = BusFrequency(newString)
}

object BusFrequency {
  def apply(p: List[Int])(implicit ctx: BusContext): BusFrequency = {
    require(p.length == 3, "invalid parameter list")
    new BusFrequency(p(0), p(1), p(2))
  }
}

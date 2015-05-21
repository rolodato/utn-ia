package buses.webapp

import buses.{BusContext, BusFrequency}
import rolodato.genetics.impl.{IntegerMutation, OnePointCrossover,
RouletteSelection}
import rolodato.genetics._

import scala.scalajs.js.JSApp
import scala.util.Random

object BusApp extends JSApp {
  object TestGenetic extends Genetic {
    implicit val busContext = new BusContext {
      val seatsPerBus: Int = 30
      val standingPerBus: Int = 15
      val afternoonFreq: Int = 20
      val busCount: Int = 5
      val roundTripMins: Int = 15
      val roundTripCost: Int = 5
      def departCondition(paxWaiting: Int): Boolean = paxWaiting > 0
    }

    val populationSize: Int = 500
    val mutationProbability: Double = 0.1
    val selection: Selection = RouletteSelection()
    val crossover: Crossover = OnePointCrossover()
    val mutation: Mutation = IntegerMutation()
    val selectionPercentage: Double = 0.2
    val rng: Random = Random
    def randomGene(): Gene = {
      def rand(prop: Double) = (rng.nextInt(300) * prop).toInt
      new BusFrequency(rand(1.2), rand(1.0), rand(0.9))
    }
  }
  def main(): Unit = {
    println(TestGenetic.run(1))
    println("Hello world!")
  }
}

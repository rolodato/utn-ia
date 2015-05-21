package buses.webapp

import buses.{BusContext, BusFrequency}
import org.scalajs.dom.{Event, document}
import org.scalajs.dom.raw.HTMLInputElement
import rolodato.genetics._
import rolodato.genetics.impl.{IntegerMutation, OnePointCrossover, RouletteSelection}

import scala.language.postfixOps
import scala.scalajs.js.JSApp
import scala.util.Random

object BusApp extends JSApp {
  def main(): Unit = {
    val form = document.getElementById("form")
    form.addEventListener("submit", (e: Event) => {
      e.preventDefault()
      val names = List("populationSize", "mutationProbability",
        "selectionPercentage", "iterationCount", "busCount", "seatsPerBus",
        "standingPerBus", "roundTripMins", "roundTripCost")
      val inputs = names.map(name => name -> document.getElementById(name).asInstanceOf[HTMLInputElement].valueAsNumber) toMap
      val genetic = new Genetic {
        implicit val busContext = new BusContext {
          val seatsPerBus: Int = inputs("seatsPerBus")
          val standingPerBus: Int = inputs("standingPerBus")
          val busCount: Int = inputs("busCount")
          val roundTripMins: Int = inputs("roundTripMins")
          val roundTripCost: Int = inputs("roundTripCost")
          def departCondition(paxWaiting: Int): Boolean = paxWaiting > 0
        }
        def randomGene(): Gene = {
          def rand(prop: Double) = (rng.nextInt(300) * prop).toInt
          new BusFrequency(rand(1.0), rand(0.8), rand(0.7))
        }
        val mutationProbability: Double = inputs("mutationProbability") / 100.0
        val populationSize: Int = inputs("populationSize")
        val selection: Selection = RouletteSelection()
        val crossover: Crossover = OnePointCrossover()
        val mutation: Mutation = IntegerMutation()
        val selectionPercentage: Double = {
          inputs("selectionPercentage") / 100.0
        }
        val rng: Random = Random
      }
      println(genetic.run(inputs("iterationCount")))
    })
  }
  println("Hello world!")
}

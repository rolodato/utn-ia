package buses.webapp

import buses.{BusContext, BusFrequency}
import org.scalajs.dom.raw.{HTMLElement, HTMLInputElement}
import org.scalajs.dom.{Event, document}
import rolodato.genetics._
import rolodato.genetics.impl.{IntegerMutation, OnePointCrossover, RouletteSelection}

import scala.language.postfixOps
import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
import scala.scalajs.js
import scala.scalajs.js.JSApp
import scala.util.Random

object BusApp extends JSApp {
  def main(): Unit = {
    // If running in console, nothing to do
    if (js.isUndefined(document)) return
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
      genetic.run(inputs("iterationCount")).onSuccess {
        case result =>
          val fit = result.fittest.string
          document.getElementById("result").asInstanceOf[HTMLElement].style.display = "block"
          document.getElementById("fittest").innerHTML = s"${fit(0)}, ${fit (1)}, ${fit(2)}"
          document.getElementById("fitness").innerHTML = result.fittest.fitness.toString
          document.getElementById("evolution").innerHTML = result.fitnessEvolution.mkString(",")
          document.getElementById("pop-fitness").innerHTML = result.finalPopulation.map(_.fitness).mkString(",")
      }
    })
  }
}

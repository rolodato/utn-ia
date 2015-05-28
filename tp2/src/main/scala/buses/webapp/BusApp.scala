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
        "standingPerBus", "roundTripMins", "roundTripCost", "paxMorning",
        "paxAfternoon", "paxNight", "maxInterval")
      val inputs = names.map(name => name -> document.getElementById(name).asInstanceOf[HTMLInputElement].valueAsNumber) toMap
      val genetic = new Genetic {
        implicit val busContext = new BusContext {
          val seatsPerBus: Int = inputs("seatsPerBus")
          val standingPerBus: Int = inputs("standingPerBus")
          val busCount: Int = inputs("busCount")
          val roundTripMins: Int = inputs("roundTripMins")
          val roundTripCost: Int = inputs("roundTripCost")
          def departCondition(paxWaiting: Int): Boolean = paxWaiting > 0
          val paxFreqPerShift: List[Int] = List(
            inputs("paxMorning"),
            inputs("paxAfternoon"),
            inputs("paxNight")
          )
        }
        def randomGene(): Gene = {
          def rand = rng.nextInt(inputs("maxInterval")).toInt
          new BusFrequency(rand, rand, rand)
        }
        val mutationProbability: Double = inputs("mutationProbability") / 100.0
        val populationSize: Int = inputs("populationSize")
        val selection: Selection = RouletteSelection()
        val crossover: Crossover = OnePointCrossover(1)
        val mutation: Mutation = IntegerMutation()
        val selectionPercentage: Double = {
          inputs("selectionPercentage") / 100.0
        }
        val rng: Random = Random
      }
      def fitString(string: List[Int]) = {
        s"${string(0)}, ${string(1)}, ${string(2)}"
      }
      genetic.run(inputs("iterationCount")).onSuccess {
        case result =>
          val lastBest = result.finalPopulation.head
          val best = result.fittest
          document.getElementById("result").asInstanceOf[HTMLElement].style.display = "block"
          document.getElementById("best").innerHTML = fitString(best.string)
          document.getElementById("best-fitness").innerHTML = best.fitness.toString
          document.getElementById("evolution").innerHTML = result.fitnessEvolution.mkString("\n")
          document.getElementById("pop-fitness").innerHTML = result.finalPopulation.map(_.fitness).mkString("\n")
          document.getElementById("last-best").innerHTML = fitString(lastBest.string)
          document.getElementById("last-best-fitness").innerHTML = lastBest.fitness.toString
      }
    })
  }
}

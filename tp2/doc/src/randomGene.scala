def randomGene(): Gene = {
  def rand(prop: Double) = (rng.nextInt(300) * prop).toInt
  new BusFrequency(rand(1.0), rand(0.8), rand(0.7))
}

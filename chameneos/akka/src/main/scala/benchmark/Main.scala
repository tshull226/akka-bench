
package benchmark.cs524

//import se.scalablesolutions.akka.actor.{Actor, ActorRef}
//import se.scalablesolutions.akka.actor.Actor._
import akka.actor._
import kamon.Kamon
import Chameneos._


object Main extends App {

  //This is now necessary to add
  Kamon.start()

  val system = ActorSystem("application")
  Chameneos.start = System.currentTimeMillis
  val mallActor = system.actorOf(Props(new Mall(1000000, 4)), "mall")
  Thread.sleep(10000)
  println("Elapsed: " + (end - start))

  /*
  val numberGenerator = system.actorOf(Props[RandomNumberActor], "numbers")

  val generator = system.actorOf(Props[MessageGeneratorActor], "artifical")

  generator ! ConstantLoad(Schedule(numberGenerator, GenerateNumber, 5000))
  generator ! ConstantLoad(Schedule(numberGenerator, GenerateSecureNumber, 1000))
  generator ! Peak(Schedule(numberGenerator, GenerateNumber, 100000))
  generator ! Peak(Schedule(numberGenerator, GenerateSecureNumber, 25000))
  */
}

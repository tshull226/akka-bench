package benchmark.cs524.ring

//import se.scalablesolutions.akka.actor.{Actor, ActorRef}
//import se.scalablesolutions.akka.actor.Actor._
import akka.actor._
import kamon.Kamon

import java.lang._
import java.util.concurrent.CountDownLatch
import akka.event.Logging


object Main extends App {

  //This is now necessary to add
  //don't want to use this right now
  Kamon.start()


  //System.setProperty("akka.config", "akka.conf")

  val system = ActorSystem("application")
  //val log = Logging(system,this)
  startRing(10) ! StartMessage

  def startRing(n: Int): ActorRef = {
    val nodes = spawnNodes(n)
    connectNodes(n, nodes)
    nodes(0)
  }

  def spawnNodes(n: Int): Array[ActorRef] = {
    println("Spawning actors")
    val startConstructing = System.currentTimeMillis
    val nodes = new Array[ActorRef](n + 1)
    for (i <- 0 until n) nodes(i) = system.actorOf(Props(new NodeActor(i, null)))
    val endConstructing = System.currentTimeMillis
    //log.info("Took %s ms to construct %s nodes", (endConstructing - startConstructing), n)
    printf("Took %d ms to construct %d nodes\n", (endConstructing - startConstructing), n)
    nodes
  }

  def connectNodes(n: Int, nodes: Array[ActorRef]) = {
    println("Connecting actors")
    nodes(n) = nodes(0)
    for (i <- 0 until n) nodes(i) ! Connect(nodes(i + 1))
  }

}

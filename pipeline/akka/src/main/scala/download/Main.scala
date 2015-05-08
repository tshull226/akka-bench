package benchmark.cs524.pipeline

//import se.scalablesolutions.akka.actor.{Actor, ActorRef}
//import se.scalablesolutions.akka.actor.Actor._
import akka.actor._
import kamon.Kamon

import java.lang._
import java.util.concurrent.CountDownLatch


object Main extends App {

  //This is now necessary to add
  //don't want to use this right now
  Kamon.start()


  /*
  Chameneos.start = System.currentTimeMillis
  val mallActor = system.actorOf(Props(new Mall(1000000, 4)), "mall")
  Thread.sleep(10000)
  println("Elapsed: " + (end - start))
  */

  //System.setProperty("akka.config", "akka.conf")

  val system = ActorSystem("application")
  val write = system.actorOf(Props(new WriteActor()))
  val index = system.actorOf(Props(new IndexActor(write)))
  val download = system.actorOf(Props(new DownloadActor(index)))

  val start = System.currentTimeMillis
  import benchmark.cs524.pipeline.ActorManager._
  for (i <- 1 until NR_REQUESTS) {
    download ! ("Requested " + i)
  }
  download ! StopMessage
  //probably have to fix these too
  latch.await
  system.stop(download)
  system.stop(index)
  system.stop(write)
  println("Elapsed = " + (System.currentTimeMillis - start))
  /*
  val write = actorOf[WriteActor].start
  val index = actorOf(new IndexActor(write)).start
  val download = actorOf(new DownloadActor(index)).start

  val start = System.currentTimeMillis
  for (i <- 1 until NR_REQUESTS) download ! ("Requested " + i)
  download ! StopMessage
  latch.await
  download.stop
  index.stop
  write.stop
  println("Elapsed = " + (System.currentTimeMillis - start))
  */
}

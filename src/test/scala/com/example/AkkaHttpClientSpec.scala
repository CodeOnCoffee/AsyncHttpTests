package com.example

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import net.tstllc.common.mdc.{MDCExecutionContext, SESSION_ID}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.{AnyWordSpec, AsyncWordSpec}
import org.slf4j.MDC

import scala.concurrent.Future
import scala.util.{Failure, Success}

class AkkaHttpClientSpec extends AsyncWordSpec with Matchers {
  "AkkaHttpClient" should {
    "preserve MDC in result Futures" in {
      implicit val executionContext = MDCExecutionContext("test")

      implicit val system = ActorSystem(Behaviors.empty, "SingleRequest")

      MDC.put(SESSION_ID, "12345")
      val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(uri = "http://akka.io"))

      responseFuture.map( res => {
        println(res)
        MDC.get(SESSION_ID) shouldBe "12345"
      })
    }
  }
}

package com.example

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import net.tstllc.common.mdc.{MDCExecutionContext, SESSION_ID}
import net.tstllc.common.wsclients.StandaloneWSClients
import net.tstllc.common.wsclients.play.WSClients
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AsyncWordSpec
import org.slf4j.MDC
import play.api.libs.ws.WSRequest
import play.api.libs.ws.ahc.AhcWSClient

import scala.concurrent.Future

class AsyncHttpClientSpec extends AsyncWordSpec with Matchers {
  "AsyncHttpClient" should {
    "preserve MDC in result Futures" in {
      implicit val executionContext = MDCExecutionContext("test")

      MDC.put(SESSION_ID, "12345")
      val client = new AhcWSClient( StandaloneWSClients.internalClient ) {
        override def url(url: String): WSRequest = super.url(url)
      }

      val responseFuture = WSClients.internalClient.url("http://akka.io").execute()

      // This fails
      responseFuture.map( res => {
        println(res)
        MDC.get(SESSION_ID) shouldBe "12345"
      })
    }
  }
}

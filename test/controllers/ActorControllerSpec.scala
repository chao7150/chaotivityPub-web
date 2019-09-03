package controllers

import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test.{FakeRequest, Injecting}
import play.api.test.Helpers._
import play.api.libs.json.{JsValue, Reads, JsPath}
import models.Actor

class ActorControllerSpec
    extends PlaySpec
    with GuiceOneAppPerTest
    with Injecting {
  "ActorController GET" should {
    "return information of actor" in {
      val request = FakeRequest(GET, "/actor/1")
      val sampleActor = route(app, request).get

      status(sampleActor) mustBe OK
      contentType(sampleActor) mustBe Some("application/json")
      val content: Actor = contentAsJson(sampleActor).as[Actor]
      content.id mustBe 1
      content.actorType.toString mustBe "Person"
      content.inbox mustBe "https://sencha.chao.tokyo/actor/1/inbox"
      content.outbox mustBe "https://sencha.chao.tokyo/actor/1/outbox"
    }
  }
}

package models

import play.api.libs.json.{Json, JsPath, Reads, Writes}
import play.api.libs.functional.syntax._

case class Actor(
    id: Int,
    actorType: String,
    inbox: String,
    outbox: String
)

object Actor {
  implicit val actorReads: Reads[Actor] = (
    (JsPath \ "id").read[Int] and
      (JsPath \ "actorType").read[String] and
      (JsPath \ "inbox").read[String] and
      (JsPath \ "outbox").read[String]
  )(Actor.apply _)
  implicit val actorWrites = new Writes[Actor] {
    def writes(actor: Actor) = Json.obj(
      "id" -> actor.id,
      "actorType" -> actor.actorType,
      "inbox" -> actor.inbox,
      "outbox" -> actor.outbox
    )
  }
}

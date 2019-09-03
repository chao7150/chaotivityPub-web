package models

import play.api.libs.json.{Json, JsPath, Reads, Writes}
import play.api.libs.functional.syntax._
import play.api.libs.json.JsValue
import play.api.libs.json.JsResult

object ActorType {
  def apply(actorTypeString: String): ActorType =
    actorTypeString match {
      case "Application"  => Application
      case "Group"        => Group
      case "Organization" => Organization
      case "Person"       => Person
      case "Service"      => Service
      case _              => UnknownActor
    }
}
sealed abstract class ActorType {
  override def toString: String = this.getClass.getSimpleName.init
}

case object Application extends ActorType
case object Group extends ActorType
case object Organization extends ActorType
case object Person extends ActorType
case object Service extends ActorType
case object UnknownActor extends ActorType

final class Actor private (
    val id: Int,
    val actorType: ActorType,
    val inbox: String,
    val outbox: String
)

object Actor {
  def apply(
      id: Int,
      actorTypeString: String,
      inbox: String,
      outbox: String
  ): Actor = new Actor(id, ActorType(actorTypeString), inbox, outbox)

  implicit val actorReads: Reads[Actor] = (
    (JsPath \ "id").read[Int] and
      (JsPath \ "actorType").read[String] and
      (JsPath \ "inbox").read[String] and
      (JsPath \ "outbox").read[String]
  )(Actor.apply _)

  implicit val actorWrites = new Writes[Actor] {
    def writes(actor: Actor) = Json.obj(
      "id" -> actor.id,
      "actorType" -> actor.actorType.toString,
      "inbox" -> actor.inbox,
      "outbox" -> actor.outbox
    )
  }
}

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
    val actorType: ActorType
) {
  val hostName: String = sys.env.getOrElse("HOST_NAME", "sencha.chao.tokyo")
  val inbox: String = s"https://$hostName/actor/$id/inbox"
  val outbox: String = s"https://$hostName/actor/$id/outbox"
}

object Actor {
  def apply(
      id: Int,
      actorTypeString: String
  ): Actor = new Actor(id, ActorType(actorTypeString))

  implicit val actorReads: Reads[Actor] = (
    (JsPath \ "id").read[Int] and
      (JsPath \ "actorType").read[String]
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

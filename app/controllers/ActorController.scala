package controllers

import javax.inject._
import play.api._
import play.api.libs.json.{Json}
import play.api.mvc._
import play.api.db.Database
import scalikejdbc._
import models.Actor

@Singleton
class ActorController @Inject()(cc: ControllerComponents)
    extends AbstractController(cc) {
  implicit val session = AutoSession
  sql"""
  create table actors (
    id smallint not null primary key auto_increment,
    type varchar(64),
    inbox varchar(255),
    outbox varchar(255)
  )
      """.execute.apply()
  val sampleActor: Actor =
    Actor(
      1,
      "person",
      "https://sencha.chao.tokyo/actor/1/inbox",
      "https://sencha.chao.tokyo/actor/1/outbox"
    )
  sql"""
    insert into actors (type, inbox, outbox) values (${sampleActor.actorType}, ${sampleActor.inbox}, ${sampleActor.outbox})
  """.execute.apply()
  def get(id: Int) = Action {
    val actor: Option[Actor] = DB readOnly { implicit session: DBSession =>
      sql"select * from actors where id = ${id}"
        .map(
          rs =>
            Actor(
              rs.int("id"),
              rs.string("type"),
              rs.string("inbox"),
              rs.string("outbox")
            )
        )
        .single
        .apply()
    }
    actor match {
      case Some(value) => Ok(Json.toJson(value)).as("application/json")
      case None        => NotFound
    }
  }
}

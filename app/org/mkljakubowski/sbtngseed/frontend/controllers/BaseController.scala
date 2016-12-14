package org.mkljakubowski.sbtngseed.frontend.controllers

import org.mkljakubowski.sbtngseed.db.{ BaseIdDao, BaseIdEntity, BaseTable }
import play.api.libs.json.{ Format, JsArray, JsObject, Json }

case class QueryRequest(skip: Int, take: Int, obj: JsObject)

object QueryRequest {
  implicit val format = Json.format[QueryRequest]
}

case class QueryResult(limit: Int, result: JsArray)

object QueryResult {
  implicit val format = Json.format[QueryResult]
}

trait BaseController[T <: BaseIdEntity[Int]] extends AuthenticatedController {

  implicit def format: Format[T]

  def dao: BaseIdDao[T, _ <: BaseTable, Int]

  def save = doPost[T, Int](dao.save)
  def search = doPost[QueryRequest, QueryResult] { req =>
    dao.search(req).flatMap { objs =>
      dao.count().map { count =>
        QueryResult(count, Json.toJson(objs).as[JsArray])
      }
    }
  }
  def get(id: Int) = doGetOption(dao.findById(id))
  def findAll = doGet(dao.findAll)

}

package org.codefeedr.duplication.stages

import org.apache.flink.streaming.api.scala.DataStream
import org.codefeedr.pipeline.Context
import org.codefeedr.stages.InputStage
import org.json4s._
import org.json4s.native.JsonMethods._
import org.json4s.jackson.Serialization.write
import org.apache.flink.api.scala._
import scala.io.Source

case class Event(eventData: String)

class ReadEventsStage(stageName: Option[String] = None)
    extends InputStage[Event](stageName) {

  implicit val formats = DefaultFormats
  val fileName = "events.json"

  override def main(context: Context): DataStream[Event] = {
    val fileContents = Source.fromResource(fileName).getLines.mkString
    val parsedContents = parse(fileContents)
    val events = parsedContents.children.map(x => Event(compact(render(x))))

    context.env
      .fromCollection(events)
  }
}

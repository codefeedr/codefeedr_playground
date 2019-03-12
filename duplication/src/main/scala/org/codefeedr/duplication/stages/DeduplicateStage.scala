package org.codefeedr.duplication.stages

import org.apache.flink.streaming.api.scala.DataStream
import org.codefeedr.pipeline.Context
import org.codefeedr.stages.InputStage

import scala.io.Source

case class PushEvent()

class DeduplicateStage(stageName: Option[String] = None) extends InputStage[PushEvent](stageName) {

  val fileName = "events.json"
  val fileContents = Source.fromResource(fileName).getLines.mkString

  override def main(context: Context): DataStream[PushEvent] = {
    null
  }
}

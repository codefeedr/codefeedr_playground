package org.codefeedr.duplication

import java.util.UUID

import org.apache.flink.streaming.api.scala.DataStream
import org.codefeedr.duplication.data.Data.PushEvent
import org.codefeedr.duplication.stages.{
  CountElementsStage,
  DeduplicateStage,
  Event,
  ReadEventsStage
}
import org.codefeedr.pipeline.PipelineBuilder
import org.codefeedr.stages.utilities.{JsonPrinterOutput, PrinterOutput}
import org.apache.flink.api.scala._

object Main {

  def main(args: Array[String]): Unit = {
    new PipelineBuilder()
      .append(new ReadEventsStage(Some(UUID.randomUUID().toString)))
      .append(new DeduplicateStage(Some(UUID.randomUUID().toString)))
      /**.append { trans: DataStream[PushEvent] =>
        trans
          .map(_.id)
          .print()
      }**/
      .append(new CountElementsStage())
      .build()
      .startLocal()
  }

}

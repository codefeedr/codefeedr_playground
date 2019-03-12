package org.codefeedr.duplication

import org.codefeedr.duplication.data.Data.PushEvent
import org.codefeedr.duplication.stages.{
  CountElementsStage,
  DeduplicateStage,
  Event,
  ReadEventsStage
}
import org.codefeedr.pipeline.PipelineBuilder
import org.codefeedr.stages.utilities.{JsonPrinterOutput, PrinterOutput}

object Main {

  def main(args: Array[String]): Unit = {
    new PipelineBuilder()
      .append(new ReadEventsStage(Some("events")))
      .append(new DeduplicateStage(stageName = Some("push_eventss")))
      .append(new CountElementsStage())
      .build()
      .startLocal()
  }

}

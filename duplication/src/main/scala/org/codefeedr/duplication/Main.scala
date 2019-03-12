package org.codefeedr.duplication

import java.util.UUID

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
      .append(new ReadEventsStage(Some(UUID.randomUUID().toString)))
      .append(
        new DeduplicateStage(stageName = Some(UUID.randomUUID().toString)))
      .append(new CountElementsStage())
      .build()
      .startLocal()
  }

}

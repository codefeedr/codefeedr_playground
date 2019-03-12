package org.codefeedr.duplication

import org.codefeedr.duplication.stages.{Event, ReadEventsStage}
import org.codefeedr.pipeline.PipelineBuilder
import org.codefeedr.stages.utilities.JsonPrinterOutput

object Main {

  def main(args: Array[String]): Unit = {
    new PipelineBuilder()
      .append(new ReadEventsStage())
      .append(new JsonPrinterOutput[Event]())
      .build()
      .startMock()
  }

}

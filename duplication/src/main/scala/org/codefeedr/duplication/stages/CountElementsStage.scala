package org.codefeedr.duplication.stages

import org.apache.flink.streaming.api.scala.DataStream
import org.apache.flink.streaming.api.windowing.time.Time
import org.codefeedr.duplication.data.Data.PushEvent
import org.codefeedr.stages.OutputStage
import org.apache.flink.api.scala._

class CountElementsStage(stageId: Option[String] = None, seconds: Int = 10)
    extends OutputStage[PushEvent] {
  override def main(source: DataStream[PushEvent]): Unit = {
    source
      .map(x => ("sum", 1))
      .keyBy(_._1)
      .sum(1)
      .print()
  }
}

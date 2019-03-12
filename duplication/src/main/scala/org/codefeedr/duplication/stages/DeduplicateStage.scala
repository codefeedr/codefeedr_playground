package org.codefeedr.duplication.stages

import org.apache.flink.streaming.api.scala.DataStream
import org.codefeedr.stages.{InputStage, TransformStage}
import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.scala.function.WindowFunction
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.{TimeWindow, Window}
import org.apache.flink.util.Collector
import org.codefeedr.duplication.data.Data.PushEvent
import org.json4s._
import org.json4s.jackson.JsonMethods._

class DeduplicateStage(stageName: Option[String] = None)
    extends TransformStage[Event, PushEvent](stageName) {
  override def transform(source: DataStream[Event]): DataStream[PushEvent] = {
    source
      .map(_.eventData)
      .map { x =>
        implicit val defaultFormats = DefaultFormats

        parse(x).extract[PushEvent]
      }
      .assignAscendingTimestamps(_.created_at.getTime)
      .keyBy(_.id)
      .timeWindow(Time.seconds(5))
      .apply(new DuplicateWindow[PushEvent, PushEvent, String, TimeWindow]())
  }
}

class DuplicateWindow[IN, OUT, KEY, W <: Window]
    extends WindowFunction[IN, OUT, KEY, W] {

  override def apply(key: KEY,
                     window: W,
                     input: Iterable[IN],
                     out: Collector[OUT]): Unit = {
    if (input.size > 1) {
      println("Found a duplicate, just emitting only one.")
    }
    out.collect(input.head.asInstanceOf[OUT])
  }
}

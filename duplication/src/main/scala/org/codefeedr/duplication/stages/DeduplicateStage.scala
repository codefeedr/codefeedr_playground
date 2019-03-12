package org.codefeedr.duplication.stages

import org.apache.flink.streaming.api.scala.DataStream
import org.codefeedr.stages.{InputStage, TransformStage}
import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor
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
    //.assignTimestampsAndWatermarks(
    //  new PushOutOfOrdernessTimestampExtractor(Time.seconds(5)))
    //.keyBy(_.id)
    //.timeWindow(Time.seconds(5))
    //.apply(new DuplicateWindow[PushEvent, String, TimeWindow]())
  }
}

class PushOutOfOrdernessTimestampExtractor(t: Time)
    extends BoundedOutOfOrdernessTimestampExtractor[PushEvent](t) {

  override def extractTimestamp(element: PushEvent): Long =
    element.created_at.getTime
}

class DuplicateWindow[IN, KEY, W <: Window]
    extends WindowFunction[IN, IN, KEY, W] {

  override def apply(key: KEY,
                     window: W,
                     input: Iterable[IN],
                     out: Collector[IN]): Unit = {
    if (input.size > 1) {
      println("Found a duplicate, just emitting only one.")
    }

    println("Here")
    out.collect(input.head)
  }
}

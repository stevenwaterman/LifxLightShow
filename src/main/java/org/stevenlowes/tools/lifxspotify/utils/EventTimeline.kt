package org.stevenlowes.tools.lifxspotify.utils

import org.stevenlowes.tools.lifxspotify.lifx.Event
import java.util.concurrent.ScheduledThreadPoolExecutor
import java.util.concurrent.TimeUnit

class EventTimeline: ScheduledThreadPoolExecutor(10){

    private val mutableEvents: MutableSet<Event> = mutableSetOf()

    fun start(): Long{
        val events = mutableEvents.toSortedSet()
        val startTime = System.currentTimeMillis() + 1000

        for (event in events) {
            val currentTime = System.currentTimeMillis()
            val eventTime = startTime + event.ms
            val delay = eventTime - currentTime
            schedule(event::send, delay, TimeUnit.MILLISECONDS)
        }

        return startTime
    }

    fun add(event: Event){
        mutableEvents.add(event)
    }
}
package org.stevenlowes.tools.lifxspotify.tools

import org.stevenlowes.tools.lifxcontroller.commands.request.light.RequestSetColor
import org.stevenlowes.tools.lifxcontroller.values.Color
import org.stevenlowes.tools.lifxcontroller.values.Hue
import org.stevenlowes.tools.lifxspotify.utils.EventTimeline
import org.stevenlowes.tools.lifxspotify.Lights
import org.stevenlowes.tools.lifxspotify.lifx.Event
import org.stevenlowes.tools.lifxspotify.lifx.commands.Pulse
import java.util.*

class Calibration{
    companion object {
        fun start(){
            val beats = mutableListOf<Long>()

            println("Ignore the first 5 red beats and hit enter on the 5 green beats afterwards. This process will repeat 5 times. Hit enter to start.")

            val timeline = EventTimeline()
            val greenPulse = Pulse(Color(hue = Hue.GREEN), 1000, 0.8)
            val redPulse = Pulse(Color(hue = Hue.RED), 1000, 0.8)
            var ms: Long = 0

            (1..5).forEach {
                (1..5).forEach { _ ->
                    timeline.add(Event(redPulse, Lights.allLights, ms))
                    ms += 1000
                }
                (1..5).forEach { _ ->
                    timeline.add(Event(greenPulse, Lights.allLights, ms))
                    ms += 1000
                }
            }
            timeline.add(Event(RequestSetColor(color = Color.WHITE),
                                                                      Lights.allLights,
                                                                      ms))


            val scanner = Scanner(System.`in`)
            scanner.nextLine()

            val lightsStart = timeline.start()

            for(i in (1..25)){
                scanner.nextLine()
                val time = System.currentTimeMillis()
                beats.add(time)
            }

            println("Complete!")

            val delays = beats.asSequence().withIndex().map { (index, time) ->
                val set = index / 5

                val redDelay = (set + 1) * 5000
                val greenDelay = index * 1000
                val expectedDelay = redDelay + greenDelay

                val actualDelay = time - lightsStart
                val extraDelay = actualDelay - expectedDelay
                return@map extraDelay
            }.toList()

            val medianDelay = delays.sorted()[12]

            println(delays)
            println(medianDelay)
        }
    }
}

fun main(args: Array<String>){
    Calibration.start()
}
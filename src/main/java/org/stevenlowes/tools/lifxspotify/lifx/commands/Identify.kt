package org.stevenlowes.tools.lifxspotify.lifx.commands

import org.stevenlowes.tools.lifxcontroller.commands.request.Command
import org.stevenlowes.tools.lifxcontroller.commands.request.light.RequestSetColor
import org.stevenlowes.tools.lifxcontroller.commands.request.light.RequestSetLightPower
import org.stevenlowes.tools.lifxcontroller.values.Color
import org.stevenlowes.tools.lifxcontroller.values.Level
import java.net.InetAddress

class Identify : Command {
    override fun send(lights: Collection<InetAddress>) {
        val onPower = RequestSetLightPower(Level.MAX)
        val onColor = RequestSetColor(color = Color(saturation = Level.MIN, brightness = Level.MAX))
        val offColor = RequestSetColor(color = Color(saturation = Level.MIN, brightness = Level.MIN))

        println("All on")
        onPower.send(lights)

        Thread.sleep(500)

        println("All colors off")
        offColor.send(lights)

        lights.forEach { light ->
            println("Light $light")
            (1..3).forEach { _ ->
                println("Light on")
                onColor.send(light)
                Thread.sleep(500)
                println("Light off")
                offColor.send(light)
                Thread.sleep(500)
            }
            Thread.sleep(2000)
        }
    }
}
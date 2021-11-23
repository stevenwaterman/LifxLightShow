package org.stevenlowes.tools.lifxspotify.lifx

import org.stevenlowes.tools.lifxcontroller.commands.request.Command
import java.net.InetAddress

data class Event(val command: Command, val lights: Set<InetAddress>, val ms: Long): Comparable<Event>{
    override fun compareTo(other: Event): Int {
        return (ms - other.ms).toInt()
    }

    fun send(){
        command.send(lights)
    }
}
package org.stevenlowes.tools.lifxspotify

import java.net.InetAddress

class Lights{
    companion object {
        val desk = InetAddress.getByName("192.168.8.197")
        val bedside = InetAddress.getByName("192.168.8.138")

        val allLights = setOf(desk, bedside)
    }
}
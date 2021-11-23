package org.stevenlowes.tools.lifxspotify.tools

import org.stevenlowes.tools.lifxcontroller.commands.request.light.RequestSetColor
import org.stevenlowes.tools.lifxcontroller.values.Color
import org.stevenlowes.tools.lifxspotify.Lights
import javax.swing.JOptionPane

fun main(args: Array<String>) {
    while (true) {
        when (JOptionPane.showOptionDialog(null, "What action", "On/Off", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null)) {
            1 -> RequestSetColor(color = Color.BLACK).send(Lights.allLights)
            0 -> RequestSetColor(color = Color.WHITE).send(Lights.allLights)
        }
    }
}
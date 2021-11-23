package org.stevenlowes.tools.lifxspotify.lifx.commands

import org.stevenlowes.tools.lifxcontroller.commands.CompoundCommand
import org.stevenlowes.tools.lifxcontroller.commands.request.light.RequestSetColor
import org.stevenlowes.tools.lifxcontroller.commands.request.light.RequestSetWaveform
import org.stevenlowes.tools.lifxcontroller.commands.request.light.RequestSetWaveformOptional
import org.stevenlowes.tools.lifxcontroller.values.Color
import org.stevenlowes.tools.lifxcontroller.values.Level

class Pulse(color: Color, ms: Long, dutyCycle: Double) : CompoundCommand(
        RequestSetColor(color = color),
        RequestSetColor(color = Color(color.hue, color.saturation, Level.MIN), duration = (ms*dutyCycle).toLong())
                                                                        )
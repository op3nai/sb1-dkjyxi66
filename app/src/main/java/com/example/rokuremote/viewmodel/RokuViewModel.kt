package com.example.rokuremote.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rokuremote.R
import com.example.rokuremote.model.Channel
import com.example.rokuremote.model.Device
import com.example.rokuremote.model.CustomButton
import com.example.rokuremote.repository.RokuRepository
import kotlinx.coroutines.launch

class RokuViewModel(private val repository: RokuRepository = RokuRepository()) : ViewModel() {
    // ... (previous code remains)

    fun processVoiceCommand(command: String) {
        when {
            command.contains("volume up", ignoreCase = true) -> sendCommand("VolumeUp")
            command.contains("volume down", ignoreCase = true) -> sendCommand("VolumeDown")
            command.contains("mute", ignoreCase = true) -> sendCommand("VolumeMute")
            command.contains("play", ignoreCase = true) -> sendCommand("Play")
            command.contains("pause", ignoreCase = true) -> sendCommand("Pause")
            command.contains("home", ignoreCase = true) -> sendCommand("Home")
            command.contains("back", ignoreCase = true) -> sendCommand("Back")
            command.contains("netflix", ignoreCase = true) -> launchChannel("1")
            command.contains("hbo", ignoreCase = true) -> launchChannel("2")
            command.contains("hulu", ignoreCase = true) -> launchChannel("3")
            command.contains("youtube", ignoreCase = true) -> launchChannel("4")
            else -> error = "Unrecognized voice command"
        }
    }

    // ... (rest of the code remains the same)
}
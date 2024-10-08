package com.example.rokuremote

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rokuremote.ui.CustomButtonDialog
import com.example.rokuremote.ui.theme.RokuRemoteTheme
import com.example.rokuremote.viewmodel.RokuViewModel

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: RokuViewModel

    private val speechRecognizer = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val spokenText: String? =
                result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.get(0)
            spokenText?.let { viewModel.processVoiceCommand(it) }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RokuRemoteTheme {
                viewModel = viewModel()
                RokuRemoteApp(viewModel) { startVoiceRecognition() }
            }
        }
    }

    private fun startVoiceRecognition() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        }
        speechRecognizer.launch(intent)
    }
}

@Composable
fun RokuRemoteApp(viewModel: RokuViewModel, onVoiceCommandRequest: () -> Unit) {
    var showCustomButtonDialog by remember { mutableStateOf(false) }
    var showDeviceDialog by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            Column {
                FloatingActionButton(onClick = { showCustomButtonDialog = true }) {
                    Text("+")
                }
                Spacer(modifier = Modifier.height(8.dp))
                FloatingActionButton(onClick = { showDeviceDialog = true }) {
                    Text("Devices")
                }
                Spacer(modifier = Modifier.height(8.dp))
                FloatingActionButton(onClick = onVoiceCommandRequest) {
                    Icon(painter = painterResource(id = R.drawable.ic_mic), contentDescription = "Voice Command")
                }
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            // ... (previous UI components)
        }
    }

    // ... (CustomButtonDialog and DeviceDialog)
}
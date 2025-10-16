package com.mubarak.navigator
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
@Composable
fun MubarakScreen() {
  val vm = androidx.lifecycle.viewmodel.compose.viewModel<MubarakViewModel>()
  val s by vm.state.collectAsState()
  val snack = remember { SnackbarHostState() }
  LaunchedEffect(s.snackbar) { s.snackbar?.let { snack.showSnackbar(it); vm.consumeSnackbar() } }
  Scaffold(
    topBar = { TopAppBar(title = { Text("Mubarak Navigator") }) },
    snackbarHost = { SnackbarHost(snack) }
  ) { pad ->
    Column(Modifier.padding(pad).padding(16.dp).fillMaxSize()) {
      var tf by remember { mutableStateOf(TextFieldValue(s.prompt)) }
      LaunchedEffect(s.prompt) { tf = TextFieldValue(s.prompt) }
      OutlinedTextField(
        value = tf, onValueChange = { tf = it; vm.onPromptChange(it.text) },
        label = { Text("Instruction prompt") }, minLines = 3, modifier = Modifier.fillMaxWidth()
      )
      Spacer(Modifier.height(12.dp))
      Button(enabled = !s.running, onClick = vm::runOnce) {
        Text(if (s.running) "Running…" else "Run Inference Once")
      }
      Spacer(Modifier.height(12.dp))
      Text(
        s.output.ifEmpty { "Output will appear here…" },
        modifier = Modifier.weight(1f).fillMaxWidth().verticalScroll(rememberScrollState())
      )
      Spacer(Modifier.height(12.dp))
      TextButton(onClick = { /* optional diagnostics */ }) { Text("Settings → Diagnostics") }
    }
  }
}

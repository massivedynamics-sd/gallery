package com.mubarak.navigator
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
data class UiState(
  val prompt: String = DEFAULT_PROMPT,
  val output: String = "",
  val running: Boolean = false,
  val snackbar: String? = null
) {
  companion object {
    const val DEFAULT_PROMPT =
"You are an assistant for a visually impaired person. The camera is on their chest. Describe the scene in 5 words or less. Example: ‘A doorway, 2 meters away.’ "
  }
}
class MubarakViewModel : ViewModel() {
  private val _state = MutableStateFlow(UiState())
  val state: StateFlow<UiState> = _state.asStateFlow()
  fun onPromptChange(v: String) { _state.update { it.copy(prompt = v) } }
  fun runOnce() {
    val s = _state.value
    if (s.running) return
    _state.update { it.copy(running = true, output = "") }
    viewModelScope.launch {
      try {
        LlmBridge.tokens(s.prompt)
          .onCompletion { _state.update { it.copy(running = false) } }
          .collect { tok -> _state.update { st -> st.copy(output = st.output + tok) } }
      } catch (_: Throwable) {
        _state.update { it.copy(running = false, snackbar = "Model not loaded. Open Gallery’s model picker.") }
      }
    }
  }
  suspend fun selfTest(): Pair<Long,String> = 0L to ""
  fun consumeSnackbar() { _state.update { it.copy(snackbar = null) } }
}

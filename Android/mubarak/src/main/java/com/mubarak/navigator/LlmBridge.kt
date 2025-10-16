package com.mubarak.navigator
import kotlinx.coroutines.flow.Flow
object LlmBridge {
  /** Replace with Gallery's active text-generation stream API. */
  fun tokens(prompt: String): Flow<String> {
    throw NotImplementedError("Hook into Gallery engine here")
  }
}

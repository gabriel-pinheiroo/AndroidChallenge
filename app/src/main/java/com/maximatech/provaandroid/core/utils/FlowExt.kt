package com.maximatech.provaandroid.core.utils

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

/**
 * Extension function to update MutableStateFlow value using a lambda
 */
inline fun <T> MutableStateFlow<T>.update(function: T.() -> T) = update { function(it) }

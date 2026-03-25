package com.example.tudee.presentation.unit

fun String.isValidUri(): Boolean {
    val uriRegex = Regex("^(https?|ftp|file|content)://[^\\s/$.?#].[^\\s]*$")
    return uriRegex.matches(this)
}
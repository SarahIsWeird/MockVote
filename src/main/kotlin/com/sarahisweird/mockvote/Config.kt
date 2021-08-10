package com.sarahisweird.mockvote

data class Config(
    val cooldown: Long,
    val cooldownMessage: String,
    val successMessage: String,
    val commands: List<String>
)
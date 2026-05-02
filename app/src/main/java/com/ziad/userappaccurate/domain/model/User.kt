package com.ziad.userappaccurate.domain.model

data class User(
    val name: String,
    val address: String,
    val email: String,
    val phoneNumber: String,
    val city: String,
    val gender: Int,
    val id: String
)
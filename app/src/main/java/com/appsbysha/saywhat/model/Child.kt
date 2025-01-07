package com.appsbysha.saywhat.model

import java.util.UUID

/**
 * Created by sharone on 06/01/2025.
 */

data class Child(
    val childId: String = UUID.randomUUID().toString(),
    val name: String,
    val dob: Long,
    val profilePic: Byte? = null,
    val sayings: MutableList<Saying> = mutableListOf()
)

package com.appsbysha.saywhat.model

import android.net.Uri
import kotlinx.serialization.Serializable
import java.util.HashMap
import java.util.UUID

/**
 * Created by sharone on 06/01/2025.
 */
@Serializable
data class Child(
    val childId: String = UUID.randomUUID().toString(),
    val name: String = "",
    val dob: Long = 0,
    val profilePic: String? = null,
    val sayings: HashMap<String, Saying> = hashMapOf()
)

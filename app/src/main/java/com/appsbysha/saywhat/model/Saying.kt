package com.appsbysha.saywhat.model

import kotlinx.serialization.Serializable
import java.util.UUID


/**
 * Created by sharone on 06/01/2025.
 */
@Serializable
data class Saying(
    val date: Long,
    val lineList: List<Line>,
    val id: String = UUID.randomUUID().toString()
)
package com.appsbysha.saywhat.model


/**
 * Created by sharone on 06/01/2025.
 */
data class Saying(
    val date: Long,
    val lineList: List<Line>,
    val id: String
)
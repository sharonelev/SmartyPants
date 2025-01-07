package com.appsbysha.saywhat.model


/**
 * Created by sharone on 06/01/2025.
 */

data class Line(
    val lineType: LineType,
    val text: String,
    val otherPerson: String? = null
)

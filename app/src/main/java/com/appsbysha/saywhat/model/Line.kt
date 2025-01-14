package com.appsbysha.saywhat.model

import kotlinx.serialization.Serializable


/**
 * Created by sharone on 06/01/2025.
 */
@Serializable
data class Line(
    val lineType: LineType,
    var text: String = "",
    var otherPerson: String? = null
)

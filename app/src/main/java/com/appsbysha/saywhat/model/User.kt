package com.appsbysha.saywhat.model


/**
 * Created by sharone on 06/01/2025.
 */

data class User(
    val children: Map<String, List<Saying>> = emptyMap()

)

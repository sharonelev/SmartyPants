package com.appsbysha.saywhat.model

import kotlinx.serialization.Serializable
import java.util.HashMap


/**
 * Created by sharone on 06/01/2025.
 */
@Serializable
data class User(
    val children: HashMap<String, Child> = hashMapOf()

)

package mx.edu.utez.firebase4a20213tn002.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User (val username: String? = null, val email: String? = null)
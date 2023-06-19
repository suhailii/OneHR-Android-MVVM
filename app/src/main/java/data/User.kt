package data

import com.google.firebase.database.Exclude

data class User (
    val employeeID: String? = "",
    val password : String? = ""
)
{
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "userid" to employeeID,
            "password" to password
        )
    }

}
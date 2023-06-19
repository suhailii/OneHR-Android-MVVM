package data

import com.google.firebase.database.Exclude

data class Attendance (
    var employeeID: String? = "",
    var time : String? = "",
    var location: String? = "",
    var inout: Boolean? = false
)
{
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "employeeID" to employeeID,
            "time" to time,
            "location" to location,
            "inout" to inout
        )
    }

}
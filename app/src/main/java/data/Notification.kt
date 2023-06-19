package data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notification_table")
data class Notification (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "notifID")
    val notifID : Int,

    @ColumnInfo(name = "title")
    val title : String,

    @ColumnInfo(name = "body")
    val body : String
)
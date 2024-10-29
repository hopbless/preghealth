package com.hopeinyang.preg_health.data.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.toObject
import java.time.LocalDateTime
import java.time.ZonedDateTime

@Entity(tableName = "heartrate")
data class HeartRate(
    @PrimaryKey(autoGenerate = false)
    var id:String = " ",
    var heartRateTime: String =" ",
    var heartRateDate: String = " ",
    var heartRate: String = " ",
    ){

    companion object{
        fun toObject(doc: DocumentSnapshot):HeartRate?{
            val item = doc.toObject<HeartRate>()
            item?.id = doc.id
            return item
        }
    }
}

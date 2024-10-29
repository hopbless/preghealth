package com.hopeinyang.preg_health.data.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.toObject
import java.time.LocalDateTime
import java.time.ZonedDateTime

@Entity(tableName = "bloodpressure")
data class BloodPressure(
    @PrimaryKey(autoGenerate = false)
    var id: String = " ",
    var bloodPressureDate: String = " ",
    var bloodPressureTime: String = " ",
    var diastolic: String = "40",
    var systolic: String = "80",
    ){

    companion object{
        fun toObject(doc: DocumentSnapshot):BloodPressure?{
            val item = doc.toObject<BloodPressure>()
            item?.id = doc.id
            return item
        }
    }
}

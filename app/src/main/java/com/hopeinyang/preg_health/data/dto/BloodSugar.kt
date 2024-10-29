package com.hopeinyang.preg_health.data.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.toObject
import java.time.LocalDateTime
import java.time.ZonedDateTime


@Entity(tableName = "bloodsugar")
data class BloodSugar(
    @PrimaryKey(autoGenerate = false)
    var id:String = " ",
    var bloodSugarTime: String = " ",
    var bloodSugarDate: String = " ",
    var bloodSugar: String = " ",

    ){

    companion object{
        fun toObject(doc: DocumentSnapshot):BloodSugar?{
            val item = doc.toObject<BloodSugar>()
            item?.id = doc.id
            return item
        }
    }

}

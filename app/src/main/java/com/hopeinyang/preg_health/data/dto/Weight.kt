package com.hopeinyang.preg_health.data.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.toObject
import java.time.*

@Entity(tableName = "weight")
data class Weight(
    @PrimaryKey(autoGenerate = false)
    @DocumentId
    var id : String = " ",
    var weightTime : String = " ",
    var weightDate : String = " ",
    var weight: String = "20.0",

){
    companion object{
        fun toObject(doc:DocumentSnapshot):Weight?{
            val item = doc.toObject<Weight>()
            item?.id = doc.id
            return item
        }
    }
}

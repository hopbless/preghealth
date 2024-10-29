package com.hopeinyang.preg_health.data.dto

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.toObject

data class BodyTemp(
    var id:String = " ",
    var bodyTempTime: String = " ",
    var bodyTempDate: String = " ",
    var bodyTemp: String = " ",

) {

    companion object {
        fun toObject(doc: DocumentSnapshot): BodyTemp? {
            val item = doc.toObject<BodyTemp>()
            item?.id = doc.id
            return item
        }
    }
}

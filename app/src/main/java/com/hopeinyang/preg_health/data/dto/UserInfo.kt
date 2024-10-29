package com.hopeinyang.preg_health.data.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.toObject
@Entity(tableName = "userInfo")
data class UserInfo (
    @PrimaryKey(autoGenerate = false)
    var id: String = " ",
    var userId: String? = " ",
    var emailAddress: String? =" ",
    var firstName: String? =" ",
    var lastName: String? = " ",
    var height: String = " ",
    var age: String = " ",
    var address:  String? = " ",
    var phoneNumber: String? = " ",
    var doctorSelected:Boolean = false,
    var doctorAccount: Boolean = false,
    var doctorCode: String = " ",
    var hospitalName: String = " ",
    var accountActive: Boolean = true,
    var doctorUID: String = " ",
    var result:String = " ",
    var pregnancyWeek:String = " "
){
    companion object{
        fun toObject(doc: DocumentSnapshot):UserInfo?{
            val item = doc.toObject<UserInfo>()
            item?.userId = doc.id
            return item
        }
    }
}
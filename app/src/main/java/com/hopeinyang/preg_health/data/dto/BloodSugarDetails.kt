package com.hopeinyang.preg_health.data.dto

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

data class BloodSugarDetails(

    var dateOfBloodSugar: String = " ",
    var timeOfBloodSugar: String = " ",
    var bloodSugarValue: Int = 0


){

}

package com.hopeinyang.preg_health.common.ext

import android.util.Patterns
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.util.*
import java.util.regex.Pattern

private const val MIN_PASS_LENGTH = 6
private const val PASS_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{4,}$"
private val listOfCode = listOf("doc3-5-W-Oinv", "docL-4-J-Einv", "docf-c-8-Minv", "docL-N-v-Uinv",
    "docP-s-U-Hinv", "docI-E-3-hinv", "docI-a-l-qinv", "doc0-O-R-0inv", "docD-D-4-linv", "docu-X-z-Vinv",
    "docM-l-g-Ninv", "docL-Y-Y-Hinv", "docz-j-0-Uinv", "docc-e-v-cinv", "docP-J-Q-Kinv", "doci-Q-S-3inv",
    "docT-J-Q-linv", "docq-Z-r-kinv", "docT-6-A-4inv", "docW-s-p-Sinv")

fun String.isValidEmail(): Boolean {
    return this.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isValidPassword(): Boolean {
    return this.isNotBlank() &&
            this.length >= MIN_PASS_LENGTH &&
            Pattern.compile(PASS_PATTERN).matcher(this).matches()
}

fun String.passwordMatches(repeated: String): Boolean {
    return this == repeated
}

fun String.isValidAge(): Boolean{
    return this.isNotBlank() && this.toInt() > 18
}

fun String.isValidWeekNo(): Boolean{
    return this.isNotBlank() && this.toInt() > 0
}

fun String.isValidPhoneNumber(): Boolean{
    return  this.isNotBlank() &&
            this.length == 11
}

fun String.isValidHeight(): Boolean{
    return this.isNotBlank() &&
            this.toFloat() <= 3.0 &&
            this.toFloat() >= 1.0
}

fun String.isValidFirstName(): Boolean{
    return  this.isNotBlank() &&
            this.length > 2
}

fun String.isValidLastName(): Boolean{
    return  this.isNotBlank() &&
            this.length > 2
}

fun String.isValidAddress(): Boolean{
    return  this.isNotBlank() &&
            this.length > 2
}

fun String.isValidHospitalName(): Boolean{
    return  this.length > 3 && this.isNotBlank()
}

fun String.isValidDoctorCode(): Boolean{
    val foundValue = listOfCode.find { item-> item == this }
    return this.isNotBlank() && foundValue != null
}

fun String.toDate(): LocalDate {
    val dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    return LocalDate.parse(this, dateTimeFormatter)
}

fun String.toTime():LocalTime{
    val dateTimeFormatter = DateTimeFormatterBuilder()
        .parseCaseInsensitive().appendPattern("hh:mm a").toFormatter()
    return LocalTime.parse(this, dateTimeFormatter)

}
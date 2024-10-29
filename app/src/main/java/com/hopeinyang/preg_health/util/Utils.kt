package com.hopeinyang.preg_health.util

import androidx.compose.ui.graphics.Color
import com.hopeinyang.preg_health.common.ext.roundTo
import com.hopeinyang.preg_health.data.dto.Weight
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


object Utils {

     fun fromDateTimeToSeconds(dateTime: ZonedDateTime):Long{
         return dateTime.toEpochSecond()

     }

    fun fromDateTimeToDate(dateTime: ZonedDateTime): String{
        return dateTime.toLocalDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))

    }

    fun fromDateTimeToTime(dateTime: ZonedDateTime): String{
        return dateTime.toLocalTime().format(DateTimeFormatter.ofPattern("hh:mm a"))

    }

    fun getWeightResult(weightValue: String, height:Float):Map<String, Color>{
        val weightResult : MutableMap<String, Color> = mutableMapOf()
        if (weightValue.isNotEmpty()){

            val bmi = (weightValue.toFloat()/(height*height))
            val bmiFloat = bmi.roundTo(1)
            if (bmiFloat < 18.5f){
                weightResult["Low"] = Color.Blue
            }else if (bmiFloat in 18.5f .. 25.0f){
                weightResult["Normal"] = Color.Green

            }else{
                weightResult ["High"] = Color.Red
            }
        return weightResult
        }else{
            weightResult[" "] = Color.Transparent
            return weightResult
        }

    }

    fun getBloodPressureResult(diastolic:String, systolic:String):Map<String, Color>{
        val bpResult: MutableMap<String, Color> = mutableMapOf()
        if (diastolic.isNotEmpty() && systolic.isNotEmpty() && systolic != "--"){
            if (diastolic.toInt() >= 90 && systolic.toInt() >= 140  ){
                bpResult["High"] = Color.Red
            }else if(diastolic.toInt() in 60..90 && systolic.toInt() in 90 .. 140 ){
                bpResult["Normal"] = Color.Green

            }else if (diastolic.toInt() < 60 && systolic.toInt() <= 90 ){
                bpResult["Low"] = Color.Blue

            }else{
                bpResult["  "] = Color.Transparent

            }
            return bpResult
        }else{
            bpResult["  "] = Color.Transparent
            return bpResult

        }
    }

    fun getBloodSugarResult(bs:String):Map<String, Color>{
        val bsResult: MutableMap<String, Color> = mutableMapOf()
        if (bs.isNotEmpty() && bs != "--"){
            if (bs.toFloat() in 5.3f .. 7.8f  ){
                bsResult["Normal"] = Color.Green
            }else if(bs.toFloat() > 7.8f ){
                bsResult["High"] = Color.Red

            }else if (bs.toFloat() < 5.3f ){
                bsResult["Low"] = Color.Blue

            }else{
                bsResult[" "] = Color.Transparent

            }
            return bsResult
        }else{
            bsResult[" "] = Color.Transparent
            return bsResult
        }

    }

    fun getHeartResult(hr:String):Map<String, Color>{
        val hrResult: MutableMap<String, Color> = mutableMapOf()
        if (hr.isNotEmpty() && hr != "--"){
            if (hr.toInt() in 55 .. 80  ){
                hrResult["Normal"] = Color.Green
            }else if(hr.toInt() > 80 ){
                hrResult["Fast"] = Color.Red

            }else if (hr.toInt() < 55 ){
                hrResult["Slow"] = Color.Blue

            }else{
                hrResult[" "] = Color.Transparent

            }
            return hrResult
        }else {
            hrResult[" "] = Color.Transparent
            return hrResult

        }

    }

    fun getBodyTempResult(bt:String):Map<String, Color>{
        val btResult: MutableMap<String, Color> = mutableMapOf()
        if (bt.isNotEmpty() && bt != "--"){
            if (bt.toFloat() in 32.5f .. 37.5f  ){
                btResult["Normal"] = Color.Green
            }else if(bt.toFloat() > 37.5f ){
                btResult["High"] = Color.Red

            }else if (bt.toFloat() < 32.5f ){
                btResult["Low"] = Color.Blue

            }else{
                btResult[" "] = Color.Transparent

            }
            return btResult
        }else{
            btResult[" "] = Color.Transparent
            return btResult

        }

    }

    fun getPredictionResult(prediction:String):Map<String, Color>{
        val predictionResult = mutableMapOf<String, Color>()
        when (prediction) {
            "High Risk" -> {
                predictionResult[prediction] = Color.Red
            }
            "Low Risk" -> {
                predictionResult[prediction] = Color.Green
            }
            else -> {
                predictionResult["Undefined"] = Color.Transparent
            }
        }

        return predictionResult

    }

}
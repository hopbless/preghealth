package com.hopeinyang.preg_health.util

//fun Weight.toWeightItemList(weight: List<Weight>):ArrayList<WeightDetails>{
//    var weightDetails = WeightDetails()
//    val weightDetailList = ArrayList<WeightDetails>()
//
//    weight.forEach {weight ->
//        weightDetails.also {
//            val instant = Instant.ofEpochSecond(weight.weightTimestamp)
//            it.dateOfWeight = LocalDateTime.ofInstant(instant,
//                ZoneId.systemDefault()).toLocalDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
//            it.timeOfWeight = LocalDateTime.ofInstant(instant,
//                ZoneId.systemDefault()).toLocalTime().format(DateTimeFormatter.ofPattern("hh:mm a"))
//            it.weightValue = weight.weight
//        }
//
//        weightDetailList.add(weightDetails)
//
//    }
//
//    return weightDetailList
//
//}
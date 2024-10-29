package com.hopeinyang.preg_health.components

sealed class Screen(val route: String){
    object HomeScreen : Screen("home_screen")
    object WeightDetailScreen: Screen("weight_detail_screen")
    object WeightMeasurement:Screen("weight_measurement")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg->
                append("/$arg")
            }
        }
    }
}
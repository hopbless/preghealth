package com.hopeinyang.preg_health.common

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource


sealed class AppText{
    data class StringValue(val value: String): AppText()

    class StringResource(
        @StringRes val resId: Int,
        vararg val args: Any
    ): AppText()

    @Composable
    fun asString():String{
        return when(this){
            is StringValue -> value
            is StringResource -> {
                stringResource(resId, *args)
            }
        }
    }


}

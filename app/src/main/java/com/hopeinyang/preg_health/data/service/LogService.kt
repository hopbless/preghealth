package com.hopeinyang.preg_health.data.service

interface LogService {

    fun logNonFatalCrash(throwable: Throwable)
}
package com.hopeinyang.preg_health.data.dao.offline

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hopeinyang.preg_health.data.dao.PreHealthDAO
import com.hopeinyang.preg_health.data.dto.*

@Database(entities =
    [UserInfo::class, Weight::class, BloodSugar:: class, BloodPressure::class, HeartRate :: class],
    version = 4,
    exportSchema = false
)
abstract class PreHealthDatabase : RoomDatabase() {
    abstract val preHealthDAO: PreHealthDAO

    companion object{
        @Volatile
        private var Instance: PreHealthDatabase? = null

        fun getInstance(context: Context): PreHealthDatabase {

            return Instance ?: synchronized(this){
                Room.databaseBuilder(context, PreHealthDatabase::class.java, "pre_health_database")
                    .fallbackToDestructiveMigration()
                    .build().also { Instance = it }
            }

        }
    }

}
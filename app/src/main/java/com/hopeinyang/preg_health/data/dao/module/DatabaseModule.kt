package com.hopeinyang.preg_health.data.dao.module

import android.content.Context
import com.hopeinyang.preg_health.data.dao.PreHealthDAO
import com.hopeinyang.preg_health.data.dao.PreHealthRepository
import com.hopeinyang.preg_health.data.dao.impl.PreHealthDBRepositoryImpl
import com.hopeinyang.preg_health.data.dao.offline.PreHealthDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class RoomDatabaseService  {

    @Provides
    @ViewModelScoped
    fun providesPreHealthDAO(@ApplicationContext appContext: Context): PreHealthDAO {
        return PreHealthDatabase.getInstance(appContext).preHealthDAO
    }


    @Provides
    @ViewModelScoped
    fun providesPreHealthDBRepository(dao: PreHealthDAO) : PreHealthRepository{
        return PreHealthDBRepositoryImpl(dao)
    }
}




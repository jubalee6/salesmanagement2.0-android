package com.intravan.salesmanagement.data.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.intravan.salesmanagement.core.util.DebugLog
import com.intravan.salesmanagement.data.local.dao.CompanyDao
import com.intravan.salesmanagement.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val APP_DATABASE_NAME = "intravan_salesmanagement_db"

@Module
@InstallIn(SingletonComponent::class)
object AppDatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            APP_DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    DebugLog.i { ">>>>> Room onCreate" }
                }

                override fun onOpen(db: SupportSQLiteDatabase) {
                    super.onOpen(db)
                    DebugLog.i { ">>>>> Room onOpen" }
                }
            })
            .build()
    }

    @Singleton
    @Provides
    fun provideCompanyDao(database: AppDatabase): CompanyDao {
        return database.CompanyDao()
    }
}
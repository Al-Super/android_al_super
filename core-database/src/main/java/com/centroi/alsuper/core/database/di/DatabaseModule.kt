/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.centroi.alsuper.core.database.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import com.centroi.alsuper.core.database.AppDatabase
import com.centroi.alsuper.core.database.LandingPageDao
import com.centroi.alsuper.core.database.tables.CartItemDao
import com.centroi.alsuper.core.database.tables.EmergencyContactDao
import com.centroi.alsuper.core.database.tables.LocationDao
import com.centroi.alsuper.core.database.tables.UserIDDao
import com.centroi.alsuper.core.database.tables.UserSessionDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideLandingPageDao(appDatabase: AppDatabase): LandingPageDao {
        return appDatabase.landingPageDao()
    }

    @Singleton
    @Provides
    fun provideEmergencyContactDao(appDatabase: AppDatabase): EmergencyContactDao {
        return appDatabase.emergencyContactDao()
    }

    @Singleton
    @Provides
    fun provideLocationDao(appDatabase: AppDatabase): LocationDao {
        return appDatabase.locationDao()
    }

    @Provides
    @Singleton
    fun provideUserSessionDao(appDatabase: AppDatabase): UserSessionDao {
        return appDatabase.userSessionDao()
    }

    @Provides
    @Singleton
    fun provideUserId(appDatabase: AppDatabase): UserIDDao {
        return appDatabase.userIdDao()
    }

    @Provides
    @Singleton
    fun provideCartItemDao(appDatabase: AppDatabase): CartItemDao {
        return appDatabase.cartItemDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    """
                         CREATE TABLE IF NOT EXISTS CartItem (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                productId TEXT NOT NULL,
                name TEXT NOT NULL,
                price REAL NOT NULL,
                imageUrl TEXT,
                quantity INTEGER NOT NULL
            )
        """.trimIndent()
                )
            }
        }
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "LandingPage"
        )
            .addMigrations(MIGRATION_3_4)
            .build()
    }
}

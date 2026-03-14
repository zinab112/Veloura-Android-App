package com.zinab.veloura2.data.local


import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.zinab.veloura2.data.local.dao.CartDao
import com.zinab.veloura2.data.local.entity.CartItemEntity

@Database(
    entities = [CartItemEntity::class],
    version = 1,
    exportSchema = false
)
abstract class VelouraDatabase : RoomDatabase() {

    abstract fun cartDao(): CartDao

    companion object {
        @Volatile
        private var INSTANCE: VelouraDatabase? = null

        fun getInstance(context: Context): VelouraDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VelouraDatabase::class.java,
                    "veloura_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
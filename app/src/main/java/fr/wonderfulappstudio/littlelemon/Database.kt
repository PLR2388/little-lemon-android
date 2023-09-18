package fr.wonderfulappstudio.littlelemon

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Update

@Database(entities = [MenuItem::class], version = 1, exportSchema = false)
abstract class LittleLemonDatabase: RoomDatabase() {
    abstract fun menuItemDao(): MenuItemDao

    companion object {
        @Volatile
        private var Instance: LittleLemonDatabase? = null

        fun getDatabase(context: Context): LittleLemonDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, LittleLemonDatabase::class.java, "little_lemon_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}

@Dao
interface MenuItemDao {
    @Query("SELECT * FROM menu_item")
    fun getMenuItems(): LiveData<List<MenuItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(menuItem: MenuItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(menuItem: List<MenuItem>)

    @Update
    fun update(menuItem: MenuItem)

    @Delete
    fun delete(menuItem: MenuItem)
}

@Entity(tableName = "menu_item")
data class MenuItem(
    @PrimaryKey
    val id: Int,
    val title: String,
    val description: String,
    val price: String,
    val image: String,
    val category: String
)
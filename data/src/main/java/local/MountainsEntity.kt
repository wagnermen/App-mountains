package local

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.remote.model.response.MountainsResponseItem

@Entity(tableName = "mountains")
data class MountainsEntity(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "name") val name: String? = "",
    @ColumnInfo(name = "image") val image: String = "",
    @ColumnInfo(name = "country") val country: String? = "",

){
    companion object {
        fun MountainsResponseItem.toMountainsDataEntity(): MountainsEntity {
            return MountainsEntity(
                id = _id,
                name = name,
                image = mountain_img,
                country = location,
            )
        }
    }
}

@Database(entities = [MountainsEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mountainsDao(): MountainsDao
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

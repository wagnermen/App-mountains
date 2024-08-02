package local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MountainsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMountains(mountainsDataEntity: List<MountainsEntity>)

    @Query("SELECT * FROM mountains")
    fun getMountains(): List<MountainsEntity>

    @Query("DELETE FROM mountains")
    fun deleteAllMountains()
}
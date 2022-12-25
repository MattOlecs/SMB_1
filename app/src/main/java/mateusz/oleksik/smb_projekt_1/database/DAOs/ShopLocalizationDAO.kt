package mateusz.oleksik.smb_projekt_1.database.DAOs

import androidx.lifecycle.LiveData
import androidx.room.*
import mateusz.oleksik.smb_projekt_1.models.ShopLocalization

@Dao
interface ShopLocalizationDAO {

    @Query("select * from ShopLocalization")
    fun getAll(): LiveData<List<ShopLocalization>>

    @Insert
    fun insert(shoppingItem: ShopLocalization) : Long

    @Update
    fun update(shoppingItem: ShopLocalization)

    @Delete
    fun delete(shoppingItem: ShopLocalization)
}
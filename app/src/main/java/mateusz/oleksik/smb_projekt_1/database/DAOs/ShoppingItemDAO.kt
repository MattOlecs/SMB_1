package mateusz.oleksik.smb_projekt_1.database.DAOs

import androidx.lifecycle.LiveData
import androidx.room.*
import mateusz.oleksik.smb_projekt_1.models.ShoppingItem

@Dao
interface ShoppingItemDAO {

    @Query("select * from ShoppingItems")
    fun getAll(): LiveData<List<ShoppingItem>>

    @Insert
    fun insert(shoppingItem: ShoppingItem)

    @Update
    fun update(shoppingItem: ShoppingItem)

    @Delete
    fun delete(shoppingItem: ShoppingItem)
}
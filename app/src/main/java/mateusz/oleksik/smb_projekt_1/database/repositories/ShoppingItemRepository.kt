package mateusz.oleksik.smb_projekt_1.database.repositories

import androidx.lifecycle.LiveData
import mateusz.oleksik.smb_projekt_1.database.DAOs.ShoppingItemDAO
import mateusz.oleksik.smb_projekt_1.models.ShoppingItem

class ShoppingItemRepository(private val shoppingItemDAO: ShoppingItemDAO) {
    val allShoppingItems: LiveData<List<ShoppingItem>> = shoppingItemDAO.getAll()

    fun insert(shoppingItem: ShoppingItem) : Long{
        return shoppingItemDAO.insert(shoppingItem)
    }
    fun update(shoppingItem: ShoppingItem){
        shoppingItemDAO.update(shoppingItem)
    }
    fun delete(shoppingItem: ShoppingItem){
        shoppingItemDAO.delete(shoppingItem)
    }
}
package mateusz.oleksik.smb_projekt_1.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import mateusz.oleksik.smb_projekt_1.database.ShoppingListDatabase
import mateusz.oleksik.smb_projekt_1.database.repositories.ShoppingItemRepository
import mateusz.oleksik.smb_projekt_1.models.ShoppingItem

class ShoppingItemViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ShoppingItemRepository
    val shoppingItems: LiveData<List<ShoppingItem>>

    init {
        val shoppingItemDAO = ShoppingListDatabase.getDatabaseInstance(application).shoppingItemDAO()
        repository = ShoppingItemRepository(shoppingItemDAO)
        shoppingItems = repository.allShoppingItems
    }

    fun insert(shoppingItem: ShoppingItem) = repository.insert(shoppingItem)
    fun update(shoppingItem: ShoppingItem) = repository.update(shoppingItem)
    fun delete(shoppingItem: ShoppingItem) = repository.delete(shoppingItem)
}
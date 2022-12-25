package mateusz.oleksik.smb_projekt_1.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import mateusz.oleksik.smb_projekt_1.database.ShoppingListDatabase
import mateusz.oleksik.smb_projekt_1.database.repositories.ShopLocalizationRepository
import mateusz.oleksik.smb_projekt_1.models.ShopLocalization

class ShopLocalizationViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ShopLocalizationRepository
    val shopsLocalizations: LiveData<List<ShopLocalization>>

    init {
        val shoppingItemDAO = ShoppingListDatabase.getDatabaseInstance(application).shopLocalizationDAO()
        repository = ShopLocalizationRepository(shoppingItemDAO)
        shopsLocalizations = repository.allShops
    }

    fun insert(shopLocalization: ShopLocalization) = repository.insert(shopLocalization)
    fun update(shopLocalization: ShopLocalization) = repository.update(shopLocalization)
    fun delete(shopLocalization: ShopLocalization) = repository.delete(shopLocalization)
}
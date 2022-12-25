package mateusz.oleksik.smb_projekt_1.database.repositories

import androidx.lifecycle.LiveData
import mateusz.oleksik.smb_projekt_1.database.DAOs.ShopLocalizationDAO
import mateusz.oleksik.smb_projekt_1.models.ShopLocalization

class ShopLocalizationRepository(private val shopLocalizationDAO: ShopLocalizationDAO) {
    val allShops: LiveData<List<ShopLocalization>> = shopLocalizationDAO.getAll()

    fun insert(shopLocalization: ShopLocalization) : Long{
        return shopLocalizationDAO.insert(shopLocalization)
    }
    fun update(shopLocalization: ShopLocalization){
        shopLocalizationDAO.update(shopLocalization)
    }
    fun delete(shopLocalization: ShopLocalization){
        shopLocalizationDAO.delete(shopLocalization)
    }
}
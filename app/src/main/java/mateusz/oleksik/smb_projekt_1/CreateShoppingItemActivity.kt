package mateusz.oleksik.smb_projekt_1

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import mateusz.oleksik.smb_projekt_1.Extensions.Companion.round
import mateusz.oleksik.smb_projekt_1.database.ShoppingListDatabase
import mateusz.oleksik.smb_projekt_1.database.repositories.ShoppingItemRepository
import mateusz.oleksik.smb_projekt_1.databinding.ActivityUpsertShoppingItemBinding
import mateusz.oleksik.smb_projekt_1.models.ShoppingItem

class CreateShoppingItemActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityUpsertShoppingItemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityUpsertShoppingItemBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        _binding.confirmAddItemButton.setOnClickListener {
            createShoppingItem()
        }
        _binding.cancelAddItem.setOnClickListener {
            finish()
        }
    }

    private fun createShoppingItem(){
        val name = _binding.itemNameText.text.toString()
        val amount = Integer.parseInt(_binding.amountTextNumber.text.toString())
        val price = _binding.priceTextNumber.text.toString().toDouble().round(2)
        val isBought = _binding.checkBox.isChecked

        val createdItem = ShoppingItem(0, name, price, amount, isBought)
        val shoppingItemDAO = ShoppingListDatabase.getDatabaseInstance(application).shoppingItemDAO()
        val repository = ShoppingItemRepository(shoppingItemDAO)
        val id = repository.insert(createdItem)
        sendNewItemBroadcast(createdItem, id.toInt())

        finish()
        val listActivity = Intent(applicationContext, ShoppingListActivity::class.java)
        startActivity(listActivity)
    }

    private fun sendNewItemBroadcast(item: ShoppingItem, itemId: Int) {
        val broadcastIntent = Intent("mateusz.oleksik.SHOPPING_ITEM_INTENT")
        broadcastIntent.putExtra(Constants.ItemIDExtraID, itemId)
        broadcastIntent.putExtra(Constants.ItemNameExtraID, item.name)
        broadcastIntent.putExtra(Constants.ItemAmountExtraID, item.amount)
        broadcastIntent.putExtra(Constants.ItemPriceExtraID, item.price)
        broadcastIntent.putExtra(Constants.ItemIsBoughtExtraID, item.isBought)
        broadcastIntent.component = ComponentName(
            "mateusz.oleksik.smb_projekt_2",
            "mateusz.oleksik.smb_projekt_2.ShoppingItemCreationReceiver")
        sendOrderedBroadcast(broadcastIntent, "mateusz.oleksik.SHOPPING_ITEMS_PERMISSIONS")
    }
}
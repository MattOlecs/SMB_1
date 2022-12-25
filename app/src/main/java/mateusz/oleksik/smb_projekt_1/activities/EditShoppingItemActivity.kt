package mateusz.oleksik.smb_projekt_1.activities

import android.content.Intent
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import mateusz.oleksik.smb_projekt_1.common.Constants
import mateusz.oleksik.smb_projekt_1.common.Extensions.Companion.round
import mateusz.oleksik.smb_projekt_1.database.ShoppingListDatabase
import mateusz.oleksik.smb_projekt_1.database.repositories.ShoppingItemRepository
import mateusz.oleksik.smb_projekt_1.databinding.ActivityUpsertShoppingItemBinding
import mateusz.oleksik.smb_projekt_1.models.ShoppingItem

class EditShoppingItemActivity(): AppCompatActivity() {
    private lateinit var _binding: ActivityUpsertShoppingItemBinding
    private lateinit var shoppingItem: ShoppingItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityUpsertShoppingItemBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        val intent = intent
        shoppingItem = ShoppingItem(
            intent.getIntExtra(Constants.ItemIDExtraID, 0),
            intent.getStringExtra(Constants.ItemNameExtraID),
            intent.getDoubleExtra(Constants.ItemPriceExtraID, 0.0),
            intent.getIntExtra(Constants.ItemAmountExtraID, 0),
            intent.getBooleanExtra(Constants.ItemIsBoughtExtraID, false)
        )

        fillUIWithData()

        _binding.confirmAddItemButton.setOnClickListener {
            editShoppingItem()
        }
        _binding.cancelAddItem.setOnClickListener {
            finish()
        }
    }

    private fun fillUIWithData(){
        _binding.itemNameText.setText(shoppingItem.name)
        _binding.amountTextNumber.setText(shoppingItem.amount.toString())
        _binding.priceTextNumber.setText(shoppingItem.price.round(2).toString())
        _binding.checkBox.isChecked = shoppingItem.isBought
    }

    private fun editShoppingItem(){
        val name = _binding.itemNameText.text.toString()
        val amount = Integer.parseInt(_binding.amountTextNumber.text.toString())
        val price = _binding.priceTextNumber.text.toString().toDouble().round(2)
        val isBought = _binding.checkBox.isChecked

        val updatedItem = ShoppingItem(shoppingItem.id, name, price, amount, isBought)

        val shoppingItemDAO = ShoppingListDatabase.getDatabaseInstance(application).shoppingItemDAO()
        val repository = ShoppingItemRepository(shoppingItemDAO)
        repository.update(updatedItem)
        finish()
        val listActivity = Intent(applicationContext, ShoppingListActivity::class.java)
        startActivity(listActivity)
    }
}
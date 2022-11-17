package mateusz.oleksik.smb_projekt_1

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import mateusz.oleksik.smb_projekt_1.adapters.ShoppingItemAdapter
import mateusz.oleksik.smb_projekt_1.databinding.ActivityShoppingListBinding
import mateusz.oleksik.smb_projekt_1.interfaces.IClickedShoppingItemListener
import mateusz.oleksik.smb_projekt_1.interfaces.ICreatedShoppingItemFragmentListener
import mateusz.oleksik.smb_projekt_1.models.ShoppingItem
import mateusz.oleksik.smb_projekt_1.viewModels.ShoppingItemViewModel

class ShoppingListActivity : AppCompatActivity(), ICreatedShoppingItemFragmentListener, IClickedShoppingItemListener {
    private lateinit var shoppingItemViewModel: ShoppingItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityShoppingListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.shoppingListRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.shoppingListRecyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL)
        )

        binding.addShoppingItemButton.setOnClickListener {
            openCreateShoppingItemActivity()
        }

        shoppingItemViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(ShoppingItemViewModel::class.java)
        binding.shoppingListRecyclerView.adapter = ShoppingItemAdapter(this, shoppingItemViewModel, this)
        shoppingItemViewModel.shoppingItems.observe(this, Observer { items ->
            items?.let{
                (binding.shoppingListRecyclerView.adapter as ShoppingItemAdapter).setData(it)
            }
        })
    }
    
    private fun openCreateShoppingItemActivity(){
        val createActivityIntent = Intent(this, CreateShoppingItemActivity::class.java)
        startActivity(createActivityIntent)
    }

    override fun onClickedShoppingItem(item: ShoppingItem) {
        val editActivityIntent = Intent(this, EditShoppingItemActivity::class.java)
        editActivityIntent.putExtra(Constants.ItemIDExtraID, item.id)
        editActivityIntent.putExtra(Constants.ItemNameExtraID, item.name)
        editActivityIntent.putExtra(Constants.ItemPriceExtraID, item.price)
        editActivityIntent.putExtra(Constants.ItemAmountExtraID, item.amount)
        editActivityIntent.putExtra(Constants.ItemIsBoughtExtraID, item.isBought)

        startActivity(editActivityIntent)
    }

    override fun onCreatedShoppingItem(item: ShoppingItem) {
        val id = shoppingItemViewModel.insert(item).toInt()
        val broadcastIntent = Intent("mateusz.oleksik.SHOPPING_ITEM_INTENT")
        broadcastIntent.putExtra(Constants.ItemIDExtraID, id)
        broadcastIntent.putExtra(Constants.ItemNameExtraID, item.name)
        broadcastIntent.putExtra(Constants.ItemAmountExtraID, item.amount)
        broadcastIntent.putExtra(Constants.ItemPriceExtraID, item.price)
        broadcastIntent.putExtra(Constants.ItemIsBoughtExtraID, item.isBought)
        sendOrderedBroadcast(broadcastIntent, "mateusz.oleksik.SHOPPING_ITEMS_PERMISSIONS")
    }
}
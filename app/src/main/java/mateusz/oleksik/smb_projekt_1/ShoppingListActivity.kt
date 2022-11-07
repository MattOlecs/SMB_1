package mateusz.oleksik.smb_projekt_1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import mateusz.oleksik.smb_projekt_1.adapters.ShoppingItemAdapter
import mateusz.oleksik.smb_projekt_1.databinding.ActivityShoppingListBinding
import mateusz.oleksik.smb_projekt_1.fragments.CreateShoppingItemFragment
import mateusz.oleksik.smb_projekt_1.fragments.EditShoppingItemFragment
import mateusz.oleksik.smb_projekt_1.interfaces.IClickedShoppingItemListener
import mateusz.oleksik.smb_projekt_1.interfaces.ICreatedShoppingItemFragmentListener
import mateusz.oleksik.smb_projekt_1.interfaces.IEditedShoppingItemFragmentListener
import mateusz.oleksik.smb_projekt_1.models.ShoppingItem
import mateusz.oleksik.smb_projekt_1.viewModels.ShoppingItemViewModel

class ShoppingListActivity : AppCompatActivity(), ICreatedShoppingItemFragmentListener, IEditedShoppingItemFragmentListener, IClickedShoppingItemListener {

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
            openCreateShoppingItemDialog()
        }

        shoppingItemViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(ShoppingItemViewModel::class.java)
        binding.shoppingListRecyclerView.adapter = ShoppingItemAdapter(this, shoppingItemViewModel, this)
        shoppingItemViewModel.shoppingItems.observe(this, Observer { items ->
            items?.let{
                (binding.shoppingListRecyclerView.adapter as ShoppingItemAdapter).setData(it)
            }
        })
    }

    private fun openCreateShoppingItemDialog(){
        val dialog = CreateShoppingItemFragment(this)
        dialog.show(supportFragmentManager, "createItemDialog")
    }

    override fun onClickedShoppingItem(item: ShoppingItem) {
        val dialog = EditShoppingItemFragment(this, item)
        dialog.show(supportFragmentManager, "editItemDialog")
    }

    override fun onCreatedShoppingItem(item: ShoppingItem) {
        shoppingItemViewModel.insert(item)
    }

    override fun onEditedShoppingItem(item: ShoppingItem) {
        shoppingItemViewModel.update(item)
    }
}
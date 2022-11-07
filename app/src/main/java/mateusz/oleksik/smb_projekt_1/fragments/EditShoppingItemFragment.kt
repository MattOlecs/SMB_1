package mateusz.oleksik.smb_projekt_1.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import mateusz.oleksik.smb_projekt_1.databinding.FragmentCreateShoppingItemBinding
import mateusz.oleksik.smb_projekt_1.interfaces.IEditedShoppingItemFragmentListener
import mateusz.oleksik.smb_projekt_1.models.ShoppingItem

class EditShoppingItemFragment(val listener: IEditedShoppingItemFragmentListener, val shoppingItem: ShoppingItem) : DialogFragment() {
    private lateinit var _binding: FragmentCreateShoppingItemBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCreateShoppingItemBinding.inflate(inflater, container, false)
        fillUIWithData()

        _binding.confirmAddItemButton.setOnClickListener {
            editShoppingItem()
        }
        _binding.cancelAddItem.setOnClickListener {
            dismiss()
        }

        return _binding.root
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

        listener.onEditedShoppingItem(ShoppingItem(shoppingItem.id, name, price, amount, isBought))
        dismiss()
    }

    private fun Double.round(decimals: Int): Double {
        var multiplier = 1.0
        repeat(decimals) { multiplier *= 10 }
        return kotlin.math.round(this * multiplier) / multiplier
    }
}
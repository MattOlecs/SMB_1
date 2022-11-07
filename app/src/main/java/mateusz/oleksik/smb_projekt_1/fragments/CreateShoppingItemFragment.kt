package mateusz.oleksik.smb_projekt_1.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import mateusz.oleksik.smb_projekt_1.databinding.FragmentCreateShoppingItemBinding
import mateusz.oleksik.smb_projekt_1.interfaces.ICreatedShoppingItemFragmentListener
import mateusz.oleksik.smb_projekt_1.models.ShoppingItem
import kotlin.math.round

class CreateShoppingItemFragment(private val listener: ICreatedShoppingItemFragmentListener) : DialogFragment() {

    private lateinit var _binding: FragmentCreateShoppingItemBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCreateShoppingItemBinding.inflate(inflater, container, false)
        _binding.confirmAddItemButton.setOnClickListener {
            createShoppingItem()
        }
        _binding.cancelAddItem.setOnClickListener {
            dismiss()
        }

        return _binding.root
    }

    private fun createShoppingItem(){
        val name = _binding.itemNameText.text.toString()
        val amount = Integer.parseInt(_binding.amountTextNumber.text.toString())
        val price = _binding.priceTextNumber.text.toString().toDouble().round(2)
        val isBought = _binding.checkBox.isChecked

        listener.onCreatedShoppingItem(ShoppingItem(0, name, price, amount, isBought))
        dismiss()
    }

    private fun Double.round(decimals: Int): Double {
        var multiplier = 1.0
        repeat(decimals) { multiplier *= 10 }
        return round(this * multiplier) / multiplier
    }
}
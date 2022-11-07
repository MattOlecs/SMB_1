package mateusz.oleksik.smb_projekt_1.adapters

import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import mateusz.oleksik.smb_projekt_1.Constants
import mateusz.oleksik.smb_projekt_1.databinding.ShoppingItemsListElementBinding
import mateusz.oleksik.smb_projekt_1.interfaces.IClickedShoppingItemListener
import mateusz.oleksik.smb_projekt_1.interfaces.IEditedShoppingItemFragmentListener
import mateusz.oleksik.smb_projekt_1.models.ShoppingItem
import mateusz.oleksik.smb_projekt_1.viewModels.ShoppingItemViewModel

class ShoppingItemAdapter(val context: Context, val shoppingItemViewModel: ShoppingItemViewModel, val clickListener: IClickedShoppingItemListener)
    : RecyclerView.Adapter<ShoppingItemAdapter.ShoppingItemViewHolder>() {

    private var shoppingItems = emptyList<ShoppingItem>()

    inner class ShoppingItemViewHolder(val binding: ShoppingItemsListElementBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingItemViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding = ShoppingItemsListElementBinding.inflate(inflater, parent, false)
        return ShoppingItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShoppingItemViewHolder, position: Int) {
        val shoppingItem = shoppingItems[position]
        holder.binding.shoppingItem = shoppingItem
        loadSharedPreferencesLayoutOptions(holder)

        holder.itemView.setOnClickListener {
            clickListener.onClickedShoppingItem(shoppingItem)
        }
        holder.binding.deleteButton.setOnClickListener {
            shoppingItemViewModel.delete(shoppingItem)
            Toast.makeText(
                context,
                "Removed ${shoppingItem.name} from list",
                Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun getItemCount(): Int {
        return this.shoppingItems.size
    }

    internal fun setData(shoppingItems: List<ShoppingItem>){
        this.shoppingItems = shoppingItems
        notifyDataSetChanged()
    }

    private fun loadSharedPreferencesLayoutOptions(holder: ShoppingItemViewHolder){
        val sharedPref = context.getSharedPreferences(Constants.PreferencesFileName, Context.MODE_PRIVATE)
        val buttonsColor = sharedPref.getInt(Constants.ColorPreferenceID, Color.BLUE)
        val fontSize = sharedPref.getInt(Constants.FontSizePreferenceID, 14)

        holder.binding.deleteButton.setBackgroundColor(buttonsColor)
        holder.binding.amountTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize.toFloat())
        holder.binding.nameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize.toFloat())
        holder.binding.priceTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize.toFloat())
        holder.binding.amountConstantTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize.toFloat())
        holder.binding.priceConstantTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize.toFloat())
        holder.binding.isBoughtCheckBox.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize.toFloat())
    }
}
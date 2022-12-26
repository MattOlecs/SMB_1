package mateusz.oleksik.smb_projekt_1.adapters

import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.LocationServices
import mateusz.oleksik.smb_projekt_1.common.Constants
import mateusz.oleksik.smb_projekt_1.databinding.ShopLocalizationListElementBinding
import mateusz.oleksik.smb_projekt_1.models.ShopLocalization
import mateusz.oleksik.smb_projekt_1.viewModels.ShopLocalizationViewModel

class ShopLocalizationAdapter(val context: Context, val shopLocalizationViewModel: ShopLocalizationViewModel)
    : RecyclerView.Adapter<ShopLocalizationAdapter.ShopLocalizationViewHolder>() {

    private var shopsLocalizations = emptyList<ShopLocalization>()

    inner class ShopLocalizationViewHolder(val binding: ShopLocalizationListElementBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopLocalizationViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding = ShopLocalizationListElementBinding.inflate(inflater, parent, false)
        return ShopLocalizationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShopLocalizationViewHolder, position: Int) {
        val shopLocalization = shopsLocalizations[position]
        holder.binding.shopLocalization = shopLocalization
        loadSharedPreferencesLayoutOptions(holder)

        holder.binding.deleteButton.setOnClickListener {
            shopLocalizationViewModel.delete(shopLocalization)
            Toast.makeText(
                context,
                "Removed ${shopLocalization.name} from list",
                Toast.LENGTH_SHORT)
                .show()

            val geoClient = LocationServices.getGeofencingClient(context)
            geoClient.removeGeofences(listOf("Geo${shopLocalization.id}"))
        }
    }

    override fun getItemCount(): Int {
        return this.shopsLocalizations.size
    }

    internal fun setData(shopLocalizations: List<ShopLocalization>){
        this.shopsLocalizations = shopLocalizations
        notifyDataSetChanged()
    }

    private fun loadSharedPreferencesLayoutOptions(holder: ShopLocalizationViewHolder){
        val sharedPref = context.getSharedPreferences(Constants.PreferencesFileName, Context.MODE_PRIVATE)
        val buttonsColor = sharedPref.getInt(Constants.ColorPreferenceID, Color.BLUE)
        val fontSize = sharedPref.getInt(Constants.FontSizePreferenceID, 14)

        holder.binding.deleteButton.setBackgroundColor(buttonsColor)
        holder.binding.longitudeTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize.toFloat())
        holder.binding.nameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize.toFloat())
        holder.binding.latitudeTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize.toFloat())
        holder.binding.latitudeConstantTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize.toFloat())
        holder.binding.longitudeConstantTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize.toFloat())
    }
}
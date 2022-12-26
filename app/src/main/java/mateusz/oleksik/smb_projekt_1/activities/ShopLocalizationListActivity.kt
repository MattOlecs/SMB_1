package mateusz.oleksik.smb_projekt_1.activities

import android.Manifest
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import mateusz.oleksik.smb_projekt_1.adapters.ShopLocalizationAdapter
import mateusz.oleksik.smb_projekt_1.adapters.ShoppingItemAdapter
import mateusz.oleksik.smb_projekt_1.common.Constants
import mateusz.oleksik.smb_projekt_1.databinding.ActivityShopLocalizationListBinding
import mateusz.oleksik.smb_projekt_1.models.ShopLocalization
import mateusz.oleksik.smb_projekt_1.receivers.GeofenceReceiver
import mateusz.oleksik.smb_projekt_1.viewModels.ShopLocalizationViewModel
import mateusz.oleksik.smb_projekt_1.viewModels.ShoppingItemViewModel
import kotlin.random.Random

class ShopLocalizationListActivity : AppCompatActivity() {
    private lateinit var shopLocalizationViewModel: ShopLocalizationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityShopLocalizationListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.shopLocalizationRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.shopLocalizationRecyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL)
        )

        shopLocalizationViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(ShopLocalizationViewModel::class.java)
        binding.shopLocalizationRecyclerView.adapter = ShopLocalizationAdapter(this, shopLocalizationViewModel)
        shopLocalizationViewModel.shopsLocalizations.observe(this, Observer { items ->
            items?.let{
                (binding.shopLocalizationRecyclerView.adapter as ShopLocalizationAdapter).setData(it)
                it.forEach { x ->
                    addGeolocation(x)
                }
            }
        })

        binding.floatingActionButton.setOnClickListener {
            val intent = Intent(this, CreateShopLocalizationActivity::class.java)
            startActivity(intent)
        }
    }

    private fun addGeolocation(shop: ShopLocalization){
        val geoClient = LocationServices.getGeofencingClient(this)

        val latLng = LatLng(shop.latitude, shop.longitude)
        val radius = shop.radius.toFloat()
        val geo = Geofence.Builder().setRequestId("Geo${shop.id}")
            .setCircularRegion(
                latLng.latitude,
                latLng.longitude,
                radius
            )
            .setExpirationDuration(60*60*1000)
            .setTransitionTypes(
                Geofence.GEOFENCE_TRANSITION_ENTER
                    or Geofence.GEOFENCE_TRANSITION_EXIT)
            .build()
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }

        geoClient.addGeofences(getGeofencingRequest(geo),
            getGeofencePendingIntent(shop))
            .addOnSuccessListener {
                Toast.makeText(
                    this,
                    "Geofence dodany.", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(
                    this,
                    "Geofence nie został dodany. ${it.message}",
                    Toast.LENGTH_SHORT).show()
            }
    }

    private fun getGeofencingRequest(geofence: Geofence): GeofencingRequest {
        return GeofencingRequest.Builder()
            .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            .addGeofence(geofence)
            .build()
    }
    private fun getGeofencePendingIntent(shop: ShopLocalization): PendingIntent {
        val receiverIntent = Intent(this, GeofenceReceiver::class.java)

        var promotions = arrayOf("Chleb 2.99 PLN", "Masło 4.99 PLN", "Ser gouda 12.49/kg PLN")

        receiverIntent.putExtra(Constants.ShopNameExtraID, shop.name)
        receiverIntent.putExtra(Constants.ShopPromotionExtraID, "Today's special offer: ${promotions[Random.nextInt(0, promotions.size)]}")
        return PendingIntent.getBroadcast(
            this, shop.id,
            receiverIntent,
            PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
    }
}
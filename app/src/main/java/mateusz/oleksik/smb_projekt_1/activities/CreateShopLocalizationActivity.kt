package mateusz.oleksik.smb_projekt_1.activities

import android.Manifest
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationServices.getFusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import mateusz.oleksik.smb_projekt_1.common.Constants
import mateusz.oleksik.smb_projekt_1.database.ShoppingListDatabase
import mateusz.oleksik.smb_projekt_1.database.repositories.ShopLocalizationRepository
import mateusz.oleksik.smb_projekt_1.databinding.ActivityCreateShopLocalizationBinding
import mateusz.oleksik.smb_projekt_1.models.ShopLocalization
import mateusz.oleksik.smb_projekt_1.receivers.GeofenceReceiver
import kotlin.random.Random

class CreateShopLocalizationActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityCreateShopLocalizationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityCreateShopLocalizationBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        val perms = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION,
        )
        val requestCode = 1
        if (
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, perms, requestCode)
        }

        _binding.confirmAddItemButton.setOnClickListener {
            initializeShopCreation()
        }
        _binding.cancelAddItem.setOnClickListener {
            finish()
        }
    }

    private fun createShopLocalization(location: Location){
        val shopName = _binding.itemNameText.text.toString()
        val description = _binding.descriptionTextMultiLine.text.toString()
        val newShop = ShopLocalization(0, shopName, description, 100.00, location.latitude, location.longitude)

        val dao = ShoppingListDatabase.getDatabaseInstance(application).shopLocalizationDAO()
        val repository = ShopLocalizationRepository(dao)
        repository.insert(newShop)
        addGeolocation(newShop)

        finish()
        val shopListActivity = Intent(applicationContext, ShopLocalizationListActivity::class.java)
        startActivity(shopListActivity)
    }

    private fun initializeShopCreation(){

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(this, "Missing localization permissions", Toast.LENGTH_LONG).show()
        }

        getFusedLocationProviderClient(this).lastLocation
            .addOnSuccessListener {

                if (it == null){
                    Toast.makeText(this, "Could not acquire current location", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Shop localization - latitude: ${it.latitude} | longitude: ${it.longitude}", Toast.LENGTH_LONG).show()
                    createShopLocalization(it)
                }
            }
            .addOnFailureListener{
                Toast.makeText(this, "Could not acquire current location", Toast.LENGTH_LONG).show()

            }
    }

    private fun addGeolocation(shop: ShopLocalization){
        val geoClient = LocationServices.getGeofencingClient(this)

        val latLng = LatLng(shop.latitude, shop.longitude)
        val radius = 10000F
        val geo = Geofence.Builder().setRequestId("Geo${shop.id}")
            .setCircularRegion(
                latLng.latitude,
                latLng.longitude,
                radius
            )
            .setExpirationDuration(60*60*1000)
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER
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

    private fun getGeofencingRequest(geofence: Geofence): GeofencingRequest{
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
            this, 0,
            receiverIntent,
            PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)

    }
}
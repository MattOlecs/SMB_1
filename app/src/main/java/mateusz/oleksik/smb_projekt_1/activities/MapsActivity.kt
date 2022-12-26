package mateusz.oleksik.smb_projekt_1.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import mateusz.oleksik.smb_projekt_1.R
import mateusz.oleksik.smb_projekt_1.database.ShoppingListDatabase
import mateusz.oleksik.smb_projekt_1.database.repositories.ShopLocalizationRepository
import mateusz.oleksik.smb_projekt_1.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment

        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val dao = ShoppingListDatabase.getDatabaseInstance(application).shopLocalizationDAO()
        val repository = ShopLocalizationRepository(dao)

        repository.allShops.observe(this) { items ->
            items?.forEach {
                mMap.addMarker(
                    MarkerOptions().position(LatLng(it.latitude, it.longitude)).title(it.name).snippet(it.description)
                )
            }

            if (items.isNotEmpty()){
                val lastAddedShop = items.elementAt(items.lastIndex)
                mMap.moveCamera(CameraUpdateFactory.zoomBy(5f))
                val cameraPosition = CameraPosition.Builder()
                    .target(LatLng(lastAddedShop.latitude, lastAddedShop.longitude))
                    .zoom(17f).build()
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
            }
        }
    }
}
package mateusz.oleksik.smb_projekt_1

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import mateusz.oleksik.smb_projekt_1.activities.MapsActivity
import mateusz.oleksik.smb_projekt_1.activities.OptionsActivity
import mateusz.oleksik.smb_projekt_1.activities.ShopLocalizationListActivity
import mateusz.oleksik.smb_projekt_1.activities.ShoppingListActivity
import mateusz.oleksik.smb_projekt_1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    // Precise location access granted.
                }
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    // Only approximate location access granted.
                } else -> {
                // No location access granted.
            }
            }
        }



        locationPermissionRequest.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION))

        val backgroundLocationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_BACKGROUND_LOCATION, false) -> {
                    // Precise location access granted.
                } else -> {
                // No location access granted.
            }
            }
        }

        backgroundLocationPermissionRequest.launch(arrayOf(
            Manifest.permission.ACCESS_BACKGROUND_LOCATION))


        binding.openShoppingListButton.setOnClickListener{
            val intent = Intent(this, ShoppingListActivity::class.java)
            startActivity(intent)
        }

        binding.openShopsListButton.setOnClickListener{
            val intent = Intent(this, ShopLocalizationListActivity::class.java)
            startActivity(intent)
        }

        binding.openShopsMapButton.setOnClickListener{
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }

        binding.openSettingsButton.setOnClickListener{
            val intent = Intent(this, OptionsActivity::class.java)
            startActivity(intent)
        }
    }
}
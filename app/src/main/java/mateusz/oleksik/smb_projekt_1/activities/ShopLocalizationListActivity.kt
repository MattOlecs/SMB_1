package mateusz.oleksik.smb_projekt_1.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import mateusz.oleksik.smb_projekt_1.adapters.ShopLocalizationAdapter
import mateusz.oleksik.smb_projekt_1.adapters.ShoppingItemAdapter
import mateusz.oleksik.smb_projekt_1.databinding.ActivityShopLocalizationListBinding
import mateusz.oleksik.smb_projekt_1.viewModels.ShopLocalizationViewModel
import mateusz.oleksik.smb_projekt_1.viewModels.ShoppingItemViewModel

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
            }
        })

        binding.floatingActionButton.setOnClickListener {
            val intent = Intent(this, CreateShopLocalizationActivity::class.java)
            startActivity(intent)
        }
    }
}
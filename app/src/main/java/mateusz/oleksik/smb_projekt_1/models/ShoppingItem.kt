package mateusz.oleksik.smb_projekt_1.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ShoppingItems")
data class ShoppingItem(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "Name") val name: String?,
    @ColumnInfo(name = "Price") val price: Double,
    @ColumnInfo(name = "Amount") val amount: Int,
    @ColumnInfo(name = "IsBought") val isBought: Boolean
)

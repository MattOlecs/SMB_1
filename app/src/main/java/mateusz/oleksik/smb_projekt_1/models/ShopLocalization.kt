package mateusz.oleksik.smb_projekt_1.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ShopLocalization")
data class ShopLocalization(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "Name") val name: String,
    @ColumnInfo(name = "Description") val description: String?,
    @ColumnInfo(name = "Radius") val radius: Double,
    @ColumnInfo(name = "Latitude") val latitude: Double,
    @ColumnInfo(name = "Longitude") val longitude: Double,
)

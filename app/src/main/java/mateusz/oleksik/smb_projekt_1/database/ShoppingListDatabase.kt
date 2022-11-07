package mateusz.oleksik.smb_projekt_1.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import mateusz.oleksik.smb_projekt_1.database.DAOs.ShoppingItemDAO
import mateusz.oleksik.smb_projekt_1.models.ShoppingItem

@Database(entities = [ShoppingItem::class], version = 1)
abstract class ShoppingListDatabase : RoomDatabase() {

    abstract fun shoppingItemDAO(): ShoppingItemDAO

    companion object{
        private var instance: ShoppingListDatabase? = null
        fun getDatabaseInstance(context: Context): ShoppingListDatabase{
            val tmpInst = instance
            if(tmpInst != null){
                return tmpInst
            }
            val inst = Room.databaseBuilder(
                context.applicationContext,
                ShoppingListDatabase::class.java,
                "ShoppingListDatabase"
            ).allowMainThreadQueries().build()
            instance = inst
            return inst
        }
    }
}
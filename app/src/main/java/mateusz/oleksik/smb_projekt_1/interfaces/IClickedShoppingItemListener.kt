package mateusz.oleksik.smb_projekt_1.interfaces

import mateusz.oleksik.smb_projekt_1.models.ShoppingItem

interface IClickedShoppingItemListener {
    fun onClickedShoppingItem(item: ShoppingItem)
}
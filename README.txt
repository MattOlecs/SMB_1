Mini-projekt 1 - Mateusz Oleksik (s29325)

Wykonano: 
=> Zapis wartości za pomocą SharedPreferences [3 pkt]
=> Zapis listy produktów za pomocą bazy SQLite [7 pkt]

-------------------------------------
Funkcjonalność zapisu ustawień:
=> OptionsActivity onCreate() -> binding.applyButton.setOnClickListener

Na ekranie głównym należy wybrać opcje "Settings" aby przejść do ustawień.
Możliwa jest zmiana koloru przycisków oraz rozmiaru czcionki (zapis po naciśnięciu "Apply"). 
Zmiany widoczne są na liście produktów.

-------------------------------------
Funkcjonalność zapisu listy produktów:
=> ShoppingListActivity -> onCreatedShoppingItem(item: ShoppingItem), onEditedShoppingItem(item: ShoppingItem)

Na ekranie głównym należy wybrać opcje "Shopping list" aby przejść do listy produktów.
Mozliwe jest dodawanie nowych produktów (floating button w prawym dolnym rogu), edycja (należy kliknąć na produkt na liście) oraz usuwanie (przycisk "Delete").
Przy edycji oraz tworzeniu otwierany jest nowy fragment z odpowiednimi kontrolkami. Aktywność listy produktów jest do nich wstrzykiwana jako listener aby reagować na utworzenie/edycje produktu.
Usuwanie produktu jest zaimplementowane w klasie ShoppingItemAdapter.
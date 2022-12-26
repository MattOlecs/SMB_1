Mini-projekt 4 - Mateusz Oleksik (s29325)
Wykonano: 
=> Mapa: z wyznaczonymi ulubionymi sklepami. Można wykorzystać Google Maps lub dowolną inną (np. Mapbox) [4 pkt]
=> Lista ze sklepami: składa się z listy, 3 TextView i EditText (nazwa, opis miejsca, promień jaki obejmuje) oraz przycisku do dodania sklepu, w którym się znajdujemy do listy ulubionych. [3 pkt]
=> Aplikacja powinna notyfikować o wkroczeniu do dowolnego z miejsc (Proximity Alert lub Geofence) [3 pkt]
=> Aplikacja dodatkowo notyfikuje o wyjściu z dowolnego miejsca. [1 pkt]

-------------------------------------
Mapa z wyznaczonymi ulubionymi sklepami:

=> Przycisk "SHOPS MAP" prowadzi do aktywności z Google Maps. Dla każdego dodanego sklepu wyświetlana jest na mapie
pinezka z nazwą sklepu wraz z opisem

-> MapsActivity.kt
-> onMapReady(googleMap: GoogleMap) - dodanie wszystkich pinezek z ulubionymi sklepami
-> jeżeli lista ulubionych sklepów nie jest pusta. Mapa automatycznie przybliża i skupia się na ostatnim dodanym sklepie
-------------------------------------
Lista ze sklepami:

=> Przycisk "FAVOURITE SHOPS" prowadzi do aktywności z recycler view z listą ulubionych zakupów. 
W prawym dolnym rogu znajduje się przycisk do dodania nowego sklepu. Sklep jest dodawany z ostatnią znaną 
lokalizacją urządzenia (lastKnownActivity)

-> ShopLocalizationListActivity.kt
-> addGeolocation(shop: ShopLocalization) - dodanie Geofence dla każdego sklepu.
!! Geofence dla sklepów dodawane są przy wejściu do listy sklepów 
(chciałem uniknąć ładowania z bazy danych w MainActivity) !!

-------------------------------------
Notyfikacje o wkroczeniu do sklepu:
Notyfikacje o wyjściu ze sklepu:

=> Przy wejściu do obszaru sklepu (radius określony przez uzytkownika) wysyłane jest powiadomienie.
Zawartość powiadomienia to losowo wybrana promocja

-> GeofenceReceiver.kt
-> NotificationsJobService.kt
-> fun onReceive(context: Context, intent: Intent) - ustawia zawartość powiadomienia w zależności
czy obszar jest opuszczany czy wchodzimy do niego.
-> fun scheduleNotificationJob(context: Context, bundle: PersistableBundle) - zleca wykonanie
pracy wysłania notyfikacji
-> fun sendNotification(bundle: PersistableBundle) - tworzy i wysyła powiadomienie. Wcześniej w tej samej
klasie tworzony jest kanał powiadomienia

!! Aby odświeżyć lokalizację (lastKnownLocalization) może być konieczne otwieranie google maps

Link do repozytorium (w razie problemów z zip): https://github.com/MattOlecs/SMB_1
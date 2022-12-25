package mateusz.oleksik.smb_projekt_1.receivers

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent
import mateusz.oleksik.smb_projekt_1.common.Constants
import mateusz.oleksik.smb_projekt_1.services.NotificationsJobService

class GeofenceReceiver : BroadcastReceiver() {
    private var _jobId = 0

    override fun onReceive(context: Context, intent: Intent) {
        val geoEvent = GeofencingEvent.fromIntent(intent) ?: return

        val triggering = geoEvent.triggeringGeofences ?: return
        for(geo in triggering){
            Log.i("geofence", "Geofence request: ${geo.requestId} is active.")
        }

        val shopName = intent.getStringExtra(Constants.ShopNameExtraID)
        val shopPromotion = intent.getStringExtra(Constants.ShopPromotionExtraID)

        val bundle = PersistableBundle()
        if (geoEvent.geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER){
            bundle.putString(Constants.ShopNotificationTitle, "You've entered ${shopName} shop.")
            bundle.putString(Constants.ShopNotificationMessage, "Promotion of the day ${shopPromotion}.")
        }else if (geoEvent.geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT){
            bundle.putString(Constants.ShopNotificationTitle, "You left ${shopName} shop")
            bundle.putString(Constants.ShopNotificationMessage, "Bye! Hoping to see you soon!")
        }else{
            Log.e("geofences", "Geofence error")
        }

        scheduleNotificationJob(context, bundle)
    }

    private fun scheduleNotificationJob(context: Context, bundle: PersistableBundle){
        val cn = ComponentName(context, NotificationsJobService::class.java)
        val info = JobInfo.Builder(_jobId++, cn)
            .setRequiresBatteryNotLow(true)
            .setPersisted(true)
            .setPeriodic(15*60*1000)
            .setExtras(bundle)
            .build()

        val scheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        if(scheduler.schedule(info) == JobScheduler.RESULT_SUCCESS){
            Toast.makeText(context, "Job scheduled successfully!", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context, "Job scheduling failed.", Toast.LENGTH_SHORT).show()
        }
    }
}
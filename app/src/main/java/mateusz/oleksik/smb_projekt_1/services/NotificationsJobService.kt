package mateusz.oleksik.smb_projekt_1.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import mateusz.oleksik.smb_projekt_1.R
import mateusz.oleksik.smb_projekt_1.common.Constants
import kotlin.concurrent.thread
import kotlin.random.Random

class NotificationsJobService : JobService() {

    private var cancel = false

    override fun onStartJob(parameters: JobParameters?): Boolean {
        var extras = parameters!!.extras
        doWork(extras)
        return true
    }

    override fun onStopJob(parameters: JobParameters?): Boolean {
        cancel = true
        return true
    }

    private fun doWork(bundle: PersistableBundle){
        thread(start = true) {
            createNotificationChannel()
            sendNotification(bundle)
        }
    }

    private fun sendNotification(bundle: PersistableBundle){
        val title = bundle.getString(Constants.ShopNotificationTitle)
        val message = bundle.getString(Constants.ShopNotificationMessage)

        val notification = NotificationCompat.Builder(applicationContext, Constants.NotificationChannelID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title!!)
            .setContentText(message!!)
            .setAutoCancel(true)
            .build()
        val notificationManager = NotificationManagerCompat.from(applicationContext)
        notificationManager.notify(Random.nextInt(), notification)
    }

    private fun createNotificationChannel(){
        val notificationChannel = NotificationChannel(
            Constants.NotificationChannelID,
            Constants.NotificationChannelName,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationChannel.description = Constants.NotificationChannelDescription
        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.createNotificationChannel(notificationChannel)
    }
}
package com.diksed.kriptak.utils

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.diksed.kriptak.R
import com.diksed.kriptak.data.model.PriceAlert

/**
 * Shared notification-building logic. Uses the same channel as
 * PushNotificationService so alerts land alongside the app's other
 * notifications instead of creating a second channel.
 */
object NotificationHelper {

    fun showPriceAlert(context: Context, alert: PriceAlert, formattedPrice: String) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        createNotificationChannel(context)

        val titleRes = if (alert.isAbove) {
            R.string.priceAlertNotificationTitleAbove
        } else {
            R.string.priceAlertNotificationTitleBelow
        }

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(context.getString(titleRes, alert.coinName))
            .setContentText(context.getString(R.string.priceAlertNotificationBody, alert.coinName, formattedPrice))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            notify(alert.coinId, builder.build())
        }
    }

    private fun createNotificationChannel(context: Context) {
        val name = context.getString(R.string.channelName)
        val descriptionText = context.getString(R.string.channelDescription)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private const val CHANNEL_ID = "KripTak_Notifications_Channel"
}

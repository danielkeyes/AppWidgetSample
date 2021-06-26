package dev.danielkeyes.appwidgetsample

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.widget.RemoteViews
import java.text.DateFormat
import java.util.Date

// This files if the widget provider, defines the behavior for your widget.
// key task is to handle widget update intents

private const val mSharedPrefFile = "dev.danielkeyes.appwidgetsample"
private const val COUNT_KEY = "count"

/**
 * Implementation of App Widget functionality.
 */
class NewAppWidget : AppWidgetProvider() { // AppWidgetProvider extends BroadcastReceiver
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // Doesn't modify in place, have to redraw each time
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
        // Example is an instance of a database created specifically for the widget
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
        // Cleans up resources we created in the onEnabled
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    val prefs: SharedPreferences = context.getSharedPreferences(mSharedPrefFile, MODE_PRIVATE)

    var count = prefs.getInt(COUNT_KEY + appWidgetId, 0)
    count++

    val dateString = DateFormat.getTimeInstance(DateFormat.SHORT).format(Date())

    val views = RemoteViews(context.packageName, R.layout.new_app_widget)
    views.setTextViewText(R.id.app_widget_id, appWidgetId.toString())
    views.setTextViewText(R.id.appwidget_update, "$count $dateString")

    prefs.edit().putInt(COUNT_KEY + appWidgetId, count).apply()
//    prefs.edit().apply()

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}
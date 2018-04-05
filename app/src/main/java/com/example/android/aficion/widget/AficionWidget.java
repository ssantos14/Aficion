package com.example.android.aficion.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.example.android.aficion.FeedActivity;
import com.example.android.aficion.R;

/**
 * Implementation of App Widget functionality.
 */
public class AficionWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

//        CharSequence widgetText = context.getString(R.string.appwidget_text);
//        // Construct the RemoteViews object
//        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.aficion_widget);
//        views.setTextViewText(R.id.appwidget_text, widgetText);
//
//        // Instruct the widget manager to update the widget
//        appWidgetManager.updateAppWidget(appWidgetId, views);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.aficion_widget);

        // set intent for widget service that will create the views
        Intent serviceIntent = new Intent(context, StackWidgetService.class);
        serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME))); // embed extras so they don't get ignored
        remoteViews.setRemoteAdapter(appWidgetId, R.id.stackWidgetView, serviceIntent);
        remoteViews.setEmptyView(R.id.stackWidgetView, R.id.stackWidgetEmptyView);

        // set intent for item click (opens main activity)
        Intent viewIntent = new Intent(context, FeedActivity.class);
        PendingIntent viewPendingIntent = PendingIntent.getActivity(context, 0, viewIntent, 0);
        remoteViews.setPendingIntentTemplate(R.id.stackWidgetView, viewPendingIntent);

        // update widget
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }
}


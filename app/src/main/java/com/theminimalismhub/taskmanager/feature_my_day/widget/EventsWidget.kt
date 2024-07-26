package com.theminimalismhub.taskmanager.feature_my_day.widget

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.text.Text

class EventsWidget : GlanceAppWidget() {
    
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        
        provideContent { 
            Text(text = "Ayooo Boiii")
        }
    }

}
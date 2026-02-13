package com.example.memoreels.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.action.clickable
import androidx.glance.action.actionStartActivity
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.width
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.example.memoreels.ui.MainActivity

// Pre-define color providers to avoid overload resolution issues
private val BgColor = ColorProvider(androidx.compose.ui.graphics.Color(0xFF880E4F))
private val WhiteText = ColorProvider(androidx.compose.ui.graphics.Color.White)
private val SubtleText = ColorProvider(androidx.compose.ui.graphics.Color(0xFFFFCDD2))

class DailyMemoryWidget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val prefs = context.getSharedPreferences(
            DailyMemoryWorker.PREFS_NAME, Context.MODE_PRIVATE
        )
        val uri = prefs.getString(DailyMemoryWorker.KEY_URI, null)
        val isVideo = prefs.getBoolean(DailyMemoryWorker.KEY_IS_VIDEO, false)
        val tag = prefs.getString(DailyMemoryWorker.KEY_TAG, "") ?: ""
        val date = prefs.getString(DailyMemoryWorker.KEY_DATE, "Today") ?: "Today"

        provideContent {
            GlanceTheme {
                WidgetContent(
                    hasMemory = uri != null,
                    tag = tag,
                    date = date,
                    isVideo = isVideo
                )
            }
        }
    }

    @Composable
    private fun WidgetContent(
        hasMemory: Boolean,
        tag: String,
        date: String,
        isVideo: Boolean
    ) {
        Box(
            modifier = GlanceModifier
                .fillMaxSize()
                .cornerRadius(16.dp)
                .background(BgColor)
                .clickable(actionStartActivity<MainActivity>())
                .padding(14.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            if (hasMemory) {
                Column(modifier = GlanceModifier.fillMaxWidth()) {
                    Text(
                        text = "Daily Memory",
                        style = TextStyle(
                            color = WhiteText,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal
                        )
                    )
                    Spacer(modifier = GlanceModifier.height(4.dp))
                    Text(
                        text = tag.ifEmpty { "A moment to remember" },
                        style = TextStyle(
                            color = WhiteText,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        maxLines = 2
                    )
                    Spacer(modifier = GlanceModifier.height(6.dp))
                    Row {
                        Text(
                            text = date,
                            style = TextStyle(
                                color = SubtleText,
                                fontSize = 11.sp
                            )
                        )
                        Spacer(modifier = GlanceModifier.width(8.dp))
                        Text(
                            text = if (isVideo) "Video" else "Photo",
                            style = TextStyle(
                                color = SubtleText,
                                fontSize = 11.sp
                            )
                        )
                    }
                }
            } else {
                Text(
                    text = "Tap to discover a memory",
                    style = TextStyle(
                        color = WhiteText,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
        }
    }
}

/** Receiver that Android uses to bind the widget. */
class DailyMemoryWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = DailyMemoryWidget()
}

package org.edx.mobile.segment

import android.content.Context
import android.util.Log
import com.segment.analytics.kotlin.destinations.braze.BrazeDestination
import com.segment.analytics.kotlin.destinations.firebase.FirebaseDestination
import org.openedx.foundation.interfaces.Analytics
import com.segment.analytics.kotlin.android.Analytics as SegmentAnalyticsBuilder
import com.segment.analytics.kotlin.core.Analytics as SegmentTracker

class SegmentAnalytics(
    context: Context,
    segmentWriteKey: String,
    logsEnabled: Boolean = false,
    firebaseDestinationEnabled: Boolean = false,
    brazeDestinationEnabled: Boolean = false
) : Analytics {

    // Create an analytics client with the given application context and Segment write key.
    private var tracker: SegmentTracker = SegmentAnalyticsBuilder(segmentWriteKey, context) {
        // Automatically track Lifecycle events
        trackApplicationLifecycleEvents = true
        flushAt = 20
        flushInterval = 30
    }

    init {
        if (firebaseDestinationEnabled) {
            tracker.add(plugin = FirebaseDestination(context = context))
        }

        if (firebaseDestinationEnabled && brazeDestinationEnabled) {
            tracker.add(plugin = BrazeDestination(context))
        }
        SegmentTracker.debugLogsEnabled = logsEnabled
        Log.d(TAG, "Segment Analytics Builder Initialised")
    }

    override fun logScreenEvent(screenName: String, params: Map<String, Any?>) {
        Log.d(TAG, "Segment Analytics log Screen Event: $screenName + $params")
        tracker.screen(screenName, params)
    }

    override fun logEvent(eventName: String, params: Map<String, Any?>) {
        Log.d(TAG, "Segment Analytics log Event $eventName: $params")
        tracker.track(eventName, params)
    }

    override fun logUserId(userId: Long) {
        Log.d(TAG, "Segment Analytics User Id log Event: $userId")
        tracker.identify(userId.toString())
    }

    private companion object {
        const val TAG = "SegmentAnalytics"
    }
}

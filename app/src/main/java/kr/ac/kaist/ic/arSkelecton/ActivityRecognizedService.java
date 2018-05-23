package kr.ac.kaist.ic.arSkelecton;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

import java.util.List;

public class ActivityRecognizedService extends IntentService {

    public static final String
            ACTION_ACTIVITY_BROADCAST = ActivityRecognizedService.class.getName(),
            CURRENT_ACTIVITY = "";

    public ActivityRecognizedService() {
        super("ActivityRecognizedService");
    }

    public ActivityRecognizedService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if(ActivityRecognitionResult.hasResult(intent)) {
            ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
            handleDetectedActivities( result.getProbableActivities() );
        }
    }

    private void handleDetectedActivities(List<DetectedActivity> probableActivities) {
        for( DetectedActivity activity : probableActivities ) {
            if( activity.getConfidence() >= 75 ) {
                sendBroadcastMessage(activity.getType());
            }
        }
    }

    private void sendBroadcastMessage(int activityType) {
        String currentActivity = "";
        switch( activityType ) {
            case DetectedActivity.IN_VEHICLE: {
                currentActivity = "In vehicle";
                break;
            }
            case DetectedActivity.ON_BICYCLE: {
                currentActivity = "On bicycle";
                break;
            }
            case DetectedActivity.ON_FOOT: {
                currentActivity = "On foot";
                break;
            }
            case DetectedActivity.RUNNING: {
                currentActivity = "Running";
                break;
            }
            case DetectedActivity.STILL: {
                currentActivity = "Still";
                break;
            }
            case DetectedActivity.TILTING: {
                currentActivity = "Tilting";
                break;
            }
            case DetectedActivity.WALKING: {
                currentActivity = "Walking";
                break;
            }
            case DetectedActivity.UNKNOWN: {
                currentActivity = "Unknown";
                break;
            }
        }
        Intent intent = new Intent(ACTION_ACTIVITY_BROADCAST);
        intent.putExtra(CURRENT_ACTIVITY, currentActivity);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

}

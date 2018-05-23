package kr.ac.kaist.ic.arSkelecton;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.ActivityRecognition;

import kr.ac.kaist.ic.activityRecognitionSkelecton.R ;

public class BonusActivity extends Activity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private Button btnStartAAR, btnFinishAAR;
    private Button btnStartFFTTestingModel, btnFinishFFTTestingModel;
    TextView tvAARResult;

    public GoogleApiClient mApiClient;

    private Boolean connected = false;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bonus);

        context = this;

        btnStartAAR = (Button) findViewById(R.id.btnStartAAR);
        btnFinishAAR = (Button) findViewById(R.id.btnFinishAAR);
        btnStartFFTTestingModel = (Button) findViewById(R.id.btnStartFFTTestingModel);
        btnFinishFFTTestingModel = (Button) findViewById(R.id.btnFinishFFTTestingModel);

        tvAARResult = (TextView) findViewById(R.id.tvAARResult);

        btnStartAAR.setOnClickListener(btnStartAAROnClick);
        btnFinishAAR.setOnClickListener(btnFinishAAROnClick);
        btnStartFFTTestingModel.setOnClickListener(btnStartFFTTestingModelOnClick);
        btnFinishFFTTestingModel.setOnClickListener(btnFinishFFTTestingModelOnClick);

        mApiClient = new GoogleApiClient.Builder(this)
                .addApi(ActivityRecognition.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        mApiClient.connect();

        LocalBroadcastManager.getInstance(this).registerReceiver(
                new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        String activity = intent.getStringExtra(ActivityRecognizedService.CURRENT_ACTIVITY);
                        Log.d("Received activity", activity);
                        tvAARResult.setText(activity);
                    }
                }, new IntentFilter(ActivityRecognizedService.ACTION_ACTIVITY_BROADCAST)
        );
    }

    private View.OnClickListener btnStartAAROnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (connected) {
                Intent intent = new Intent( context, ActivityRecognizedService.class );
                PendingIntent pendingIntent = PendingIntent.getService( context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT );
                ActivityRecognition.ActivityRecognitionApi.requestActivityUpdates( mApiClient, 3000, pendingIntent );
            }
        }
    };

    private View.OnClickListener btnFinishAAROnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    private View.OnClickListener btnStartFFTTestingModelOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    private View.OnClickListener btnFinishFFTTestingModelOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        connected = true;

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}

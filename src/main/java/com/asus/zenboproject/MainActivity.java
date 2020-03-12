package com.asus.zenboproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.asus.robotframework.API.RobotCallback;
import com.asus.robotframework.API.RobotCmdState;
import com.asus.robotframework.API.RobotErrorCode;
import com.asus.robotframework.API.SpeakConfig;
import com.robot.asus.robotactivity.RobotActivity;

import org.json.JSONObject;

public class MainActivity extends RobotActivity {

    final static String TAG = "zenbooffice";
    final static String DOMAIN = "968FA483819A4BAEB76941D8CEF1A2BB";

    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 101;
    public static RobotCallback robotCallback = new RobotCallback() {
        @Override
        public void onResult(int cmd, int serial, RobotErrorCode err_code, Bundle result) {
            super.onResult(cmd, serial, err_code, result);
        }

        @Override
        public void onStateChange(int cmd, int serial, RobotErrorCode err_code, RobotCmdState state) {
            super.onStateChange(cmd, serial, err_code, state);
        }

        @Override
        public void initComplete() {
            super.initComplete();

        }
    };

    public static RobotCallback.Listen robotListenCallback = new RobotCallback.Listen() {
        @Override
        public void onFinishRegister() {

        }

        @Override
        public void onVoiceDetect(JSONObject jsonObject) {

        }

        @Override
        public void onSpeakComplete(String s, String s1) {

        }

        @Override
        public void onEventUserUtterance(JSONObject jsonObject) {

        }

        @Override
        public void onResult(JSONObject jsonObject) {

        }

        @Override
        public void onRetry(JSONObject jsonObject) {

        }
    };

    private void requestPermission() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                this.checkSelfPermission(Manifest.permission.READ_CONTACTS) ==
                        PackageManager.PERMISSION_GRANTED) {
            // Android version is lesser than 6.0 or the permission is already granted.
            return;
        }

        if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},
                    PERMISSIONS_REQUEST_READ_CONTACTS);
        } else {
            //showMessageOKCancel("You need to allow access to Contacts",
            //        new DialogInterface.OnClickListener() {
            //            @Override
            //            public void onClick(DialogInterface dialog, int which) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},
                    PERMISSIONS_REQUEST_READ_CONTACTS);
            //            }
            //        });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        setMainMenuLayout();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public  MainActivity() {super(robotCallback, robotListenCallback);}

    private static ConstraintLayout mMainMenuLayout, mInfoLayout, mTourLayout;
    private static Button mTourButton, mInfoButton, mMeetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainMenuLayout = findViewById(R.id.lyt_main);

        mTourLayout = findViewById(R.id.lyt_tour);
        mTourButton = findViewById(R.id.btn_tour);
        mTourButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTourLayout();
            }
        });


    }

    public static void setMainMenuLayout(){

        mMainMenuLayout.setVisibility(View.VISIBLE);
        /**
        mMeetDataLayout.setVisibility(View.INVISIBLE);
        mTourLayout.setVisibility(View.INVISIBLE);
        mInfoLayout.setVisibility(View.INVISIBLE);
        mMeetPositionLayout.setVisibility(View.INVISIBLE);
        mQRLayout.setVisibility(View.INVISIBLE);
        mMeetShowDataLayout.setVisibility(View.INVISIBLE);
        mOfficeLayout.setVisibility(View.INVISIBLE);
        mLecturerLayout.setVisibility(View.INVISIBLE);
        mBachelorLayout.setVisibility(View.INVISIBLE);
        mMasterLayout.setVisibility(View.INVISIBLE);
        mDoctorLayout.setVisibility(View.INVISIBLE);
**/
        robotAPI.robot.speakAndListen("How can I service you today ??", new SpeakConfig().timeout(5));
    }

    public static void setTourLayout() {

        mMainMenuLayout.setVisibility(View.INVISIBLE);

        //Tour Layout
        mTourLayout.setVisibility(View.VISIBLE);

        //mMeetPositionLayout.setVisibility(View.INVISIBLE);
       // mMeetDataLayout.setVisibility(View.INVISIBLE);
       // mMeetShowDataLayout.setVisibility(View.INVISIBLE);

        mInfoLayout.setVisibility(View.INVISIBLE);
       // mQRLayout.setVisibility(View.INVISIBLE);
       // mOfficeLayout.setVisibility(View.INVISIBLE);
       // mLecturerLayout.setVisibility(View.INVISIBLE);
       // mBachelorLayout.setVisibility(View.INVISIBLE);
       // mMasterLayout.setVisibility(View.INVISIBLE);
       // mDoctorLayout.setVisibility(View.INVISIBLE);

        robotAPI.robot.speakAndListen("Would you want to take a tour in dean office?", new SpeakConfig().timeout(1));
    }

}

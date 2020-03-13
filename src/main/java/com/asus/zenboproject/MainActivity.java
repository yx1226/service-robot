package com.asus.zenboproject;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.asus.robotframework.API.RobotCallback;
import com.asus.robotframework.API.RobotCmdState;
import com.asus.robotframework.API.RobotErrorCode;
import com.asus.robotframework.API.RobotFace;
import com.asus.robotframework.API.SpeakConfig;
import com.asus.robotframework.API.VisionConfig;
import com.asus.robotframework.API.results.DetectFaceResult;
import com.robot.asus.robotactivity.RobotActivity;

import android.os.Bundle;
import android.widget.VideoView;
import android.widget.ViewSwitcher;

import org.json.JSONObject;

import static android.view.ViewGroup.LayoutParams.*;

public class MainActivity extends RobotActivity {




    private static ConstraintLayout mMainMenuLayout,
            mTourLayout,
            mMeetDataLayout, mMeetShowDataLayout, mMeetPositionLayout,
            mInfoLayout, mQRLayout, mBachelorLayout, mMasterLayout, mDoctorLayout, mLecturerLayout, mAdmissionLayout, mOfficeLayout;

    private static Button mHomeButton, mHome2Button, mHome3Button, mHome4Button, mHome5Button,
            mTourButton, mMeetButton, mSubmitButton,
            mInfoButton, mBachelorButton, mMasterButton, mDoctorButton,
            mQRButton, mAdmissionButton, mOfficeButton, mLecturerButton,
            mBackInfoButton, mBackInfo1Button, prev, next;

    private static RadioGroup radioGroupAdmission, radioGroupOffice;
    private static RadioButton rAdmission, rAdmissionNonCitizen, rDean, rHepa;

    private static ImageView iCitizen, iNonCitizen, iDean, iHepa;

    private static TextView p1, txt_close, title, title1;

    private static ImageSwitcher imageSwitcher;
    private static int i = 0;
    private static Integer[] images = {R.drawable.cs, R.drawable.it, R.drawable.is, R.drawable.mm};

    static VideoView mVideo;

    private static final String VIDEO_SAMPLE = "videoViewer";
    static SpeakConfig speakConfig = new SpeakConfig();
    static ScrollingMovementMethod scrollingMovementMethod = new ScrollingMovementMethod();

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

    /**
     * private Uri getMedia(String mediaName) {
     * return Uri.parse("android.resource://" + getPackageName() +
     * "/raw/" + mediaName);
     * }
     *
     * private void initializePlayer() {
     *
     * Uri videoUri = getMedia(VIDEO_SAMPLE);
     * mVideo.setVideoURI(videoUri);
     * }
     *
     * private void releasePlayer() {
     * mVideo.stopPlayback();
     * }
     *
     * @Override public void onBackPressed() {
     * // super.onBackPressed();
     * android.os.Process.killProcess(android.os.Process.myPid());
     * // This above line close correctly
     * }
     **/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //MAIN MENU
        mMainMenuLayout = findViewById(R.id.lyt_main);

        //TOUR LAYOUT
        mTourLayout = findViewById(R.id.lyt_tour);
        mTourButton = findViewById(R.id.btn_tour);
        mTourButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTourLayout();
            }
        });

        mHomeButton = findViewById(R.id.btn_endtour);
        mHomeButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMainMenuLayout();
            }
        });

        //checkpoint


        /**MEET LAYOUT
         * 1. Position list showed
         * 2.
         * 3.
         */
        mMeetPositionLayout = findViewById(R.id.lyt_positionlist);
        mMeetDataLayout = findViewById(R.id.lyt_meet);
        mMeetShowDataLayout = findViewById(R.id.lyt_datameet);

        title = findViewById(R.id.textView_titleMeet);
        title1 = findViewById(R.id.textView_titlemeet);

        mMeetButton = findViewById(R.id.btn_meet);
        mSubmitButton = findViewById(R.id.btn_submit);

        mMeetButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                setMeetPositionLayout();
            }
        });

        mSubmitButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                setShowMeetDataLayout();
            }
        });

        /**INFO LAYOUT
         * 1. Qr code can be scanned /
         * 2. Introduction video can be watched? /
         *
         * 3. Lecturer list showed:
         *  When user click on the name of lecturer:
         *    - lecturer's professional will be shown
         *    - perhaps can add on the profile picture of each lecturer
         *
         * 4. Programme
         *  i. Bachelor list /
         *  ii. Master list
         *  iii. Phd /
         *
         * 5.Admission
         *  i. International student /
         *  ii. diploma/ matriculation/ stpm /
         */
        mInfoLayout = findViewById(R.id.lyt_info);
        mBachelorLayout = findViewById(R.id.lyt_bachelor);
        //mMasterLayout = findViewById(R.id.lyt_master);
        mDoctorLayout = findViewById(R.id.lyt_doctor);
        mQRLayout = findViewById(R.id.lyt_qr);
        mAdmissionLayout = findViewById(R.id.lyt_admission);
        mOfficeLayout = findViewById(R.id.lyt_office);
        mLecturerLayout = findViewById(R.id.lyt_lecturer);

        txt_close = findViewById(R.id.txt_close);

        mInfoButton = findViewById(R.id.btn_info);
        mBachelorButton = findViewById(R.id.btn_bachelor);
        mMasterButton = findViewById(R.id.btn_master);
        mDoctorButton = findViewById(R.id.btn_doctor);
        mQRButton = findViewById(R.id.btn_qrcode);
        mHomeButton = findViewById(R.id.btn_home);
        mAdmissionButton = findViewById(R.id.btn_admission);
        mOfficeButton = findViewById(R.id.btn_office);
        mLecturerButton = findViewById(R.id.btn_lecturer);

        radioGroupAdmission = findViewById(R.id.radioGroup);
        rAdmission = findViewById(R.id.malaysian);
        rAdmissionNonCitizen = findViewById(R.id.international);
        iNonCitizen = findViewById(R.id.img_noncitizen);
        iCitizen = findViewById(R.id.img_citizen);

        //radioGroupOffice;
        radioGroupOffice = findViewById(R.id.radioGroupOffice);
        rDean = findViewById(R.id.dean);
        rHepa = findViewById(R.id.hepa);
        iDean = findViewById(R.id.img_deanoffice);
        iHepa = findViewById(R.id.img_hepaoffice);

        //Bachelor button
        prev = findViewById(R.id.btn_prev);
        next = findViewById(R.id.btn_next);

        imageSwitcher = findViewById(R.id.img_switch);

        mInfoButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInfoLayout();
            }
        });

        mQRButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                setQRLayout();
            }
        });

        txt_close.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInfoLayout();
            }
        });

        mOfficeButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOfficeLayout();
            }
        });

        mLecturerButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLecturerLayout();
            }
        });

        mAdmissionButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAdmissionLayout();
            }
        });


        mBachelorButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                setBachelorLayout();

            }
        });

        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(getApplicationContext());
                imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                imageView.setLayoutParams(new ImageSwitcher.LayoutParams(MATCH_PARENT, MATCH_PARENT));
                return imageView;
            }
        });

        final Animation in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.in);
        final Animation out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.out);

        final Animation in1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.in1);
        final Animation out1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.out1);


        prev.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageSwitcher.setInAnimation(in1);
                imageSwitcher.setOutAnimation(out1);
                if (i > 0) {
                    i--;
                    imageSwitcher.setImageResource(images[i]);
                }
            }
        });

        next.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageSwitcher.setInAnimation(in);
                imageSwitcher.setOutAnimation(out);
                if (i < images.length) {
                    i++;
                    imageSwitcher.setImageResource(images[i]);
                }
            }
        });

        mBackInfoButton = findViewById(R.id.btn_backinfo);
        mBackInfoButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMainMenuLayout();
            }
        });

        mBackInfo1Button = findViewById(R.id.btn_backinfo1);
        mBackInfo1Button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMainMenuLayout();
            }
        });

        mMasterButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                // setMasterLayout();

            }
        });


        mDoctorButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDoctorLayout();
            }
        });

        mHomeButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMainMenuLayout();
            }
        });

    }

    public MainActivity() {
        super(robotCallback, robotListenCallback);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // close faical
        robotAPI.robot.setExpression(RobotFace.HIDEFACE);

        robotAPI.robot.setVoiceTrigger(false);

        setMainMenuLayout();

        robotAPI.robot.speakAndListen("Welcome to FTSM, Im Zenbo. Im the robot receptionist for this office.\\n\" +\n" +
                "         \"        Which is in dean office Faculty of Technology and Science Information.\\n\" +\n" +
                "         \"        Any help can I serve you? \" +\n" +
                "         \"        You may also touch the screen to proceed the service.", new SpeakConfig().timeout(10));
    }

    public static void setMainMenuLayout() {

        mMainMenuLayout.setVisibility(View.VISIBLE);

        mTourLayout.setVisibility(View.INVISIBLE);

        mMeetDataLayout.setVisibility(View.INVISIBLE);
        mMeetPositionLayout.setVisibility(View.INVISIBLE);
        mMeetShowDataLayout.setVisibility(View.INVISIBLE);

        mInfoLayout.setVisibility(View.INVISIBLE);
        mQRLayout.setVisibility(View.INVISIBLE);
        mOfficeLayout.setVisibility(View.INVISIBLE);
        mLecturerLayout.setVisibility(View.INVISIBLE);
        mBachelorLayout.setVisibility(View.INVISIBLE);
//        mMasterLayout.setVisibility(View.INVISIBLE);
        mDoctorLayout.setVisibility(View.INVISIBLE);
        mAdmissionLayout.setVisibility(View.INVISIBLE);
    }

    public static void setTourLayout() {

        mMainMenuLayout.setVisibility(View.INVISIBLE);

        //Tour Layout
        mTourLayout.setVisibility(View.VISIBLE);

        mMeetPositionLayout.setVisibility(View.INVISIBLE);
        mMeetDataLayout.setVisibility(View.INVISIBLE);
        mMeetShowDataLayout.setVisibility(View.INVISIBLE);

        mInfoLayout.setVisibility(View.INVISIBLE);
        mQRLayout.setVisibility(View.INVISIBLE);
        mOfficeLayout.setVisibility(View.INVISIBLE);
        mLecturerLayout.setVisibility(View.INVISIBLE);
        mBachelorLayout.setVisibility(View.INVISIBLE);
        // mMasterLayout.setVisibility(View.INVISIBLE);
        mDoctorLayout.setVisibility(View.INVISIBLE);
        mAdmissionLayout.setVisibility(View.INVISIBLE);

        robotAPI.robot.speakAndListen("Would you want to take a tour in dean office?", new SpeakConfig().timeout(1));
    }

    public static void setMeetPositionLayout() {

        mMainMenuLayout.setVisibility(View.INVISIBLE);
        mMeetDataLayout.setVisibility(View.INVISIBLE);
        mMeetShowDataLayout.setVisibility(View.INVISIBLE);
        mTourLayout.setVisibility(View.INVISIBLE);
        mInfoLayout.setVisibility(View.INVISIBLE);
        mMeetPositionLayout.setVisibility(View.VISIBLE);
        mQRLayout.setVisibility(View.INVISIBLE);
        mOfficeLayout.setVisibility(View.INVISIBLE);
        mLecturerLayout.setVisibility(View.INVISIBLE);
        mBachelorLayout.setVisibility(View.INVISIBLE);
        //mMasterLayout.setVisibility(View.INVISIBLE);
        mDoctorLayout.setVisibility(View.INVISIBLE);
        mAdmissionLayout.setVisibility(View.INVISIBLE);

        robotAPI.robot.speakAndListen("There are executive list, you may scroll and click to select the executive you want to meet", new SpeakConfig().timeout(5));
    }

    public static void setMeetFillUserDataLayout() {

        mMainMenuLayout.setVisibility(View.INVISIBLE);

        mTourLayout.setVisibility(View.INVISIBLE);

        mMeetDataLayout.setVisibility(View.VISIBLE);
        mMeetPositionLayout.setVisibility(View.INVISIBLE);
        mMeetShowDataLayout.setVisibility(View.INVISIBLE);

        mInfoLayout.setVisibility(View.INVISIBLE);
        mQRLayout.setVisibility(View.INVISIBLE);
        mOfficeLayout.setVisibility(View.INVISIBLE);
        mLecturerLayout.setVisibility(View.INVISIBLE);
        mBachelorLayout.setVisibility(View.INVISIBLE);
        mMasterLayout.setVisibility(View.INVISIBLE);
        mDoctorLayout.setVisibility(View.INVISIBLE);

        robotAPI.robot.speakAndListen("Hi there, please fill in your name and phone, for the purpose by showing your photo and data to executive", new SpeakConfig().timeout(5));

    }

    public static void setShowMeetDataLayout() {

        mMainMenuLayout.setVisibility(View.INVISIBLE);
        mTourLayout.setVisibility(View.INVISIBLE);

        //Meet Data
        mMeetDataLayout.setVisibility(View.VISIBLE);
        mMeetPositionLayout.setVisibility(View.INVISIBLE);
        mMeetShowDataLayout.setVisibility(View.INVISIBLE);

        mInfoLayout.setVisibility(View.INVISIBLE);
        mQRLayout.setVisibility(View.INVISIBLE);
        mOfficeLayout.setVisibility(View.INVISIBLE);
        mLecturerLayout.setVisibility(View.INVISIBLE);
        mBachelorLayout.setVisibility(View.INVISIBLE);
        //mMasterLayout.setVisibility(View.INVISIBLE);
        mDoctorLayout.setVisibility(View.INVISIBLE);

        robotAPI.robot.speakAndListen("Please hold on for a moment", new SpeakConfig().timeout(1));
    }

    public static void setInfoLayout() {

        mMainMenuLayout.setVisibility(View.INVISIBLE);

        mTourLayout.setVisibility(View.INVISIBLE);

        //  mMeetDataLayout.setVisibility(View.INVISIBLE);
        mMeetPositionLayout.setVisibility(View.INVISIBLE);
        //  mMeetShowDataLayout.setVisibility(View.INVISIBLE);

        //Info Layout
        mInfoLayout.setVisibility(View.VISIBLE);
        mQRLayout.setVisibility(View.INVISIBLE);
        //  mOfficeLayout.setVisibility(View.INVISIBLE);
        mLecturerLayout.setVisibility(View.INVISIBLE);
        mBachelorLayout.setVisibility(View.INVISIBLE);
        //  mMasterLayout.setVisibility(View.INVISIBLE);
        mDoctorLayout.setVisibility(View.INVISIBLE);
        mAdmissionLayout.setVisibility(View.INVISIBLE);

        robotAPI.robot.speakAndListen("You may know more about us by watching introduction video or scan the QR code", new SpeakConfig().timeout(1));
    }

    public static void setQRLayout() {

        mMainMenuLayout.setVisibility(View.INVISIBLE);

        mTourLayout.setVisibility(View.INVISIBLE);

        mMeetPositionLayout.setVisibility(View.INVISIBLE);
        //mMeetDataLayout.setVisibility(View.INVISIBLE);
        //mMeetShowDataLayout.setVisibility(View.INVISIBLE);

        mInfoLayout.setVisibility(View.INVISIBLE);
        //QR layout
        mQRLayout.setVisibility(View.VISIBLE);
        //mOfficeLayout.setVisibility(View.INVISIBLE);
        mLecturerLayout.setVisibility(View.INVISIBLE);
        mBachelorLayout.setVisibility(View.INVISIBLE);
        //mMasterLayout.setVisibility(View.INVISIBLE);
        mDoctorLayout.setVisibility(View.INVISIBLE);
        mAdmissionLayout.setVisibility(View.INVISIBLE);

        robotAPI.robot.speakAndListen("Using your phone scan QR code to access our website", new SpeakConfig().timeout(1));
    }

    public static void setLecturerLayout(){
        mMainMenuLayout.setVisibility(View.INVISIBLE);

        mTourLayout.setVisibility(View.INVISIBLE);

        mMeetDataLayout.setVisibility(View.INVISIBLE);
        mMeetPositionLayout.setVisibility(View.INVISIBLE);
        mMeetShowDataLayout.setVisibility(View.INVISIBLE);

        mInfoLayout.setVisibility(View.INVISIBLE);
        mQRLayout.setVisibility(View.INVISIBLE);
        mOfficeLayout.setVisibility(View.INVISIBLE);
        //Show Lecturer Layout
        mLecturerLayout.setVisibility(View.VISIBLE);
        mBachelorLayout.setVisibility(View.INVISIBLE);
        //  mMasterLayout.setVisibility(View.INVISIBLE);
        mDoctorLayout.setVisibility(View.INVISIBLE);
        mAdmissionLayout.setVisibility(View.INVISIBLE);

        robotAPI.robot.speak("You may click the radio button to know the entry requirement");
    }

    public static void setOfficeLayout(){

        mMainMenuLayout.setVisibility(View.INVISIBLE);

        mTourLayout.setVisibility(View.INVISIBLE);

        mMeetDataLayout.setVisibility(View.INVISIBLE);
        mMeetPositionLayout.setVisibility(View.INVISIBLE);
        mMeetShowDataLayout.setVisibility(View.INVISIBLE);

        mInfoLayout.setVisibility(View.INVISIBLE);
        mQRLayout.setVisibility(View.INVISIBLE);
        mOfficeLayout.setVisibility(View.VISIBLE);
        mLecturerLayout.setVisibility(View.INVISIBLE);
        //Show Admission Layout
        mBachelorLayout.setVisibility(View.INVISIBLE);
        //  mMasterLayout.setVisibility(View.INVISIBLE);
        mDoctorLayout.setVisibility(View.INVISIBLE);
        mAdmissionLayout.setVisibility(View.INVISIBLE);

        robotAPI.robot.speak("You may click the radio button to know the entry requirement");
    }

    public static void setAdmissionLayout() {

        mMainMenuLayout.setVisibility(View.INVISIBLE);

        mTourLayout.setVisibility(View.INVISIBLE);

        mMeetDataLayout.setVisibility(View.INVISIBLE);
        mMeetPositionLayout.setVisibility(View.INVISIBLE);
        mMeetShowDataLayout.setVisibility(View.INVISIBLE);

        mInfoLayout.setVisibility(View.INVISIBLE);
        mQRLayout.setVisibility(View.INVISIBLE);
        mOfficeLayout.setVisibility(View.INVISIBLE);
        mLecturerLayout.setVisibility(View.INVISIBLE);
        //Show Admission Layout
        mBachelorLayout.setVisibility(View.INVISIBLE);
        //  mMasterLayout.setVisibility(View.INVISIBLE);
        mDoctorLayout.setVisibility(View.INVISIBLE);
        mAdmissionLayout.setVisibility(View.VISIBLE);

        robotAPI.robot.speak("You may click the radio button to know the entry requirement");
    }

    public static void setBachelorLayout() {

        mMainMenuLayout.setVisibility(View.INVISIBLE);

        mTourLayout.setVisibility(View.INVISIBLE);

        //   mMeetDataLayout.setVisibility(View.INVISIBLE);
        mMeetPositionLayout.setVisibility(View.INVISIBLE);
        //   mMeetShowDataLayout.setVisibility(View.INVISIBLE);

        mInfoLayout.setVisibility(View.INVISIBLE);
        mQRLayout.setVisibility(View.INVISIBLE);
        //   mOfficeLayout.setVisibility(View.INVISIBLE);
        mLecturerLayout.setVisibility(View.INVISIBLE);
        //Show Bachelor Layout
        mBachelorLayout.setVisibility(View.VISIBLE);
        //  mMasterLayout.setVisibility(View.INVISIBLE);
        mDoctorLayout.setVisibility(View.INVISIBLE);
        mAdmissionLayout.setVisibility(View.INVISIBLE);

        robotAPI.robot.speakAndListen("There are 4 courses offered in FTSM, which are computer science, " +
                "information technology, software engineering information system " +
                "and software engineering multimedia", new SpeakConfig().timeout(1));
    }

    public static void setMasterLayout() {
        mMainMenuLayout.setVisibility(View.INVISIBLE);

        mTourLayout.setVisibility(View.INVISIBLE);

        mMeetDataLayout.setVisibility(View.INVISIBLE);
        mMeetPositionLayout.setVisibility(View.INVISIBLE);
        mMeetShowDataLayout.setVisibility(View.INVISIBLE);

        mInfoLayout.setVisibility(View.INVISIBLE);
        mQRLayout.setVisibility(View.INVISIBLE);
        mOfficeLayout.setVisibility(View.INVISIBLE);
        mLecturerLayout.setVisibility(View.INVISIBLE);
        //Show Master Layout
        mBachelorLayout.setVisibility(View.INVISIBLE);
        mMasterLayout.setVisibility(View.VISIBLE);
        mDoctorLayout.setVisibility(View.INVISIBLE);
        mAdmissionLayout.setVisibility(View.VISIBLE);

        robotAPI.robot.speak("");
    }

    public static void setDoctorLayout() {
        mMainMenuLayout.setVisibility(View.INVISIBLE);

        mTourLayout.setVisibility(View.INVISIBLE);

        mMeetDataLayout.setVisibility(View.INVISIBLE);
        mMeetPositionLayout.setVisibility(View.INVISIBLE);
        mMeetShowDataLayout.setVisibility(View.INVISIBLE);

        mInfoLayout.setVisibility(View.INVISIBLE);
        mQRLayout.setVisibility(View.INVISIBLE);
        mOfficeLayout.setVisibility(View.INVISIBLE);
        mLecturerLayout.setVisibility(View.INVISIBLE);
        //Show Doctor Layout
        mBachelorLayout.setVisibility(View.INVISIBLE);
        //mMasterLayout.setVisibility(View.INVISIBLE);
        mDoctorLayout.setVisibility(View.VISIBLE);
        mAdmissionLayout.setVisibility(View.INVISIBLE);

        robotAPI.robot.speak("Hi there, doctor of philosophy");
    }

    //RADIO FOR Office
    public void checkOfficeButton(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.dean:
                if (checked) {
                    iHepa.setVisibility(View.INVISIBLE);
                    iDean.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.hepa:
                if (checked) {
                    iHepa.setVisibility(View.VISIBLE);
                    iDean.setVisibility(View.INVISIBLE);
                }
                break;
        }
    }

    //RADIO FOR Admission
    public void checkButton(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.malaysian:
                if (checked) {
                    iNonCitizen.setVisibility(View.INVISIBLE);
                    iCitizen.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.international:
                if (checked) {
                    iNonCitizen.setVisibility(View.VISIBLE);
                    iCitizen.setVisibility(View.INVISIBLE);
                }
                break;
        }
    }

    //POSTION List
    public void meetWithYou(View view){
        switch(view.getId()){
            case R.id.p0:
                setMeetFillUserDataLayout();
                title.setText("");
                title1 = title;
                break;

            case R.id.p1:
                setMeetFillUserDataLayout();
                title.setText("");
                title1 = title;
                break;

            case R.id.p2:
                setMeetFillUserDataLayout();
                title.setText("");
                title1 = title;
                break;

            case R.id.p3:
                setMeetFillUserDataLayout();
                title.setText("");
                title1 = title;
                break;

            case R.id.p4:
                setMeetFillUserDataLayout();
                title.setText("");
                title1.setText("");;
                break;

            case R.id.p5:
                setMeetFillUserDataLayout();
                title.setText("");
                title1 = title;
                break;

            case R.id.p6:
                setMeetFillUserDataLayout();
                title.setText("");
                title1 = title;
                break;

            case R.id.p7:
                setMeetFillUserDataLayout();
                title.setText("");
                title1 = title;
                break;
        }
    }

}

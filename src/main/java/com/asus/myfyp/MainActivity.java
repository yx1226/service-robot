package com.asus.myfyp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.asus.robotframework.API.ExpressionConfig;
import com.asus.robotframework.API.RobotCallback;
import com.asus.robotframework.API.RobotCmdState;
import com.asus.robotframework.API.RobotErrorCode;
import com.asus.robotframework.API.RobotFace;
import com.asus.robotframework.API.RobotUtil;
import com.asus.robotframework.API.SpeakConfig;
import com.asus.robotframework.API.results.RoomInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.robot.asus.robotactivity.RobotActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends RobotActivity {
    public final static String TAG = "myFYP";
    public final static String DOMAIN = "15688E5096794A6EBBD04C82647ACA03";

    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    private static boolean isRobotApiInitialed = false;

    private static ConstraintLayout mMainMenuLayout, mTourLayout, mMeetLayout, mAboutUsLayout, mPositionLayout, mPopupLayout;
    private static LinearLayout container;
    private TextView mTextView, mInfoTextView, mTitleView;
    private static Button mTourButton, mMeetButton, mAboutUsButton, mSubmitButton, mConfirmButton, mHomeButton, mQRButton;
    private static ImageView mImageQR;
    private static EditText mTextName, mTextPhone;
    private static ImageButton mCheckpointButton, mCheckpoint1Button, mCheckpoint2Button;
    static int kira = 0;
    static boolean stateStatus = false;

    private static String mapRoom;

    private static final String DATABASE_NAME = "";

    //Database
    Outsider outsider = new Outsider();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference().child("Outsider");
    int id;

    //Database time and date


    //@SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Layout
        mMainMenuLayout = findViewById(R.id.lyt_main);
        mTourLayout = findViewById(R.id.lyt_tour);
        mMeetLayout = findViewById(R.id.lyt_meet);
        mAboutUsLayout = findViewById(R.id.lyt_info);
        mPositionLayout = findViewById(R.id.lyt_positionlist);
        mPopupLayout = findViewById(R.id.lyt_qr);

        //ScrollView
        container = findViewById(R.id.linerLayout_staff);
        container.scrollTo(220, 400);

        // open MainMenuLayout
        mHomeButton = findViewById(R.id.btn_home);
        mHomeButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {setMainMenuLayout();}
        });

        mHomeButton = findViewById(R.id.btn_endtour);
        mHomeButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {setMainMenuLayout();}
            // robotAPI.robot.speak("Tour activity end...")
        });

        mHomeButton = findViewById(R.id.btn_home);
        mHomeButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {setMainMenuLayout();}
        });

        //Open TourLayout
        mTourButton = findViewById(R.id.btn_tour);
        mTourButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {setTourLayout();}
        });

        //Open MeetLayout
        mMeetButton = findViewById(R.id.btn_meet);
        mMeetButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {setViewPositionList();}
        });

        //mConfirmButton = findViewById(R.id.btn_confirm);
        mConfirmButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {setMeetLayout();}
        });

        //Open InfoLayout
        mAboutUsButton = findViewById(R.id.btn_info);
        mAboutUsButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {setAboutUsLayout();}
        });

        //Insert video introduce FTSM
        VideoView videoView = findViewById(R.id.videoViewer);
        //Set MediaController to play, pause , .. etc option
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        //Location of Media file
        Uri uri = Uri.parse("android.resouce://" + getPackageName() + "/" + R.raw.video);

        videoView.setMediaController(mediaController);
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.start();

        //Popup window to scan the QR code
        mQRButton = findViewById(R.id.btn_qrcode);
        mQRButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Popwindow
                setPopUpWindow();
            }
        });


        //Data
        final ArrayList <String> mPositionList = new ArrayList<>();
        mPositionList.add("Dean");
        mPositionList.add("Deputy Dean(Academic)");
        mPositionList.add("Deputy Dean (Research & Innovation)");
        mPositionList.add("Deputy Dean (Networking and Income Generation)");
        mPositionList.add("Assistant Dean (Quality and Strategy)");
        mPositionList.add("Assistant Dean (Teaching and CITRA)");
        mPositionList.add("Assistant Dean (Entrepreneurship and Creativity)");
        mPositionList.add("Assistant Dean (Students and Alumni Affairs)");
        mPositionList.add("Head of Senior Assistant Registrar");
        mPositionList.add("Senior Assistant Registrar");
        mPositionList.add("ICT Manager");

        /*
        String title = recyclerView.findViewHolderForAdapterPosition(mPosition).itemView.findViewById(R.id.getText).getText().toString();
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListner(){
            public void onClick(View view, int position){
                String title1 = recyclerView.findViewHolderForAdapterPosition(mPosition).itemView.findViewById(R.id.getText).getText().toString();
                Toast.makeText(getApplicationContext(), title1, Toast.LENGTH_SHORT).show();
            }

            public void onLongClick(View view, int position){

            }
        }));

         */

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        /** Meet someone
         *
         * 1. User key in his/ her name and phone no.
         * 2. Submit --> when the form is completely filled.
         * 3. Reset --> clear the form text
         * 4. Home Button
         * 5. TakePhoto --> call the camera and then need to save the photo and upload the photo
         * 6. ID of the outsider is incremental
         *
         * **/
        mTextName = findViewById(R.id.editText_name);
        mTextPhone = findViewById(R.id.editText_phone);

        mSubmitButton = findViewById(R.id.btn_submit);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                outsider.setName(mTextName.getText().toString().trim());
                outsider.setPhone(mTextPhone.getText().toString().trim());

                // ID for the Database
                myRef.child(String.valueOf(id+1)).setValue(outsider);


                if(mTextName.getText().toString().equals("") || mTextPhone.getText().toString().equals("")){

                }

                else{
                    Toast.makeText(MainActivity.this, "Data inserted successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });

       /** public void addData(){
            mSubmitButton.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    if(mTextPhone.getText().toString().contains("")){
                        throw new IllegalAccessException("SQL ");
                    }
                    else{

                    }
                }
            });
        }
        **/
        //ID of outsider
        myRef.addValueEventListener(new ValueEventListener(){
            public void onDataChange(@NonNull DataSnapshot dataSnapshot){
                if(dataSnapshot.exists()){
                    id = (int) dataSnapshot.getChildrenCount();
                }
            }

            public void onCancelled(@NonNull DatabaseError databaseError){

            }
        });

        Log.d(TAG, "onCreate: started");


        mCheckpointButton = findViewById(R.id.checkpoint);
        mCheckpointButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                robotAPI.robot.speak("Start our tour at dean office which is head of administration in FTSM.");

                ArrayList<RoomInfo> arrayListRooms = robotAPI.contacts.room.getAllRoomInfo();
                mapRoom = arrayListRooms.get(0).keyword; //ward

                if(!mapRoom.equals("")) {

                    if(isRobotApiInitialed) {
                        // use robotAPI to go to the position "keyword":
                        robotAPI.motion.goTo(mapRoom);
                    }
                }
            }
        });

        mCheckpoint1Button = findViewById(R.id.checkpoint1);
        mCheckpoint1Button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                robotAPI.robot.speak("Proceed to next checkpoint which is our staff ");
                ArrayList<RoomInfo> arrayListRooms = robotAPI.contacts.room.getAllRoomInfo();
                mapRoom = arrayListRooms.get(1).keyword; //emergency

                if(!mapRoom.equals("")) {
                    if(isRobotApiInitialed) {
                        // use robotAPI to go to the position "keyword":
                        robotAPI.motion.goTo(mapRoom);
                    }

                }
            }
        });

        mCheckpoint2Button = findViewById(R.id.checkpoint2);
        mCheckpoint2Button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                robotAPI.robot.speak("Okay, let's go to checkpoint 2... Here are meeting room and vice dean office");

                ArrayList<RoomInfo> arrayListRooms = robotAPI.contacts.room.getAllRoomInfo();
                mapRoom = arrayListRooms.get(0).keyword; //ward

                if(!mapRoom.equals("")) {
                    if(isRobotApiInitialed) {
                        // use robotAPI to go to the position "keyword":
                        robotAPI.motion.goTo(mapRoom);
                    }
                }
            }
        });

        mCheckpointButton = findViewById(R.id.checkpoint);
        mCheckpointButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                robotAPI.robot.speak("Okay, lets go to the dean's office");
                ArrayList<RoomInfo> arrayListRooms = robotAPI.contacts.room.getAllRoomInfo();
                mapRoom = arrayListRooms.get(2).keyword; //lobby

                if(!mapRoom.equals("")) {
                    if(isRobotApiInitialed) {
                        // use robotAPI to go to the position "keyword":
                        robotAPI.motion.goTo(mapRoom);
                    }
                }
            }
        });

        mCheckpoint1Button= findViewById(R.id.checkpoint1);
        mCheckpoint1Button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                robotAPI.robot.speak("Okay, lets go to checkpoint 1!");
                ArrayList<RoomInfo> arrayListRooms = robotAPI.contacts.room.getAllRoomInfo();
                mapRoom = arrayListRooms.get(0).keyword; //ward

                if(!mapRoom.equals("")) {
                    if(isRobotApiInitialed) {
                        // use robotAPI to go to the position "keyword":
                        robotAPI.motion.goTo(mapRoom);
                    }
                }
            }
        });

        mCheckpoint2Button= findViewById(R.id.checkpoint2);
        mCheckpoint2Button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                robotAPI.robot.speak("Okay, lets move on to checkpoint 2.");
                ArrayList<RoomInfo> arrayListRooms = robotAPI.contacts.room.getAllRoomInfo();
                mapRoom = arrayListRooms.get(1).keyword; //emergency

                if(!mapRoom.equals("")) {
                    if(isRobotApiInitialed) {
                        // use robotAPI to go to the position "keyword":
                        robotAPI.motion.goTo(mapRoom);
                    }
                }
            }
        });
    }

    /*
    private void initImageBitmaps(){
        int d = Log.d(MyRecyclerViewAdapter.RecycleTAG, "initImageBitmaps: preparing bitmaps");

        mImageUrls.add("");

        initRecyclerView();
    }

    private void initRecyclerView(){
        Log.d(TAG, "");
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(mNames, mImageUrls, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
     */

    @Override
    protected void onResume() {
        super.onResume();

        setMainMenuLayout();

        // check permission READ_CONTACTS is granted or not
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted by user yet
            Log.d("ZenboGoToLocation", "READ_CONTACTS permission is not granted by user yet");
        }
        else{
            // permission is granted by user
            Log.d("ZenboGoToLocation", "READ_CONTACTS permission is granted");
        }

        // close faical
        robotAPI.robot.setExpression(RobotFace.HIDEFACE);

        robotAPI.robot.setVoiceTrigger(false);

        // jump dialog domain
        robotAPI.robot.jumpToPlan(DOMAIN, "launchHospitalApp");

        // listen user utterance
       robotAPI.robot.speakAndListen("Welcome to FTSM, Im Zenbo. Im the robot receptionist for this office.\n" +
                "        Which is in dean office Faculty of Technology and Science Information.\n" +
                "        Any help can I serve you?   \n" +
                "        You may also touch the screen to proceed the service.", new SpeakConfig().timeout(20));

        requestPermission();
    }

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
        }

        else {
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
    protected void onPause() {
        super.onPause();
        //stop listen user utterance
        robotAPI.robot.stopSpeakAndListen();
    }

    @Override
    public void onBackPressed() {

        android.os.Process.killProcess(android.os.Process.myPid());
        // This above line close correctly
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

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

            isRobotApiInitialed = true;

        }

        @Override
        public void onDetectFaceResult(java.util.List resultList){
            super.onDetectFaceResult(resultList);
        }

    };

    public static RobotCallback.Listen robotListenCallback = new RobotCallback.Listen() {

        @Override
        public void onFinishRegister() {
        }

        @Override
        public void onVoiceDetect(JSONObject jsonObject) { }

        @Override
        public void onSpeakComplete(String s, String s1) {}

        @Override
        public void onEventUserUtterance(JSONObject jsonObject) {
            String text;
            text = "onEventUserUtterance: " + jsonObject.toString();
            Log.d(TAG, text);
        }

        public void onDetectFaceResult(JSONObject jsonObject){

            robotAPI.robot.speak("Thank you for ordered with Zenbo, wish you have a nice day");

        }

        //Mapping
        @Override
        public void onResult(JSONObject jsonObject) {
            String text;
            text = "onResult: " + jsonObject.toString();
            Log.d(TAG, text);

            ExpressionConfig expConfig = new ExpressionConfig();


            String sIntentionID = RobotUtil.queryListenResultJson(jsonObject, "IntentionId");
            Log.d(TAG, "Intention Id = " + sIntentionID);

            if(sIntentionID.equals("hospitalApp")) {

                String SLUvisitor = RobotUtil.queryListenResultJson(jsonObject, "myVisitor", null);
                String SLUpatient = RobotUtil.queryListenResultJson(jsonObject, "myPatient", null);

                Log.d(TAG, "Result Visitor = " + SLUvisitor);
                Log.d(TAG, "Result Patient = " + SLUpatient);

             
                if( ( SLUvisitor).equals("null")) { }
                else {

                    ArrayList<RoomInfo> arrayListRooms = robotAPI.contacts.room.getAllRoomInfo();

                    robotAPI.robot.stopSpeakAndListen();

                    if (SLUvisitor.equals("visitor")){

                        setAboutUsLayout();
                    }

                    if (SLUvisitor.equals("where is lobby") || SLUvisitor.equals("lobby")){

                        robotAPI.robot.speak("Now you are located at lobby of dean office");
                        mapRoom = arrayListRooms.get(2).keyword; //lobby

                        if(!mapRoom.equals("")) {

                            if(isRobotApiInitialed) {
                                // use robotAPI to go to the position "keyword":
                                robotAPI.motion.goTo(mapRoom);
                            }

                        }
                    }

                    if (SLUvisitor.equals("Setiausaha Dekan")){

                        robotAPI.robot.speak("Okay, lets go to the ward!");
                        mapRoom = arrayListRooms.get(0).keyword; //ward

                        if(!mapRoom.equals("")) {

                            if(isRobotApiInitialed) {
                                // use robotAPI to go to the position "keyword":
                                robotAPI.motion.goTo(mapRoom);
                            }

                        }
                    }

                    if (SLUvisitor.equals("Checkpoint 2")){

                        robotAPI.robot.speak("Okey, lets go to the shop. You can shopping anything you want.");
                        mapRoom = arrayListRooms.get(1).keyword; //emergency

                        if(!mapRoom.equals("")) {

                            if(isRobotApiInitialed) {
                                // use robotAPI to go to the position "keyword":
                                robotAPI.motion.goTo(mapRoom);
                            }

                        }
                    }


                }

                if ( SLUpatient.equals("Checkpoint 3")){ }
                else {

                    ArrayList<RoomInfo> arrayListRooms = robotAPI.contacts.room.getAllRoomInfo();

                    robotAPI.robot.setExpression(RobotFace.HIDEFACE);

                    if (SLUpatient.equals("i am patient") ){

                        setMeetLayout();
                    }

                    if (SLUpatient.equals("Chekcpoint 4")){

                        robotAPI.robot.speak("Lets go to the pharmacy");
                        mapRoom = arrayListRooms.get(2).keyword; //lobby

                        if(!mapRoom.equals("")) {

                            if(isRobotApiInitialed) {
                                // use robotAPI to go to the position "keyword":
                                robotAPI.motion.goTo(mapRoom);
                            }

                        }
                    }

                    if (SLUpatient.equals("Washroom") || SLUpatient.equals("Toilet") || SLUpatient.equals("Tandas")){

                        robotAPI.robot.speak("Here is the washroom of dean office.");
                        mapRoom = arrayListRooms.get(1).keyword; //emergency

                        if(!mapRoom.equals("")) {

                            if(isRobotApiInitialed) {
                                // use robotAPI to go to the position "keyword":
                                robotAPI.motion.goTo(mapRoom);
                            }

                        }
                    }

                    if (SLUpatient.equals("Meeting room")){

                        robotAPI.robot.speak("Lets go to meeting room.... you are not allow to go in if you are not invited");
                        mapRoom = arrayListRooms.get(0).keyword; //ward

                        if(!mapRoom.equals("")) {

                            if(isRobotApiInitialed) {
                                // use robotAPI to go to the position "keyword":
                                robotAPI.motion.goTo(mapRoom);
                            }

                        }
                    }

                    robotAPI.robot.speakAndListen("There are anything else i can do for you??? ",new SpeakConfig().timeout(5));

                }

            }
        }


        @Override
        public void onRetry(JSONObject jsonObject) {
        }
    };

    public void setMainMenuLayout() {

        mMainMenuLayout.setVisibility(View.VISIBLE);
        mMeetLayout.setVisibility(View.INVISIBLE);
        mTourLayout.setVisibility(View.INVISIBLE);
        mAboutUsLayout.setVisibility(View.INVISIBLE);
    }

    public static void setMeetLayout() {

        mMainMenuLayout.setVisibility(View.INVISIBLE);
        mMeetLayout.setVisibility(View.VISIBLE);
        mTourLayout.setVisibility(View.INVISIBLE);
        mAboutUsLayout.setVisibility(View.INVISIBLE);

        robotAPI.robot.speak("Who would you like to meet?");

    }

    public static void setViewPositionList() {
        mMainMenuLayout.setVisibility(View.INVISIBLE);
        mMeetLayout.setVisibility(View.INVISIBLE);
        mTourLayout.setVisibility(View.INVISIBLE);
        mAboutUsLayout.setVisibility(View.INVISIBLE);
        mPositionLayout.setVisibility(View.VISIBLE);
        mPopupLayout.setVisibility(View.INVISIBLE);

        robotAPI.robot.speak("Who you want to meet? ");
    }

    public static void setTourLayout() {

        mMainMenuLayout.setVisibility(View.INVISIBLE);
        mMeetLayout.setVisibility(View.INVISIBLE);
        mTourLayout.setVisibility(View.VISIBLE);
        mAboutUsLayout.setVisibility(View.INVISIBLE);
        mPositionLayout.setVisibility(View.INVISIBLE);
        mPopupLayout.setVisibility(View.INVISIBLE);

        robotAPI.robot.speak("Are you ready to start our tour?");
    }

    public static void setAboutUsLayout() {

        mMainMenuLayout.setVisibility(View.INVISIBLE);
        mMeetLayout.setVisibility(View.INVISIBLE);
        mTourLayout.setVisibility(View.INVISIBLE);
        mAboutUsLayout.setVisibility(View.VISIBLE);
        mPositionLayout.setVisibility(View.INVISIBLE);
        mPopupLayout.setVisibility(View.INVISIBLE);

        robotAPI.robot.speak("Know more about us");
    }

    public void setPopUpWindow(){
        mPopupLayout.setVisibility(View.VISIBLE);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width* .8), (int)(height*.6));

        robotAPI.robot.speak("Please scan this QR code to access the FTSM website.");
    }


    public MainActivity() {
        super(robotCallback, robotListenCallback);
    }
}

/*
public class Adapter extends RecyclerView.Adapter<Adapter.myViewHolder> {

    Context context;
    List<String> mData;

    public Adapter(Context context, List<String> data){
        this.context = context;
        this.mData = data;
    }

    public Adapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.support_simple_spinner_dropdown_item);
        return view;
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        holder.country.setText(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder{
        TextView country;

        public myViewHolder(View itemView){
            super(itemView);
            country = itemView.findViewById(R.id.recyclerPosition);
        }
    }

 */

public class SizeAdapter extends RecyclerView.Adapter<SizeAdapter.ViewHolder> {

    private List<SizeConstructor> sizeConstructorList;
    private Context context;

    public SizeAdapter(List<SizeConstructor> sizeConstructorList, Context context) {
        this.sizeConstructorList = sizeConstructorList;
        this.context = context;
    }

    @Override
    public SizeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.size_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SizeAdapter.ViewHolder holder, int position) {
        SizeConstructor sizeConstructor = sizeConstructorList.get(position);
        holder.button1.setText(sizeConstructor.getSize());
    }

    @Override
    public int getItemCount() {
        return sizeConstructorList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView button1;

        public ViewHolder(View itemView) {
            super(itemView);

            button1 = (TextView)itemView.findViewById(R.id.SizeButton);
        }
    }
}

class SizeConstructor {

    String Size;

    public SizeConstructor(ArrayList<String> sizeArray) {
    }

    public SizeConstructor(String size) {
        Size = size;
    }

    public String getSize() {
        return Size;
    }
}

package cn.moecity.virtualpet;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences.Editor;

public class MainActivity extends Activity {

    private SharedPreferences petData,userData;
    private Pet myPet;
    private Boolean isDied = false, isOpened = false,isNew=false;
    private Date date = new Date();
    private Long now, collectTime, current;
    private TextView txt, skillTxt, balanceTxt, timeTxt, hungTxt, dirtyTxt,
            illTxt;
    private Button food1Button, food2Button, food3Button, collectButton,
            moreButton;
    private LinearLayout btnsLayout, barLayout;
    private ProgressBar hungBar, dirtyBar, illBar;
    private String[] skillStr = { "I can ", "jump ", "dance ", "sing ", "cry ",
            "shy ", "kiss ", "angry ", "hug " };
    private static String msg = "";
    private long feedgap, bathgap, cleangap;
    private int balance;
    private Timer timer;
    private Food food1, food2, food3;
    private Food milk, bread, hotdog, sandwitch, hamburger, chicken, steak;
    private float mPosY, mPosX, mCurPosY, mCurPosX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        OnTouchListener l = new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                // TODO Auto-generated method stub
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mPosX = event.getX();
                        mPosY = event.getY();
                        Log.e("down", mPosX + "," + mPosY);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mCurPosX = event.getX();
                        mCurPosY = event.getY();
                        Log.e("up", mCurPosX + "," + mCurPosY);

                        break;
                    case MotionEvent.ACTION_UP:
                        if (mCurPosY - mPosY > 0
                                && (Math.abs(mCurPosY - mPosY) > 25)) {
                            if (isOpened) {
                                moreButton.setText("^");
                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT, 0,
                                        0.0f);
                                btnsLayout.setLayoutParams(lp);
                                isOpened = false;
                            }

                        } else if (mCurPosY - mPosY < 0
                                && (Math.abs(mCurPosY - mPosY) > 25)) {
                            if (!isOpened) {
                                moreButton.setText("v");
                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT, 0,
                                        1.5f);
                                btnsLayout.setLayoutParams(lp);
                                isOpened = true;
                            }
                        }

                        break;
                }
                return true;
            }
        };
        barLayout.setOnTouchListener(l);
        timer = new Timer();
        setTimerTask();
    }

    private void setTimerTask() {
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                Message message = new Message();
                message.what = 1;
                doActionHandler.sendMessage(message);
            }
        }, 500, 1000);
    }

    private Handler doActionHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int msgId = msg.what;
            switch (msgId) {
                case 1:
                    int illPoint = myPet.getIllPoint();
                    int hungPoint = myPet.getHungPoint();
                    int dirtyPoint = myPet.getDirtyPoint();
                    setFood();
                    date = new Date();
                    current = date.getTime() / 1000 - 1511450000;
                    checkCollect();
                    feedgap = current - myPet.getFeedTime();
                    bathgap = current - myPet.getBathTime();
                    cleangap = current - myPet.getCleanTime();

                    myPet.setIllPoint(dirtyPoint / 2 + hungPoint / 2);
                    balanceTxt.setText("£" + balance);
                    timeTxt.setText("Lv."+ myPet.getpLevel() + "\nbath gap: " + bathgap
                            + " clean gap: " + cleangap + " feed gap: " + feedgap);
                    hungTxt.setText("Hungery Point: " + hungPoint);
                    dirtyTxt.setText("Dirty Point: " + dirtyPoint);
                    illTxt.setText("Ill Point: " + illPoint);
                    hungBar.setProgress(10 - hungPoint);
                    dirtyBar.setProgress(10 - dirtyPoint);
                    illBar.setProgress(10 - illPoint);

                    if (illPoint >= 10 && !isDied) {
                        Toast.makeText(getApplicationContext(), "You Died",
                                Toast.LENGTH_SHORT).show();
                        isDied = true;
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
        timer = null;
    }

    private void init() {
        creatFood();
        petData = getSharedPreferences("myPet", MODE_PRIVATE);
        userData = getSharedPreferences("myUser", MODE_PRIVATE);
        now = date.getTime() / 1000 - 1511450000;
        btnsLayout = (LinearLayout) findViewById(R.id.btnsLayout);
        barLayout = (LinearLayout) findViewById(R.id.barLayout);
        txt = (TextView) findViewById(R.id.textView1);
        skillTxt = (TextView) findViewById(R.id.textView2);
        balanceTxt = (TextView) findViewById(R.id.textView3);
        timeTxt = (TextView) findViewById(R.id.timeView);
        hungTxt = (TextView) findViewById(R.id.hungView);
        dirtyTxt = (TextView) findViewById(R.id.dirtyView);
        illTxt = (TextView) findViewById(R.id.illView);
        hungBar = (ProgressBar) findViewById(R.id.progressBar1);
        dirtyBar = (ProgressBar) findViewById(R.id.progressBar2);
        illBar = (ProgressBar) findViewById(R.id.progressBar3);
        food1Button = (Button) findViewById(R.id.milkButton);
        food2Button = (Button) findViewById(R.id.breadButton);
        food3Button = (Button) findViewById(R.id.hotdogButton);
        collectButton = (Button) findViewById(R.id.collectButton);
        moreButton = (Button) findViewById(R.id.moreButton);
        myPet = new Pet();
        isNew=userData.getBoolean("isNew",false);
        if (isNew)
        {
            Editor editor = petData.edit();
            editor.putLong("bathTime", now);
            editor.putLong("cleanTime", now);
            editor.putLong("feedTime", now);
            editor.commit();
            myPet.setBathTime(now);
            myPet.setFeedTime(now);
            myPet.setCleanTime(now);
            isNew=false;
            Editor editor1 = userData.edit();
            editor1.putBoolean("isNew",false);
            editor1.commit();
        }
        myPet.setuID(petData.getInt("uID", 1));
        myPet.setpName(petData.getString("pName","NoName"));
        myPet.setIllPoint(petData.getInt("illPoint", 1));
        myPet.setDirtyPoint(petData.getInt("dirtyPoint", 1));
        myPet.setHungPoint(petData.getInt("hungPoint", 1));
        myPet.setpLevel(petData.getInt("pLevel", 1));
        myPet.setExperience(petData.getInt("experience", 100));
        myPet.setBathTime(petData.getLong("bathTime", now));
        myPet.setCleanTime(petData.getLong("cleanTime", now));
        myPet.setFeedTime(petData.getLong("feedTime", now));
        myPet.setpSkill(petData.getInt("pSkill", 0));
        balance = petData.getInt("balance", 1000);
        collectTime = petData.getLong("collectTime", 0);
        isNew=userData.getBoolean("isNew",false);
        if (isNew)
        {
            myPet.setBathTime(now);
            myPet.setFeedTime(now);
            myPet.setCleanTime(now);
            collectTime= Long.valueOf(0);
            isNew=false;
            Editor editor1 = userData.edit();
            editor1.putBoolean("isNew",false);
            editor1.commit();
        }
        skillShow(myPet);
        setFood();
        skillTxt.setText(msg);
        txt.setText(myPet.toString());
        balanceTxt.setText("£" + balance);
        date = new Date();
        int illPoint = myPet.getIllPoint();
        int hungPoint = myPet.getHungPoint();
        int dirtyPoint = myPet.getDirtyPoint();
        current = date.getTime() / 1000 - 1511450000;
        checkCollect();
        feedgap = current - myPet.getFeedTime();
        bathgap = current - myPet.getBathTime();
        cleangap = current - myPet.getCleanTime();
        if (feedgap >= 7200 && illPoint < 10) {
            myPet.setHungPoint((int) (hungPoint + (feedgap / 14400)));
            if (hungPoint >= 10) {
                myPet.setHungPoint(10);
                myPet.setFeedTime(current);
            }
        }
        if ((bathgap >= 7200 || cleangap >= 7200) && illPoint < 10) {
            myPet.setDirtyPoint((int) (dirtyPoint + bathgap / 57600 + cleangap / 57600));
            if (dirtyPoint >= 10) {
                myPet.setDirtyPoint(10);
                myPet.setBathTime(current);
                myPet.setCleanTime(current);
            }
        }
    }

    private void creatFood() {
        milk = new Food("Milk", 1, 5, 2, 1, 10);
        bread = new Food("Bread", 3, 10, 1, 2, 15);
        hotdog = new Food("Hotdog", 3, 10, 2, 3, 15);
        sandwitch = new Food("Sandwitch", 6, 20, 3, 4, 20);
        hamburger = new Food("Hamburger", 11, 30, 5, 7, 40);
        chicken = new Food("Chicken", 11, 30, 10, 10, 80);
        steak = new Food("Steak", 21, 30, 20, 20, 100);
    }

    private void setFood() {
        if (myPet.getpLevel() >= 1 && myPet.getpLevel() <= 5) {
            food1 = milk;
            food2 = new Food();
            food3 = new Food();
            if (myPet.getpLevel() >= 3) {
                food2 = bread;
                food3 = hotdog;
            }
        } else if (myPet.getpLevel() >= 6 && myPet.getpLevel() <= 10) {
            food1 = bread;
            food2 = hotdog;
            food3 = sandwitch;
        } else if (myPet.getpLevel() >= 11 && myPet.getpLevel() <= 20) {
            food1 = sandwitch;
            food2 = hamburger;
            food3 = chicken;
        } else if (myPet.getpLevel() >= 21 && myPet.getpLevel() <= 30) {
            food1 = hamburger;
            food2 = chicken;
            food3 = steak;
        }
        food1Button.setText(food1.getFoodName());

        food2Button.setText(food2.getFoodName());

        food3Button.setText(food3.getFoodName());
    }

    private void checkCollect() {
        if (current - collectTime >= 7200)
            collectButton.setText("Yes");
        else
            collectButton.setText("No");
    }

    private void feedFunction(Food foodUsed) {
        if (myPet.getpLevel() <= foodUsed.getPlevelEnd()
                && myPet.getpLevel() >= foodUsed.getPlevelStart()
                && myPet.getHungPoint() > 0) {
            myPet.setHungPoint(myPet.getHungPoint() - foodUsed.getfHung());
            if (myPet.getHungPoint() < 0)
                myPet.setHungPoint(0);
            balance = balance - foodUsed.getfCoast();
            myPet.setExperience(myPet.getExperience() + foodUsed.getfExp());
            DataMethods.levelCheck(myPet);
            date = new Date();
            now = date.getTime() / 1000 - 1511450000;
            myPet.setFeedTime(now);
            skillShow(myPet);
            skillTxt.setText(msg);
            txt.setText(myPet.toString());
        } else if (myPet.getHungPoint() <= 0)
            Toast.makeText(getApplicationContext(), "I'm not hungery now!",
                    Toast.LENGTH_SHORT).show();
    }

    private void skillShow(Pet myPet) {
        switch (myPet.getpSkill()) {
            case 0:
                msg = skillStr[0] + "do nothing, I'm just a baby!";
                break;
            case 1:
                msg = skillStr[0] + skillStr[1];
                break;
            case 2:
                msg = skillStr[0] + skillStr[1] + skillStr[2];
                break;
            case 3:
                msg = skillStr[0] + skillStr[1] + skillStr[2] + skillStr[3];
                break;
            case 4:
                msg = skillStr[0] + skillStr[1] + skillStr[2] + skillStr[3]
                        + skillStr[4];
                break;
            case 5:
                msg = skillStr[0] + skillStr[1] + skillStr[2] + skillStr[3]
                        + skillStr[4] + skillStr[5];
                break;
            case 6:
                msg = skillStr[0] + skillStr[1] + skillStr[2] + skillStr[3]
                        + skillStr[4] + skillStr[5] + skillStr[6];
                break;
            case 7:
                msg = skillStr[0] + skillStr[1] + skillStr[2] + skillStr[3]
                        + skillStr[4] + skillStr[5] + skillStr[6] + skillStr[7];
                break;
            case 8:
                msg = skillStr[0] + skillStr[1] + skillStr[2] + skillStr[3]
                        + skillStr[4] + skillStr[5] + skillStr[6] + skillStr[7]
                        + skillStr[8];
                break;
        }
    }

    public void handleClicked(View v) {
        switch (v.getId()) {
            case R.id.bathButton:
                if (balance > 0) {
                    if (myPet.getDirtyPoint() > 0) {
                        DataMethods.bathMethod(myPet);
                        date = new Date();
                        now = date.getTime() / 1000 - 1511450000;
                        balance = balance - 10;
                        myPet.setBathTime(now);
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "I'm very clean now!", Toast.LENGTH_SHORT).show();
                    }
                    skillShow(myPet);
                    skillTxt.setText(msg);
                    txt.setText(myPet.toString());
                }
                balanceTxt.setText("£" + balance);
                break;
            case R.id.cleanButton:
                if (balance > 0) {
                    if (myPet.getDirtyPoint() > 0) {
                        DataMethods.cleanMethod(myPet);
                        date = new Date();
                        now = date.getTime() / 1000 - 1511450000;
                        balance = balance - 10;
                        myPet.setCleanTime(now);
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "I'm very clean now!", Toast.LENGTH_SHORT).show();
                    }
                    skillShow(myPet);
                    skillTxt.setText(msg);
                    txt.setText(myPet.toString());
                }
                balanceTxt.setText("£" + balance);
                break;
            case R.id.milkButton:
                if (balance > 0) {
                    feedFunction(food1);
                }
                balanceTxt.setText("£" + balance);
                break;
            case R.id.breadButton:
                if (balance > 0) {
                    feedFunction(food2);
                }
                balanceTxt.setText("£" + balance);
                break;
            case R.id.hotdogButton:
                if (balance > 0) {
                    feedFunction(food3);
                }
                balanceTxt.setText("£" + balance);
                break;
            case R.id.cheatButton:
                myPet.setExperience(myPet.getExperience() + 100);
                balance = balance + 200;
                DataMethods.levelCheck(myPet);
                skillShow(myPet);
                skillTxt.setText(msg);
            case R.id.injectionButton:
                if (balance > 0 && myPet.getIllPoint() > 0) {
                    myPet.setBathTime(now);
                    myPet.setCleanTime(now);
                    myPet.setFeedTime(now);
                    myPet.setHungPoint(0);
                    myPet.setDirtyPoint(0);
                    myPet.setIllPoint(0);
                    balance = balance - 100;
                }
                balanceTxt.setText("£" + balance);
                txt.setText(myPet.toString());
                break;
            case R.id.killButton:
                myPet.setDirtyPoint(10);
                myPet.setHungPoint(10);
                myPet.setIllPoint(10);
                txt.setText(myPet.toString());
                break;
            case R.id.nextButton:
                startActivity(new Intent(getApplicationContext(),
                        MoveableActivity.class));
                finish();
                break;
            case R.id.collectButton:
                date = new Date();
                long clicked = date.getTime() / 1000 - 1511450000;
                if (clicked - collectTime >= 7200) {
                    collectTime = clicked;
                    balance = balance + 200;
                    Editor editor1 = petData.edit();
                    editor1.putLong("collectTime", collectTime);
                }
                break;
            case R.id.moreButton:
                if (!isOpened) {
                    moreButton.setText("v");
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT, 0, 1.5f);
                    btnsLayout.setLayoutParams(lp);
                    isOpened = true;
                } else {
                    moreButton.setText("^");
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.0f);
                    btnsLayout.setLayoutParams(lp);
                    isOpened = false;
                }
                break;
            case R.id.coreBtn:
                startActivity(new Intent(getApplicationContext(),CoreActivity.class));
                Editor editor = petData.edit();
                editor.putInt("uID", myPet.getuID());
                editor.putString("pName",myPet.getpName());
                editor.putInt("illPoint", myPet.getIllPoint());
                editor.putInt("dirtyPoint", myPet.getDirtyPoint());
                editor.putInt("hungPoint", myPet.getHungPoint());
                editor.putInt("pLevel", myPet.getpLevel());
                editor.putInt("experience", myPet.getExperience());
                editor.putLong("bathTime", myPet.getBathTime());
                editor.putLong("cleanTime", myPet.getCleanTime());
                editor.putLong("feedTime", myPet.getFeedTime());
                editor.putLong("collectTime", collectTime);
                editor.putInt("pSkill", myPet.getpSkill());
                editor.putInt("balance", balance);
                editor.commit();
                finish();
                break;
        }
    }

}

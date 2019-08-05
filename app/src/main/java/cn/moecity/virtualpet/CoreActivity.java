package cn.moecity.virtualpet;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import pl.droidsonroids.gif.GifImageView;

public class CoreActivity extends Activity {
    private int[] drawGif = new int[10];
    private Drawable moneyD, moneyD1;
    private Button[] foodBtn = new Button[3];
    private Button collectButton, bathBtn, injecBtn;
    private SharedPreferences petData, userData;
    private Pet myPet;
    private Boolean isDied = false, isOpened = false, isNew = false, isSafe = true, newLogin = false, isHidden = false;
    private Date date = new Date();
    private Long now, collectTime, current;
    private TextView balanceTxt, expTxt, levelText, nameText, illTxt;
    private ProgressBar hungBar;
    private Button[] shitBtn = new Button[5];
    private static String msg = "";
    private long feedgap, bathgap, cleangap;
    private int balance, imgNum = 8;
    private Timer timer;
    private Food food1, food2, food3, milk, bread, hotdog, sandwich, hamburger, chicken, steak;
    private String userName, phoneID = "0", phone, localPhoneID, str;
    private TextView userNameText;
    private String[] strs;
    private LinearLayout foodLayOut, barLayOut, foods, shits;
    private GifImageView petView;
    private CustomView cView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_core);
        init();

    }

    private void setTimerTask() {
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                Message message = new Message();
                message.what = 1;
                doActionHandler.sendMessage(message);
            }
        }, 1000, 3000);
    }

    private Handler doActionHandler;

    {
        doActionHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                int msgId = msg.what;
                switch (msgId) {
                    case 1:
                        updateState();
                        break;
                    default:
                        break;
                }
            }
        };
    }

    private void init() {
        creatFood();
        drawGif[0] = R.drawable.eating;
        drawGif[1] = R.drawable.jump;
        drawGif[2] = R.drawable.happy;
        drawGif[3] = R.drawable.sing;
        drawGif[4] = R.drawable.cry;
        drawGif[5] = R.drawable.shy;
        drawGif[6] = R.drawable.kiss;
        drawGif[7] = R.drawable.angry;
        drawGif[8] = R.drawable.walk;
        drawGif[9] = R.drawable.die;
        newLogin = getIntent().getBooleanExtra("newLogin", false);
        petData = getSharedPreferences("myPet", MODE_PRIVATE);
        userData = getSharedPreferences("myUser", MODE_PRIVATE);
        now = date.getTime() / 1000 - 1511450000;
        petView = (GifImageView) findViewById(R.id.petView);
        petView.setBackgroundResource(drawGif[imgNum]);
        foodLayOut = (LinearLayout) findViewById(R.id.foodLayOut);
        barLayOut = (LinearLayout) findViewById(R.id.barLayOut);
        foods = (LinearLayout) findViewById(R.id.foods);
        shits = (LinearLayout) findViewById(R.id.shits);
        cView = (CustomView) findViewById(R.id.moveLayOut);
        bathBtn = (Button) findViewById(R.id.bathButton);
        injecBtn = (Button) findViewById(R.id.injectionBtn);
        Drawable injecDraw = getResources().getDrawable(R.drawable.injection);
        injecDraw.setBounds(0, 0, 200, 200);
        injecBtn.setPadding(0, 0, 0, 0);
        injecBtn.setCompoundDrawables(null, injecDraw, null, null);
        petView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (isHidden) {
                    foodLayOut.setVisibility(View.VISIBLE);
                    barLayOut.setVisibility(View.VISIBLE);
                    isHidden = false;
                } else {
                    foodLayOut.setVisibility(View.INVISIBLE);
                    barLayOut.setVisibility(View.INVISIBLE);
                    isHidden = true;
                }
                return false;
            }
        });
        balanceTxt = (TextView) findViewById(R.id.balText);
        illTxt = (TextView) findViewById(R.id.illView);
        expTxt = (TextView) findViewById(R.id.expText);
        hungBar = (ProgressBar) findViewById(R.id.progress_horizontal);
        collectButton = (Button) findViewById(R.id.collectBtn);
        levelText = (TextView) findViewById(R.id.levelText);
        nameText = (TextView) findViewById(R.id.nameText);
        userNameText = (TextView) findViewById(R.id.userText);
        moneyD = getResources().getDrawable(R.drawable.money);
        moneyD1 = getResources().getDrawable(R.drawable.nomoney);
        moneyD.setBounds(0, 0, 100, 100);
        moneyD1.setBounds(0, 0, 100, 100);
        collectButton.setPadding(0, 0, 0, 0);
        collectButton.setCompoundDrawables(null, moneyD, null, null);
        Drawable bathDraw = getResources().getDrawable(R.drawable.bath);
        bathDraw.setBounds(0, 0, 100, 100);
        bathBtn.setPadding(0, 0, 0, 0);
        bathBtn.setCompoundDrawables(null, bathDraw, null, null);
        myPet = new Pet();
        String android_id = Settings.Secure.getString(this.getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        localPhoneID = android_id;
        petData = getSharedPreferences("myPet", MODE_PRIVATE);
        userData = getSharedPreferences("myUser", MODE_PRIVATE);

        foodBtn[0] = (Button) findViewById(R.id.foodbtn1);
        foodBtn[1] = (Button) findViewById(R.id.foodbtn2);
        foodBtn[2] = (Button) findViewById(R.id.foodbtn3);
        shitBtn[0] = (Button) findViewById(R.id.shit1);
        shitBtn[1] = (Button) findViewById(R.id.shit2);
        shitBtn[2] = (Button) findViewById(R.id.shit3);
        shitBtn[3] = (Button) findViewById(R.id.shit4);
        shitBtn[4] = (Button) findViewById(R.id.shit5);
        isNew = userData.getBoolean("isNew", false);
        userName = userData.getString("userName", "NoName");
        userNameText.setText(userName);
        phone = userData.getString("phone", "NoPhone");
        if (isNew) {
            SharedPreferences.Editor editor = petData.edit();
            editor.putLong("bathTime", now);
            editor.putLong("cleanTime", now);
            editor.putLong("feedTime", now);
            editor.commit();
            myPet.setBathTime(now);
            myPet.setFeedTime(now);
            myPet.setCleanTime(now);
            isNew = false;
            SharedPreferences.Editor editor1 = userData.edit();
            editor1.putBoolean("isNew", false);
            editor1.commit();
        }
        myPet.setuID(petData.getInt("uID", 1));
        myPet.setpName(petData.getString("pName", "NoName"));
        myPet.setIllPoint(petData.getInt("illPoint", 1));
        myPet.setHungPoint(petData.getInt("hungPoint", 1));
        myPet.setpLevel(petData.getInt("pLevel", 1));
        myPet.setExperience(petData.getInt("experience", 100));
        myPet.setBathTime(petData.getLong("bathTime", now));
        myPet.setCleanTime(petData.getLong("cleanTime", now));
        myPet.setFeedTime(petData.getLong("feedTime", now));
        myPet.setpSkill(petData.getInt("pSkill", 0));
        balance = petData.getInt("balance", 1000);
        collectTime = petData.getLong("collectTime", 0);
        isNew = userData.getBoolean("isNew", false);

        if (!newLogin) {
            MyAsyncTask myAsyncTask = new MyAsyncTask();
            myAsyncTask.execute(5000);

        } else if (isNew) {
            myPet.setBathTime(now);
            myPet.setFeedTime(now);
            myPet.setCleanTime(now);
            collectTime = Long.valueOf(0);
            isNew = false;
            SharedPreferences.Editor editor1 = userData.edit();
            editor1.putBoolean("isNew", false);
            editor1.commit();
        }
        setFood();
        expTxt.setText("Exp:" + myPet.getExperience());
        levelText.setText("Lv." + myPet.getpLevel());
        nameText.setText(myPet.getpName());
        nameText.setGravity(Gravity.CENTER);
        balanceTxt.setText(" £" + balance);
        date = new Date();
        int illPoint = myPet.getIllPoint();
        int hungPoint = 0;
        int dirtyPoint = 0;

        current = date.getTime() / 1000 - 1511450000;
        checkCollect();
        feedgap = current - myPet.getFeedTime();
        bathgap = current - myPet.getBathTime();
        cleangap = current - myPet.getCleanTime();
        if (feedgap >= 7200 && illPoint < 10) {
            hungPoint = ((int) (hungPoint + (feedgap / 14400)));
            if (hungPoint >= 10) {
                hungPoint = 10;
                myPet.setHungPoint(10);
                //myPet.setFeedTime(current);
            }
        }
        if ((bathgap >= 7200 || cleangap >= 7200) && illPoint < 10) {
            dirtyPoint = (int) (dirtyPoint + bathgap / 57600 + cleangap / 57600);
            if (dirtyPoint >= 10) {
                dirtyPoint = 10;
                myPet.setDirtyPoint(10);
               // myPet.setBathTime(current);
              //  myPet.setCleanTime(current);
            }
            myPet.setHungPoint(hungPoint);
            myPet.setDirtyPoint(dirtyPoint);
            illPoint = (dirtyPoint / 2 + hungPoint / 2);
            myPet.setIllPoint(illPoint);
            if (illPoint >= 10) {
                illPoint = 10;
                myPet.setIllPoint(10);
                isDied = true;
            }
            checkDied();
        }
        petView.setBackgroundResource(drawGif[imgNum]);
        createShit();
        updateState();
        timer = new Timer();
        setTimerTask();
    }

    private void updateState() {
        int illPoint = myPet.getIllPoint();
        int hungPoint = myPet.getHungPoint();
        int dirtyPoint = myPet.getDirtyPoint();

        for (int i = 0; i < 5; i++) {
            Drawable drawable1 = getResources().getDrawable(R.drawable.shit);
            drawable1.setBounds(0, 0, 100, 100);
            shitBtn[i].setCompoundDrawables(null, drawable1, null, null);
            shitBtn[i].setVisibility(View.INVISIBLE);
            shitBtn[i].setEnabled(false);
        }
        for (int i = 0; i < (dirtyPoint / 2); i++) {
            shitBtn[i].setVisibility(View.VISIBLE);
            shitBtn[i].setEnabled(true);
        }

        date = new Date();
        current = date.getTime() / 1000 - 1511450000;
        checkCollect();
        expTxt.setText("Exp:" + myPet.getExperience());
        if (isHidden) {
            foodLayOut.setVisibility(View.INVISIBLE);
            barLayOut.setVisibility(View.INVISIBLE);
        } else {
            foodLayOut.setVisibility(View.VISIBLE);
            barLayOut.setVisibility(View.VISIBLE);

        }
        feedgap = current - myPet.getFeedTime();
        bathgap = current - myPet.getBathTime();
        cleangap = current - myPet.getCleanTime();

        myPet.setIllPoint(dirtyPoint / 2 + hungPoint / 2);
        balanceTxt.setText(" £" + balance);
        levelText.setText("Lv." + myPet.getpLevel());
        hungBar.setProgress(10 - myPet.getHungPoint());
        bathBtn.setText("Dirty: " + dirtyPoint);
        if (illPoint >= 10 && !isDied) {
            illPoint = 10;
            Toast.makeText(getApplicationContext(), "Your pet is ill!",
                    Toast.LENGTH_SHORT).show();
            isDied = true;
            checkDied();
        }
    }

    private void changeImg(Pet myPet) {
        DataMethods.levelCheck(myPet);
        imgNum = makeRandom(myPet.getpSkill());
        petView.setBackgroundResource(drawGif[imgNum]);

    }

    private void checkDied() {
        if (isDied) {
            foods.setVisibility(View.GONE);
            shits.setVisibility(View.GONE);
            injecBtn.setVisibility(View.VISIBLE);
            imgNum = 9;
            petView.setBackgroundResource(drawGif[imgNum]);
        } else {
            injecBtn.setVisibility(View.GONE);

            foods.setVisibility(View.VISIBLE);
            shits.setVisibility(View.VISIBLE);
        }
    }

    private int makeRandom(int max) {
        int min = 0;
        int s = 0;
        Random random = new Random();
        if (max > 0)
            s = random.nextInt(max) % (max - min + 1) + min;
        return s;
    }

    private void createShit() {
        for (int i = 0; i < 5; i++) {
            Drawable drawable1 = getResources().getDrawable(R.drawable.shit);
            drawable1.setBounds(0, 0, 100, 100);
            shitBtn[i].setCompoundDrawables(null, drawable1, null, null);
            shitBtn[i].setVisibility(View.INVISIBLE);
            shitBtn[i].setEnabled(false);
        }
        for (int i = 0; i < (myPet.getDirtyPoint() / 2); i++) {
            shitBtn[i].setVisibility(View.VISIBLE);
            shitBtn[i].setEnabled(true);
        }
    }

    private void creatFood() {
        milk = new Food("Milk", 1, 5, 2, 1, 10);
        bread = new Food("Bread", 3, 10, 1, 2, 15);
        hotdog = new Food("Hotdog", 3, 10, 2, 3, 15);
        sandwich = new Food("Sandwich", 6, 20, 3, 4, 20);
        hamburger = new Food("Burger", 11, 30, 5, 7, 40);
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
            food3 = sandwich;
        } else if (myPet.getpLevel() >= 11 && myPet.getpLevel() <= 20) {
            food1 = sandwich;
            food2 = hamburger;
            food3 = chicken;
        } else if (myPet.getpLevel() >= 21 && myPet.getpLevel() <= 30) {
            food1 = hamburger;
            food2 = chicken;
            food3 = steak;
        }
        foodBtn[0].setText(food1.getFoodName());

        foodBtn[1].setText(food2.getFoodName());

        foodBtn[2].setText(food3.getFoodName());
        setFoodImg(food1, foodBtn[0]);
        setFoodImg(food2, foodBtn[1]);
        setFoodImg(food3, foodBtn[2]);
    }

    private void checkCollect() {
        if (current - collectTime >= 7200) {
            collectButton.setCompoundDrawables(null, moneyD, null, null);
            collectButton.setText("Collect");
        } else {
            collectButton.setCompoundDrawables(null, moneyD1, null, null);
            String mm, ss;
            int gap = (int) (7200 - (current - collectTime));
            int shi = gap / 3600;
            int fen = (gap % 3600) / 60;
            int miao = gap % 60;
            if (fen < 10)
                mm = "0" + fen;
            else
                mm = "" + fen;
            if (miao < 10)
                ss = "0" + miao;
            else
                ss = "" + miao;
            collectButton.setText("" + shi + ":" + mm + ":" + ss);
        }
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
        } else if (myPet.getHungPoint() <= 0)
            Toast.makeText(getApplicationContext(), "I'm not hungery now!",
                    Toast.LENGTH_SHORT).show();
    }

    private void setFoodImg(Food thisFood, Button btn) {
        btn.setCompoundDrawablePadding(0);
        btn.setPadding(0, 0, 0, 0);
        if (thisFood.getFoodName().equals("Milk")) {
            Drawable drawable1 = getResources().getDrawable(R.drawable.milk);
            drawable1.setBounds(0, 0, 100, 100);
            btn.setCompoundDrawables(null, drawable1, null, null);
        } else if (thisFood.getFoodName().equals("Bread")) {
            Drawable drawable1 = getResources().getDrawable(R.drawable.bread);
            drawable1.setBounds(0, 0, 100, 100);
            btn.setCompoundDrawables(null, drawable1, null, null);
        } else if (thisFood.getFoodName().equals("Hotdog")) {
            Drawable drawable1 = getResources().getDrawable(R.drawable.hotdog);
            drawable1.setBounds(0, 0, 100, 100);
            btn.setCompoundDrawables(null, drawable1, null, null);
        } else if (thisFood.getFoodName().equals("Sandwich")) {
            Drawable drawable1 = getResources().getDrawable(R.drawable.sandwich);
            drawable1.setBounds(0, 0, 100, 100);
            btn.setCompoundDrawables(null, drawable1, null, null);
        } else if (thisFood.getFoodName().equals("Burger")) {
            Drawable drawable1 = getResources().getDrawable(R.drawable.hamburger);
            drawable1.setBounds(0, 0, 100, 100);
            btn.setCompoundDrawables(null, drawable1, null, null);
        } else if (thisFood.getFoodName().equals("Chicken")) {
            Drawable drawable1 = getResources().getDrawable(R.drawable.chicken);
            drawable1.setBounds(0, 0, 100, 100);
            btn.setCompoundDrawables(null, drawable1, null, null);
        } else if (thisFood.getFoodName().equals("Steak")) {
            Drawable drawable1 = getResources().getDrawable(R.drawable.steak);
            drawable1.setBounds(0, 0, 100, 100);
            btn.setCompoundDrawables(null, drawable1, null, null);

        } else {
            Drawable drawable1 = getResources().getDrawable(R.drawable.no);
            drawable1.setBounds(0, 0, 100, 100);
            btn.setCompoundDrawables(null, drawable1, null, null);
        }
    }

    private void saveDataLocal() {
        SharedPreferences.Editor editor = petData.edit();
        editor.putInt("uID", myPet.getuID());
        editor.putString("pName", myPet.getpName());
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
    }

    public void handleClicked(View v) {
        switch (v.getId()) {
            case R.id.bathButton:
                updateState();
                if (balance > 0) {
                    if (myPet.getDirtyPoint() > 0) {
                        DataMethods.bathMethod(myPet);
                        date = new Date();
                        now = date.getTime() / 1000 - 1511450000;
                        balance = balance - 10;
                        myPet.setBathTime(now);
                        setFood();
                        updateState();
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "I'm very clean now!", Toast.LENGTH_SHORT).show();
                    }

                }
                expTxt.setText("Exp:" + myPet.getExperience());
                levelText.setText("Lv." + myPet.getpLevel());
                balanceTxt.setText(" £" + balance);
                break;

            case R.id.collectBtn:
                updateState();
                date = new Date();
                long clicked = date.getTime() / 1000 - 1511450000;
                if (clicked - collectTime >= 7200) {
                    checkCollect();
                    collectTime = clicked;
                    balance = balance + 200;
                    SharedPreferences.Editor editor1 = petData.edit();
                    editor1.putLong("collectTime", collectTime);
                    updateState();
                }
                break;
            case R.id.injectionBtn:
                updateState();
                if (balance > 0 && myPet.getIllPoint() > 0) {
                    myPet.setBathTime(now);
                    myPet.setCleanTime(now);
                    myPet.setFeedTime(now);
                    myPet.setHungPoint(1);
                    myPet.setDirtyPoint(2);
                    myPet.setIllPoint(0);
                    balance = balance - 100;
                    isDied = false;
                    checkDied();
                    changeImg(myPet);
                    updateState();
                }
                balanceTxt.setText(" £" + balance);
                break;
            case R.id.attBtn:
                updateState();
                saveDataLocal();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                break;
            case R.id.outBtn:
                updateState();
                SharedPreferences.Editor editor = userData.edit();
                editor.clear();
                editor.commit();
                SharedPreferences.Editor editor1 = petData.edit();
                editor1.clear();
                editor1.commit();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                Toast.makeText(getApplicationContext(), "Log out Success!", Toast.LENGTH_SHORT).show();
                finish();
            case R.id.shit1:
            case R.id.shit2:
            case R.id.shit3:
            case R.id.shit4:
            case R.id.shit5:
                updateState();
                if (balance > 0) {
                    if (myPet.getDirtyPoint() > 0) {
                        DataMethods.cleanMethod(myPet);
                        date = new Date();
                        now = date.getTime() / 1000 - 1511450000;
                        balance = balance - 10;
                        myPet.setCleanTime(now);
                        createShit();
                        updateState();
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "I'm very clean now!", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.foodbtn1:
                updateState();
                if (balance > 0) {
                    feedFunction(food1);
                    updateState();
                }
                expTxt.setText("Exp:" + myPet.getExperience());
                levelText.setText("Lv." + myPet.getpLevel());
                balanceTxt.setText(" £" + balance);
                hungBar.setProgress(10 - myPet.getHungPoint());
                setFood();
                changeImg(myPet);
                updateState();
                break;
            case R.id.foodbtn2:
                updateState();
                if (balance > 0) {
                    feedFunction(food2);
                    updateState();
                }
                expTxt.setText("Exp:" + myPet.getExperience());
                levelText.setText("Lv." + myPet.getpLevel());
                balanceTxt.setText(" £" + balance);
                setFood();
                changeImg(myPet);
                updateState();
                break;
            case R.id.foodbtn3:
                updateState();
                if (balance > 0) {
                    feedFunction(food3);
                    updateState();
                }
                expTxt.setText("Exp:" + myPet.getExperience());
                levelText.setText("Lv." + myPet.getpLevel());
                balanceTxt.setText(" £" + balance);
                setFood();
                changeImg(myPet);
                updateState();
                break;

        }
    }

    private void checkPhoneID() {

        HttpURLConnection conn;
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            String url1 = "http://www.moecity.cn/Service1.asmx/FindUserByPhone?searchstring=" + phone;
            URL u = new URL(url1);
            conn = (HttpURLConnection) u.openConnection();
            conn.connect();


            int recode = conn.getResponseCode();
            if (recode == 200) {
                InputStream in = conn.getInputStream();
                str = "";
                int n;
                byte[] buffer = new byte[512];
                while ((n = in.read(buffer)) > 0) {
                    str += new String(buffer, 0, n);
                }
                try {
                    strs = str.split("phoneID>");
                    strs = strs[1].split("</");
                    phoneID = strs[0];
                } catch (Exception e) {
                    // TODO: handle exception
                }

            }

        } catch (MalformedURLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        updateState();
        saveDataLocal();
        if (isSafe) {
            Thread th = new Thread() {
                @Override
                public void run() {

                    HttpURLConnection conn;
                    try {
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);
                        String url1 = "http://www.moecity.cn/Service1.asmx/UpdatePet?uID=" + myPet.getuID() + "&pl=" + myPet.getpLevel() +
                                "&pHP=" + myPet.getHungPoint() + "&pDP=" + myPet.getDirtyPoint() + "&pB=" + balance + "&exp=" + myPet.getExperience() +
                                "&pCt=" + myPet.getCleanTime() + "&pFt=" + myPet.getFeedTime() + "&pBt=" + myPet.getBathTime() + "&pCot=" + collectTime;
                        URL u = new URL(url1);
                        conn = (HttpURLConnection) u.openConnection();
                        conn.connect();
                        int recode0 = conn.getResponseCode();
                        if (recode0 == 200) {
                            InputStream in0 = conn.getInputStream();
                            int n0;
                            byte[] buffer0 = new byte[512];
                            while ((n0 = in0.read(buffer0)) > 0)
                                Log.e("new", "new");
                        }
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            };
            th.run();

        }
        timer.cancel();
        timer = null;
    }

    private class MyAsyncTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            checkPhoneID();
            return true;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            if (!(phoneID.equals(localPhoneID))) {
                isSafe = false;
                SharedPreferences.Editor editor = userData.edit();
                editor.clear();
                editor.commit();
                SharedPreferences.Editor editor1 = petData.edit();
                editor1.clear();
                editor1.commit();
                new AlertDialog.Builder(CoreActivity.this)

                        .setTitle("Error")
                        .setMessage("You have changed your phone,\nplease login again!")
                        .setOnCancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                Intent intent = new Intent(CoreActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setPositiveButton("Login",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialoginterface, int i) {
                                        Intent intent = new Intent(CoreActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }).show();
            }
        }
    }
}

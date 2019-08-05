package cn.moecity.virtualpet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.provider.Settings.Secure;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginActivity extends Activity implements OnClickListener {
    private User s = new User();
    private Pet p = new Pet();
    private Button logBtn, newBtn;
    private EditText phoneEd, passEd;
    private TextView errorText;
    private String[] strs;
    private String passGet, pass, phone, id, name, str, pname, pLevel, pDp, pHp, pbalance;
    private SharedPreferences userData, petData;
    private String userName, phoneID;
    private int balance;
    private boolean isIn = false;
    private String pexp;
    private String pcleanTime, pbathTime, pfeedTime, pcollectTime;
    private Long collectTime, feedTime;
    private LinearLayout waitlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String android_id = Secure.getString(this.getApplicationContext().getContentResolver(),
                Secure.ANDROID_ID);
        phoneID = android_id;
        logBtn = (Button) findViewById(R.id.login_btn);
        newBtn = (Button) findViewById(R.id.new_btn);
        phoneEd = (EditText) findViewById(R.id.phoneText);
        passEd = (EditText) findViewById(R.id.passText);
        errorText = (TextView) findViewById(R.id.errorView);
        waitlayout = (LinearLayout) findViewById(R.id.waitlayout);
        logBtn.setOnClickListener(this);
        newBtn.setOnClickListener(this);
        userData = getSharedPreferences("myUser", MODE_PRIVATE);
        petData = getSharedPreferences("myPet", MODE_PRIVATE);
        userName = userData.getString("userName", "NoName");
        isIn = userData.getBoolean("isIn", false);
        if (isIn) {
            Toast.makeText(getBaseContext(), "Welcome, " + userName,
                    Toast.LENGTH_LONG).show();
            Intent intent = new Intent(LoginActivity.this, CoreActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        switch (arg0.getId()) {
            case R.id.login_btn:
                phone = phoneEd.getText().toString();
                pass = passEd.getText().toString();
                str = "";
                phone = phoneEd.getText().toString();
                pass = passEd.getText().toString();
                if (phone.equals("")) {
                    errorText.setText("Your phone number is empty!");
                } else if (pass.equals("")) {
                    errorText.setText("Your password is empty!");
                } else {
                    errorText.setText("");
                    waitlayout.setVisibility(View.VISIBLE);
                    logBtn.setVisibility(View.GONE);
                    passEd.setEnabled(false);
                    phoneEd.setEnabled(false);
                    MyAsyncTask myAsyncTask = new MyAsyncTask();
                    myAsyncTask.execute(5000);
                }
                break;
            case R.id.new_btn:
                Intent intent = new Intent(LoginActivity.this, NewUserActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }

    }

    public void checkTheUser() {
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
                    strs = str.split("uPass>");
                    strs = strs[1].split("</");
                    passGet = strs[0];
                    s.setuPass(pass);

                    strs = str.split("uID>");
                    strs = strs[1].split("</");
                    id = strs[0];
                    s.setuID(Integer.parseInt(id));

                    strs = str.split("uname>");
                    strs = strs[1].split("</");
                    name = strs[0];
                    s.setuName(name);

                    s.setuPhone(phone);
                    s.setPhoneID(phoneID);

                } catch (Exception e) {
                    // TODO: handle exception
                }
                policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                String url2 = "http://www.moecity.cn/Service1.asmx/FindPet?searchstring=" + s.getuID();
                URL u2 = new URL(url2);
                conn = (HttpURLConnection) u2.openConnection();
                conn.connect();
                recode = conn.getResponseCode();
                if (recode == 200) {
                    in = conn.getInputStream();
                    str = "";
                    buffer = new byte[512];
                    while ((n = in.read(buffer)) > 0) {
                        str += new String(buffer, 0, n);
                    }
                    try {
                        strs = str.split("pName>");
                        strs = strs[1].split("</");
                        pname = strs[0];
                        p.setpName(pname);

                        strs = str.split("pLevel>");
                        strs = strs[1].split("</");
                        pLevel = strs[0];
                        p.setpLevel(Integer.parseInt(pLevel));

                        strs = str.split("pDirtyPoint>");
                        strs = strs[1].split("</");
                        pDp = strs[0];
                        p.setDirtyPoint(Integer.parseInt(pDp));

                        strs = str.split("pHungerPoint>");
                        strs = strs[1].split("</");
                        pHp = strs[0];
                        p.setHungPoint(Integer.parseInt(pHp));

                        p.setuID(s.getuID());

                        strs = str.split("balance>");
                        strs = strs[1].split("</");
                        pbalance = strs[0];
                        balance = Integer.parseInt(pbalance);

                        strs = str.split("exp>");
                        strs = strs[1].split("</");
                        pexp = strs[0];
                        p.setExperience(Integer.parseInt(pexp));

                        strs = str.split("cleanTime>");
                        strs = strs[1].split("</");
                        pcleanTime = strs[0];
                        p.setCleanTime(Long.parseLong(pcleanTime));

                        strs = str.split("feedTime>");
                        strs = strs[1].split("</");
                        pfeedTime = strs[0];
                        p.setFeedTime(Long.parseLong(pfeedTime));
                        feedTime = Long.parseLong(pfeedTime);
                        strs = str.split("bathTime>");
                        strs = strs[1].split("</");
                        pbathTime = strs[0];
                        p.setBathTime(Long.parseLong(pbathTime));

                        strs = str.split("collectTime>");
                        strs = strs[1].split("</");
                        pcollectTime = strs[0];
                        collectTime = Long.parseLong(pcollectTime);

                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                    policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    url2 = "http://www.moecity.cn/Service1.asmx/UpdateUserPhoneID?uID=" + s.getuID() + "&newPhoneID=" + phoneID;
                    u2 = new URL(url2);
                    conn = (HttpURLConnection) u2.openConnection();
                    conn.connect();

                    recode = conn.getResponseCode();
                    if (recode == 200) {
                        in = conn.getInputStream();
                        str = "";
                        buffer = new byte[512];
                        while ((n = in.read(buffer)) > 0) {
                            str += new String(buffer, 0, n);
                        }

                    }
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

    private class MyAsyncTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            checkTheUser();
            return true;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            if (MD5.generatePassword(pass).equals(passGet)) {
                Editor editor = userData.edit();
                editor.putString("userName", name);
                editor.putString("phone", phone);
                editor.putBoolean("isIn", true);
                editor.putInt("userID", s.getuID());
                editor.putBoolean("isNew", false);
                editor.putString("phoneID", phoneID);
                editor.commit();
                Editor editor2 = petData.edit();
                editor2.putInt("uID", p.getuID());
                editor2.putString("pName", p.getpName());
                editor2.putInt("pLevel", p.getpLevel());
                editor2.putInt("dirtyPoint", p.getDirtyPoint());
                editor2.putInt("hungPoint", p.getHungPoint());
                editor2.putInt("balance", balance);
                editor2.putInt("experience", p.getExperience());
                editor2.putLong("bathTime", p.getBathTime());
                editor2.putLong("cleanTime", p.getCleanTime());
                editor2.putLong("feedTime", p.getFeedTime());
                editor2.putLong("collectTime", collectTime);
                editor2.commit();
                Toast.makeText(getBaseContext(), "Welcome, " + name,
                        Toast.LENGTH_LONG).show();

                Intent intent = new Intent(LoginActivity.this, CoreActivity.class);
                intent.putExtra("newLogin", true);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Error!",
                        Toast.LENGTH_SHORT).show();
                logBtn.setVisibility(View.VISIBLE);
                waitlayout.setVisibility(View.GONE);
                passEd.setEnabled(true);
                phoneEd.setEnabled(true);
                errorText.setText("Double check and try again!");
            }
        }
    }

}

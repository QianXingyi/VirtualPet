package cn.moecity.virtualpet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NewPetActivity extends Activity {
    private Button nextBtn,backBtn;
    private EditText petNameText;
    private String pName, phone;
    private int uID;
    private String[] strs;
    private String id, str, userName;
    private SharedPreferences petData, userData;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newpet);
        context = this;
        petData = getSharedPreferences("myPet", MODE_PRIVATE);
        userData = getSharedPreferences("myUser", MODE_PRIVATE);
        phone = userData.getString("phone", "NoPhone");
        nextBtn = (Button) findViewById(R.id.nextBtn);
        petNameText = (EditText) findViewById(R.id.petNameText);
        nextBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                pName = petNameText.getText().toString();
                final SharedPreferences.Editor editor = petData.edit();
                editor.putInt("uID", uID);
                editor.putString("pName",pName);
                editor.putInt("illPoint", 0);
                editor.putInt("dirtyPoint", 1);
                editor.putInt("hungPoint", 1);
                editor.putInt("pLevel", 1);
                editor.putInt("experience", 0);
                editor.putInt("balance", 1000);
                editor.commit();
                Thread th1 = new Thread() {
                    @Override
                    public void run() {
                        HttpURLConnection conn;
                        try {
                            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                            StrictMode.setThreadPolicy(policy);
                            String url = "http://www.moecity.cn/Service1.asmx/FindUserByPhone?searchstring=" + phone;
                            URL u = new URL(url);
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


                                    strs = str.split("uID>");
                                    strs = strs[1].split("</");
                                    id = strs[0];
                                    uID = Integer.parseInt(id);
                                    strs = str.split("uname>");
                                    strs = strs[1].split("</");
                                    userName = strs[0];

                                    try {
                                        StrictMode.setThreadPolicy(policy);
                                        url = "http://www.moecity.cn/Service1.asmx/InsertPetDetails?uID=" + uID + "&pName=" + pName +
                                                "&pL=1&pHP=1&pDP=1&pB=1000&exp=0&pCt=0&pFt=0&pBt=0&pCot=0";
                                        u = new URL(url);
                                        conn = (HttpURLConnection) u.openConnection();
                                        conn.connect();
                                        int recode0 = conn.getResponseCode();
                                        if (recode0 == 200) {
                                            InputStream in0 = conn.getInputStream();
                                            int n0;
                                            byte[] buffer0 = new byte[512];
                                            while ((n0 = in0.read(buffer0)) > 0)
                                                Log.e("new", "new");
                                            Toast.makeText(getBaseContext(), "Welcome, " + userName,
                                                    Toast.LENGTH_SHORT).show();
                                            SharedPreferences.Editor editor1 = userData.edit();
                                            editor1.putBoolean("isNew",true);
                                            editor1.putInt("userID", uID);
                                            editor1.commit();
                                            SharedPreferences.Editor editor2 = petData.edit();
                                            editor2.putInt("uID", uID);
                                            editor2.commit();
                                            Intent intent = new Intent(context, CoreActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    } catch (MalformedURLException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

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
                };
                th1.run();
            }
        });
    }
    public void onClick1(View arg0) {
        // TODO Auto-generated method stub
        switch (arg0.getId()) {
            case R.id.backLogBtn1:
                Intent intent = new Intent(NewPetActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}

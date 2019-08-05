package cn.moecity.virtualpet;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.os.StrictMode;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class NewUserActivity extends Activity implements OnClickListener {
    private EditText nameText, phoneText, passText, passText2;
    private Button createBtn, backBtn;
    private String name, pass1, pass2, phone, str,android_id,passGet,id;
    private User s = new User();
    private Context context;
    private SharedPreferences userData,petData;
    private LinearLayout waitLayout;
    private String [] strs;
    private Pet p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        userData = getSharedPreferences("myUser", MODE_PRIVATE);
        petData = getSharedPreferences("myPet", MODE_PRIVATE);

        setContentView(R.layout.activity_newuser);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        context = this;
        findById();
    }

    private void findById() {
        // TODO Auto-generated method stub
        nameText = (EditText) findViewById(R.id.editTextUserName);
        phoneText = (EditText) findViewById(R.id.editTextUserPhone);
        passText = (EditText) findViewById(R.id.editTextUserPass);
        passText2 = (EditText) findViewById(R.id.editTextUserPass2);
        createBtn = (Button) findViewById(R.id.createBtn);
        backBtn = (Button) findViewById(R.id.backLogBtn);
        waitLayout=(LinearLayout) findViewById(R.id.wait1layout);
        createBtn.setOnClickListener(this);
        backBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        switch (arg0.getId()) {
            case R.id.backLogBtn:
                Intent intent = new Intent(NewUserActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.createBtn:
                name = nameText.getText().toString();
                pass1 = passText.getText().toString();
                pass2 = passText2.getText().toString();
                phone = phoneText.getText().toString();
                MyAsyncTask myAsyncTask = new MyAsyncTask();
                nameText.setEnabled(false);
                phoneText.setEnabled(false);
                passText.setEnabled(false);
                passText2.setEnabled(false);
                createBtn.setVisibility(View.GONE);
                waitLayout.setVisibility(View.VISIBLE);
                myAsyncTask.execute(5000);
                break;
            default:
                break;
        }
    }

    private void backRoll(){
        nameText.setEnabled(true);
        phoneText.setEnabled(true);
        passText.setEnabled(true);
        passText2.setEnabled(true);
        waitLayout.setVisibility(View.GONE);
        createBtn.setVisibility(View.VISIBLE);
    }

    private void createUser(){
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
                if (str.contains("<uPhone>")) {
                    Toast.makeText(NewUserActivity.this, "This Phone Number has been used!",
                            Toast.LENGTH_SHORT).show();
                    backRoll();
                } else {
                    if (pass1.equals(pass2)) {
                        StrictMode.setThreadPolicy(policy);
                        android_id = Settings.Secure.getString(this.getApplicationContext().getContentResolver(),
                                Settings.Secure.ANDROID_ID);
                        url = "http://www.moecity.cn/Service1.asmx/InsertUserDetails?uname=" + name + "&uPass=0" + "&uPhone=" + phone+"&phoneID="+android_id;
                        u = new URL(url);
                        conn = (HttpURLConnection) u.openConnection();
                        conn.connect();
                        recode = conn.getResponseCode();
                        if (recode == 200) {
                            in = conn.getInputStream();

                            buffer = new byte[512];
                            while ((n = in.read(buffer)) > 0)
                                Log.e("new", "new");
                            StrictMode.setThreadPolicy(policy);
                            url = "http://www.moecity.cn/Service1.asmx/UpdateUserPassByPhone?uPhone=" + phone + "&newPass=" + MD5.generatePassword(pass1);
                            u = new URL(url);
                            conn = (HttpURLConnection) u.openConnection();
                            conn.connect();
                            recode = conn.getResponseCode();
                            if (recode == 200) {
                                in = conn.getInputStream();
                                buffer = new byte[512];
                                while ((n = in.read(buffer)) > 0)
                                    Log.e("new", "new");
                                StrictMode.setThreadPolicy(policy);
                                SharedPreferences.Editor editor = userData.edit();
                                editor.putString("userName", name);
                                editor.putString("phone", phone);
                                editor.putBoolean("isIn", true);
                                editor.putString("phoneID", android_id);
                                editor.commit();
                                Intent intent = new Intent(context, NewPetActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }

                    } else {
                        Toast.makeText(NewUserActivity.this, "Make sure your password!",
                                Toast.LENGTH_SHORT).show();
                        backRoll();
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
            Looper.prepare();
            createUser();
            Looper.loop();
            return true;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            backRoll();

        }
    }
}

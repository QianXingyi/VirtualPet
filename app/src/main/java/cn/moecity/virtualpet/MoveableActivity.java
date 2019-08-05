package cn.moecity.virtualpet;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Random;

import pl.droidsonroids.gif.GifImageView;

public class MoveableActivity extends Activity {
    private ViewGroup rootView;
    private LinearLayout layout;
    private Button backButton,dieButton,logoutButton;
    private GifImageView imageView;
    private int _xDelta;
    private int _yDelta;
    private  int[] drawGif;
    private SharedPreferences userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moveable);
        userData = getSharedPreferences("myUser", MODE_PRIVATE);
        drawGif= new int[]{R.drawable.die, R.drawable.angry,R.drawable.eating,R.drawable.happy,R.drawable.kiss,R.drawable.shy,
        R.drawable.walk};
        dieButton=(Button) findViewById(R.id.dieButton);
        imageView=(GifImageView) findViewById(R.id.imageView1);
        dieButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				imageView.setBackgroundResource(drawGif[makeRandom()]);
				
			}
		});
        backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),
                        MainActivity.class));
                finish();
            }
        });
        logoutButton=(Button) findViewById(R.id.outbutton);
        logoutButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Editor editor=userData.edit();
                editor.putBoolean("isIn",false);
                editor.putString("userName","NoName");
                editor.commit();
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                Toast.makeText(getApplicationContext(),"Log out Success!",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        rootView = (ViewGroup) findViewById(R.id.RelativeLayout1);
        layout = (LinearLayout) findViewById(R.id.linearLayout1);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                150, 50);
        layoutParams.leftMargin = 50;
        layoutParams.topMargin = 50;
        layoutParams.bottomMargin = -250;
        layoutParams.rightMargin = -250;
        layoutParams.width = 400;
        layoutParams.height = 400;
        layout.setLayoutParams(layoutParams);
        layout.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int X = (int) event.getRawX();
                final int Y = (int) event.getRawY();
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        imageView.setBackgroundResource(drawGif[makeRandom()]);
                        RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) v
                                .getLayoutParams();
                        _xDelta = X - lParams.leftMargin;
                        _yDelta = Y - lParams.topMargin;
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) v
                                .getLayoutParams();
                        layoutParams.leftMargin = X - _xDelta;
                        layoutParams.topMargin = Y - _yDelta;
                        layoutParams.rightMargin = -250;
                        layoutParams.bottomMargin = -250;
                        v.setLayoutParams(layoutParams);
                        break;
                }
                rootView.invalidate();
                return true;
            }
        });

    }
    private int makeRandom()
    {
        int max=6;
        int min=0;
        Random random = new Random();
        int s = random.nextInt(max)%(max-min+1) + min;
        return s;
    }
}

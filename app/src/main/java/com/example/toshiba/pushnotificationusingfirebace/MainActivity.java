package com.example.toshiba.pushnotificationusingfirebace;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.toshiba.pushnotificationusingfirebace.Apater.PagerViewAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView profile;
    private TextView allUser;
    private TextView notifiactio;
    private ViewPager viewPager;
    private FirebaseAuth mAuth;

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = mAuth.getCurrentUser();
        if(user == null){
           logIn();
        }
    }

    private void logIn() {
        Intent logInActivity = new Intent(MainActivity.this,activity_logIn.class);
        startActivity(logInActivity);
        finish();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        profile = (TextView)findViewById(R.id.profile);
        allUser = (TextView)findViewById(R.id.users);
        notifiactio = (TextView)findViewById(R.id.notification);

        profile.setOnClickListener(this);
        allUser.setOnClickListener(this);
        notifiactio.setOnClickListener(this);

        viewPager = (ViewPager)findViewById(R.id.mainPage);

        PagerAdapter pagerAdapter = new PagerViewAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        // TO Make it Default
       // viewPager.setOffscreenPageLimit(2);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onPageSelected(int position) {
                    ChangeLable(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void ChangeLable(int position) {
        if(position==0){
            profile.setTextSize(22);
            profile.setTextColor(getColor(R.color.textLableBright));
            notifiactio.setTextSize(16);
            notifiactio.setTextColor(getColor(R.color.textLableLight));
            allUser.setTextSize(16);
            allUser.setTextColor(getColor(R.color.textLableLight));
        }else  if (position==1){
            profile.setTextSize(16);
            profile.setTextColor(getColor(R.color.textLableLight));
            notifiactio.setTextSize(16);
            notifiactio.setTextColor(getColor(R.color.textLableLight));
            allUser.setTextSize(22);
            allUser.setTextColor(getColor(R.color.textLableBright));
        }else {
            profile.setTextSize(16);
            profile.setTextColor(getColor(R.color.textLableLight));
            notifiactio.setTextSize(22);
            notifiactio.setTextColor(getColor(R.color.textLableBright));
            allUser.setTextSize(16);
            allUser.setTextColor(getColor(R.color.textLableLight));
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.profile){
        viewPager.setCurrentItem(0);
        }else if (view.getId()==R.id.users){
            viewPager.setCurrentItem(1);
        }else if(view.getId()==R.id.notification){
            viewPager.setCurrentItem(2);
        }
    }
}

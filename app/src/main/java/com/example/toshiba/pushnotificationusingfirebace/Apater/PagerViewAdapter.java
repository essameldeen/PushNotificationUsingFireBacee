package com.example.toshiba.pushnotificationusingfirebace.Apater;

import android.app.Notification;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.toshiba.pushnotificationusingfirebace.Fragment.AllUser;
import com.example.toshiba.pushnotificationusingfirebace.Fragment.Notifiaction;
import com.example.toshiba.pushnotificationusingfirebace.Fragment.Profile;

public class PagerViewAdapter extends FragmentPagerAdapter {
    public PagerViewAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
             if (position==0){
                 Profile profile = new Profile();
                 return  profile;
             }else if(position==1){
                 AllUser users = new AllUser();
                 return  users;
             }else {
                 Notifiaction notification = new Notifiaction();
                 return notification;
             }
    }

    @Override
    public int getCount() {
        return 3;
    }
}

package com.hbyadav.myapplication;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.android.tourguide.R;

public class SimpleFragmentAdapter extends FragmentPagerAdapter {
    private Context mContext;

    public SimpleFragmentAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new Home_fragment();
        } else if (position == 1)
            return new schedule_fragment();
        else {
            return new Attendence_fragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return mContext.getString(R.string.Student_Home);
        } else if (position == 1) {
            return mContext.getString(R.string.Student_schedule);
        } else
            return mContext.getString(R.string.Student_Attendence);
    }
}

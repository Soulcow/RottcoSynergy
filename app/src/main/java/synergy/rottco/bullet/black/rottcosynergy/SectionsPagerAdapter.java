package synergy.rottco.bullet.black.rottcosynergy;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.annotation.Nullable;
import android.support.v13.app.FragmentPagerAdapter;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by boghi on 1/8/2018.
 */

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(Fragment fragment,String title)
    {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
       return mFragmentList.size();
    }
}

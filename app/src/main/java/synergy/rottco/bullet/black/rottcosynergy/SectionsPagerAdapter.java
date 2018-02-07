package synergy.rottco.bullet.black.rottcosynergy;


import android.app.Fragment;
import android.app.FragmentManager;
import android.support.annotation.Nullable;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v13.app.FragmentStatePagerAdapter;


import java.util.ArrayList;
import java.util.List;

public class SectionsPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public SectionsPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Tab1Fragment tab1 = new Tab1Fragment();
                return tab1;
            case 1:
                Tab2Fragment tab2 = new Tab2Fragment();
                return tab2;
            case 2:
                Tab3Fragment tab3 = new Tab3Fragment();
                return tab3;
            case 3:
                Tab4Fragment tab4 = new Tab4Fragment();
                return tab4;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}

//public class SectionsPagerAdapter extends FragmentPagerAdapter {
//
//    private final List<Fragment> mFragmentList = new ArrayList<>();
//    private final List<String> mFragmentTitleList = new ArrayList<>();
//
//    public SectionsPagerAdapter(FragmentManager fm) {
//        super(fm);
//    }
//
//    public void addFragment(Fragment fragment,String title)
//    {
//        mFragmentList.add(fragment);
//        mFragmentTitleList.add(title);
//    }
//    @Nullable
//    @Override
//    public CharSequence getPageTitle(int position) {
//        return mFragmentTitleList.get(position);
//    }
//
//    @Override
//    public Fragment getItem(int position) {
//
////        return mFragmentList.get(position);
//        switch (position) {
//            case 0:
//                Tab1Fragment tab1 = new Tab1Fragment();
//                return tab1;
//            case 1:
//                Tab2Fragment tab2 = new Tab2Fragment();
//                return tab2;
//            case 2:
//                Tab3Fragment tab3 = new Tab3Fragment();
//                return tab3;
//            case 3:
//                Tab4Fragment tab4 = new Tab4Fragment();
//                return tab4;
//            default:
//                return null;
//        }
//    }
//
//    @Override
//    public int getCount() {
//       return mFragmentList.size();
//    }
//}

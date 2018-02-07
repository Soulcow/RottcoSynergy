package synergy.rottco.bullet.black.rottcosynergy;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Tab1Fragment extends Fragment {
    public static String TAG = Tab1Fragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab1_fragment,container,false);
        return view;
    }
}

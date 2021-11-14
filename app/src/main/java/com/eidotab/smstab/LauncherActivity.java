package com.eidotab.smstab;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.WindowManager;

import com.eidotab.smstab.Fragments.EstadisticasFragment;
import com.eidotab.smstab.Fragments.MeserosFragment;
import com.eidotab.smstab.Fragments.PlatoflashFragment;
import com.eidotab.smstab.Fragments.ReportsFragment;
import com.eidotab.smstab.Fragments.TiemporealFragment;
import com.eidotab.smstab.Model.ScrollableViewPager;

import java.util.ArrayList;
import java.util.Objects;

public class LauncherActivity extends FragmentActivity {


    TabLayout tabs;

    ScrollableViewPager pager;

    ArrayList<Fragment>list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);


        list = new ArrayList<>();
        tabs  = findViewById(R.id.tabs);
        pager = findViewById(R.id.viewPager);


        for (int i = 0; i <5; i++)
        {
            switch (i)
            {
                case 0:

                    list.add(TiemporealFragment.newInstance());

                    break;

                case 1:


                   list.add(MeserosFragment.newInstance());

                    break;

                case 2:

                    list.add(PlatoflashFragment.newInstance());

                    break;

                case 3:


                    list.add(ReportsFragment.newInstance());

                    break;

                case 4:

                    list.add(EstadisticasFragment.newInstance());

                    break;

            }

        }


        pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager())
        {

            @Override
            public Fragment getItem(int i) {

                return list.get(i);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });

        pager.setOffscreenPageLimit(5);
        pager.setCanScroll(false);

        tabs.setTabMode(TabLayout.MODE_FIXED);
        tabs.setupWithViewPager(pager);
        tabs.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.bgn));
        tabs.setTabGravity(TabLayout.GRAVITY_FILL);


        for (int i = 0; i < list.size(); i++)
        {
            switch (i)
            {
                case 0 :

                    Objects.requireNonNull(tabs.getTabAt(i)).setIcon(R.drawable.home);

                    break;

                case 1 :

                    Objects.requireNonNull(tabs.getTabAt(i)).setIcon(R.drawable.people);

                    break;


                case 2 :

                    Objects.requireNonNull(tabs.getTabAt(i)).setIcon(R.drawable.docs);

                    break;


                case 3 :

                    Objects.requireNonNull(tabs.getTabAt(i)).setIcon(R.drawable.chart);

                    break;


                case 4 :

                    Objects.requireNonNull(tabs.getTabAt(i)).setIcon(R.drawable.settings);

                    break;
            }

        }

        setupMainWindowDisplayMode();


    }


/*    private void showmessage(String mensaje)
    {
        Toast toast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }*/

    private View setSystemUiVisibilityMode()
    {
        View decorView = getWindow().getDecorView();
        int options;
        options =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        decorView.setSystemUiVisibility(options);
        return decorView;
    }

    private void setupMainWindowDisplayMode()
    {
        View decorView = setSystemUiVisibilityMode();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                setSystemUiVisibilityMode(); // Needed to avoid exiting immersive_sticky when keyboard is displayed
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);

        setupMainWindowDisplayMode();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);

        if(hasFocus)
        {
            setupMainWindowDisplayMode();
        }

    }

}


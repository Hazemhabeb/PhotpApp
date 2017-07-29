package com.hazem.photpapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.IntentCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hazem.photpapp.R;
import com.hazem.photpapp.adapter.ViewPagerAdapter;
import com.hazem.photpapp.fragment.dummyFragment;
import com.hazem.photpapp.fragment.profileFragment;

public class MainActivity extends AppCompatActivity {

    //inti the views
    private TabLayout tabLayout;
    private ViewPager viewPager;

    //the icons of tablayout  icon white  don't selected
    private int[] tabIcons = {
            R.drawable.home_unselected,
            R.drawable.rate_unselected,
            R.drawable.account_gray
    };
    // icon of tab layout selected blue icons
    private int[] tabIconsSelected = {
            R.drawable.home_selected,
            R.drawable.rate_selected,
            R.drawable.account
    };



    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In activity
            Intent i = new Intent(MainActivity.this, SignIn.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            return;
        }

        intiViews();
    }

    /**
     * here to intialize  the view from the xml
     */
    private void intiViews() {
        // inti the viewPager and set up it
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        //inti the tab layout and it's icons
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }

    /**
     * set up the view pager fragment
     *
     * @param viewPager refer to the viewPager view
     */
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new dummyFragment(), "");
        adapter.addFragment(new dummyFragment(), "");
        adapter.addFragment(new profileFragment(), "");
        viewPager.setAdapter(adapter);
    }


    /**
     * set up the tab icons to the tab layout and inti the custom view to it
     */
    private void setupTabIcons() {
        final View view0 = getLayoutInflater().inflate(R.layout.custom_tab, null);
        ((ImageView) view0.findViewById(R.id.image_tab)).setImageResource(R.drawable.home_selected);
//        ((FrameLayout)view0.findViewById(R.id.tab_back)).setBackgroundColor(getResources()
//                .getColor(R.color.tab_selected_back,null));

        tabLayout.getTabAt(0).setCustomView(view0);
        View view1 = getLayoutInflater().inflate(R.layout.custom_tab, null);

        ((ImageView) view1.findViewById(R.id.image_tab)).setImageResource(tabIcons[1]);
//        ((FrameLayout)view1.findViewById(R.id.tab_back)).setBackgroundColor(getResources()
//                .getColor(R.color.tab_unselected_back,null));

        tabLayout.getTabAt(1).setCustomView(view1);

        View view2 = getLayoutInflater().inflate(R.layout.custom_tab, null);
        ((ImageView) view2.findViewById(R.id.image_tab)).setImageResource(tabIcons[2]);
//        ((FrameLayout)view2.findViewById(R.id.tab_back)).setBackgroundColor(getResources()
//                .getColor(R.color.tab_unselected_back,null));

        tabLayout.getTabAt(2).setCustomView(view2);


        final View[] selectedImageResources = {view0, view1, view2};

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ((ImageView) selectedImageResources[tab.getPosition()].findViewById(R.id.image_tab))
                        .setImageResource(tabIconsSelected[tab.getPosition()]);
//                ((FrameLayout) selectedImageResources[tab.getPosition()]
//                        .findViewById(R.id.tab_back)).setBackgroundColor(getResources()
//                        .getColor(R.color.tab_selected_back,null));

                tab.setCustomView(selectedImageResources[tab.getPosition()]);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                ((ImageView) selectedImageResources[tab.getPosition()].findViewById(R.id.image_tab))
                        .setImageResource(tabIcons[tab.getPosition()]);

//                ((FrameLayout) selectedImageResources[tab.getPosition()]
//                        .findViewById(R.id.tab_back)).setBackgroundColor(getResources()
//                        .getColor(R.color.tab_unselected_back,null));

                tab.setCustomView(selectedImageResources[tab.getPosition()]);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}

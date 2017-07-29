package com.hazem.photpapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.IntentCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hazem.photpapp.R;
import com.hazem.photpapp.activity.SignIn;
import com.hazem.photpapp.view.CirclePageIndicator;

public class profileFragment extends Fragment {


    //intit the views
    public ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    public int NUM_PAGES = 2;
    private TextView fragment_nameTV, nameTv;
    private ImageView profile_image, logoutImage;

    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    //google sign in log out
    //google sign in
    private GoogleApiClient mGoogleApiClient;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        initViews(view);
        getData();
        return view;
    }

    /**
     * here init the view from xml and connect to java
     *
     * @param view the view from xml
     */
    public void initViews(View view) {
        fragment_nameTV = (TextView) view.findViewById(R.id.fragment_nameTV);
        nameTv = (TextView) view.findViewById(R.id.nameTV);
        profile_image = (ImageView) view.findViewById(R.id.profile_image);
        logoutImage = (ImageView) view.findViewById(R.id.logoutImage);


        mPager = (ViewPager) view.findViewById(R.id.freelance_profileVP);
        mPagerAdapter = new ScreenSlidePagerAdapter(getChildFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setOffscreenPageLimit(2);
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    fragment_nameTV.setText("My Wall");
//                bundle.putInt(intentHandle.fragement_number, 1);
                } else {
                    fragment_nameTV.setText("Likes");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        CirclePageIndicator mIndicator = (CirclePageIndicator) view.findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);
    }


    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment;
            Bundle bundle = new Bundle();
            if (position == 0) {
                fragment = new dummyFragment();
//                bundle.putInt(intentHandle.fragement_number, 1);
            } else {
                fragment = new dummyFragment();
            }
            fragment.setArguments(bundle);
            return fragment;

        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }


    //backend

    /**
     * here in the get data method get the data from the server
     */
    private void getData() {

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        Glide.with(getContext()).load(mFirebaseUser.getPhotoUrl()).
                diskCacheStrategy(DiskCacheStrategy.ALL).into(profile_image);
        nameTv.setText(mFirebaseUser.getDisplayName());


        //logout from firebase and facebook and google

        //google sign in logout
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .enableAutoManage(getActivity() /* FragmentActivity */, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
                        // be available.
                        Log.d("google", "onConnectionFailed:" + connectionResult);
                        Toast.makeText(getContext(), "Google Play Services error.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        logoutImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFirebaseAuth.signOut();
                //facebook sign out
                if (LoginManager.getInstance() != null) {
                    LoginManager.getInstance().logOut();
                    Intent i = new Intent(getContext(), SignIn.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                }
                //google log out
                else if (Auth.CREDENTIALS_API != null)
                    Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                            new ResultCallback<Status>() {
                                @Override
                                public void onResult(@NonNull Status status) {
                                    if (status.isSuccess()){
                                        Intent i = new Intent(getContext(), SignIn.class);
                                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(i);
                                    }
                                }
                            });
            }
        });
    }

}

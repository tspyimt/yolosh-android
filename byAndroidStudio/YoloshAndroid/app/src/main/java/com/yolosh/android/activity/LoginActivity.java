package com.yolosh.android.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

//import com.facebook.login.widget.CallbackManager;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.Sharer;
import com.facebook.share.widget.ShareDialog;
import com.gc.materialdesign.views.ButtonRectangle;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.PageIndicator;
import com.yolosh.android.R;
import com.yolosh.android.R.drawable;
import com.yolosh.android.R.id;
import com.yolosh.android.R.layout;
import com.yolosh.android.adapter.WelcomePageAdapter;
import com.yolosh.android.fragment.login.WelcomePageFragment;
import com.yolosh.android.util.MessageKeyValues;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends FragmentActivity {

    private WelcomePageAdapter pageAdapter;
    private PageIndicator mIndicator;
    private ButtonRectangle buttonSigup, buttonLogin;
    // facebook
    private final String PENDING_ACTION_BUNDLE_KEY =
            "com.yolosh.android.activity:PendingAction";
    private LoginButton loginButton;
    private PendingAction pendingAction = PendingAction.NONE;
    private CallbackManager callbackManager;
    private ShareDialog shareDialog;
    private ProfileTracker profileTracker;

    private FacebookCallback<Sharer.Result> shareCallback = new FacebookCallback<Sharer.Result>() {
        @Override
        public void onCancel() {
            Log.d("HelloFacebook", "Canceled");
        }

        @Override
        public void onError(FacebookException error) {
            Log.d("HelloFacebook", String.format("Error: %s", error.toString()));
            String title = getString(R.string.error);
            String alertMessage = error.getMessage();
            showResult(title, alertMessage);
        }

        @Override
        public void onSuccess(Sharer.Result result) {
            Log.d("HelloFacebook", "Success!");
            if (result.getPostId() != null) {
                String title = getString(R.string.success);
                String id = result.getPostId();

                String alertMessage = getString(R.string.successfully_posted_post, id);
                showResult(title, alertMessage);
            }
        }

        private void showResult(String title, String alertMessage) {
            new AlertDialog.Builder(LoginActivity.this)
                    .setTitle(title)
                    .setMessage(alertMessage)
                    .setPositiveButton(R.string.ok, null)
                    .show();
        }
    };

    private enum PendingAction {
        NONE,
        POST_PHOTO,
        POST_STATUS_UPDATE
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // facebook
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        handlePendingAction();
                        Log.d("LOG", "do log in");
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        LoginActivity.this.finish();
                        overridePendingTransition(R.anim.slide_in_right,
                                R.anim.slide_out_left);
                    }

                    @Override
                    public void onCancel() {
                        Log.d("LOG", "onCancel");
                        if (pendingAction != PendingAction.NONE) {
                            showAlert();
                            pendingAction = PendingAction.NONE;
                        }
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Log.d("LOG", "onError" + exception);
                        if (pendingAction != PendingAction.NONE
                                && exception instanceof FacebookAuthorizationException) {
                            showAlert();
                            pendingAction = PendingAction.NONE;
                        }
                    }

                    private void showAlert() {
                        Log.d("LOG", "showAlert");
                        new AlertDialog.Builder(LoginActivity.this)
                                .setTitle(R.string.cancelled)
                                .setMessage(R.string.permission_not_granted)
                                .setPositiveButton(R.string.ok, null)
                                .show();
                    }
                });

        shareDialog = new ShareDialog(this);
        shareDialog.registerCallback(
                callbackManager,
                shareCallback);

        if (savedInstanceState != null) {
            String name = savedInstanceState.getString(PENDING_ACTION_BUNDLE_KEY);
            pendingAction = PendingAction.valueOf(name);
        }
        // init view
        setContentView(layout.activity_login);
        loginButton = (LoginButton) findViewById(id.facebook_login_button);
        loginButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                // It's possible that we were waiting for Profile to be populated in order to
                // post a status update.
                handlePendingAction();
            }
        };

        List<Fragment> fragments = getFragments();
        pageAdapter = new WelcomePageAdapter(getSupportFragmentManager(),
                fragments);
        ViewPager pager = (ViewPager) findViewById(id.id_welcome_viewpager);
        pager.setAdapter(pageAdapter);

        mIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
        mIndicator.setViewPager(pager);

//        buttonSigup = (ButtonRectangle) findViewById(id.id_btn_Sign_up);
        buttonLogin = (ButtonRectangle) findViewById(id.id_btn_Log_in);

//        buttonSigup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("LOG", "do log out");
                LoginManager.getInstance().logOut();
//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent);
//                LoginActivity.this.finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Call the 'activateApp' method to log an app event for use in analytics and advertising
        // reporting.  Do so in the onResume methods of the primary Activities that an app may be
        // launched into.
        AppEventsLogger.activateApp(this);

        if (isLoggedIn()) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            LoginActivity.this.finish();
        }
    }

    public boolean isLoggedIn() {
        boolean enableButtons = AccessToken.getCurrentAccessToken() != null;
        Profile mProfile = Profile.getCurrentProfile();
        Log.d("LOG", enableButtons + "");
        if (enableButtons && mProfile != null) {
            return true;
        } else return false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Call the 'deactivateApp' method to log an app event for use in analytics and advertising
        // reporting.  Do so in the onPause methods of the primary Activities that an app may be
        // launched into.
        AppEventsLogger.deactivateApp(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        profileTracker.stopTracking();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(PENDING_ACTION_BUNDLE_KEY, pendingAction.name());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    // faceboook method
    private void handlePendingAction() {
        PendingAction previouslyPendingAction = pendingAction;
        // These actions may re-set pendingAction if they are still pending, but we assume they
        // will succeed.
        pendingAction = PendingAction.NONE;

        switch (previouslyPendingAction) {
            case NONE:
                break;
//            case POST_PHOTO:
//                postPhoto();
//                break;
//            case POST_STATUS_UPDATE:
//                postStatusUpdate();
//                break;
        }
    }

    // init list pages welcome screen
    private List<Fragment> getFragments() {
        List<Fragment> fList = new ArrayList<Fragment>();

        fList.add(WelcomePageFragment.newInstance(drawable.img_welcome));
        fList.add(WelcomePageFragment.newInstance(drawable.img_2_360));
        fList.add(WelcomePageFragment.newInstance(drawable.img_3_360));
        fList.add(WelcomePageFragment.newInstance(drawable.img_4_360));
        fList.add(WelcomePageFragment.newInstance(drawable.img_5_360));
//        fList.add(LoginFragment.newInstance());
        return fList;
    }

}
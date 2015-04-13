package com.yolosh.android.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.Sharer;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.PageIndicator;
import com.yolosh.android.R;
import com.yolosh.android.R.drawable;
import com.yolosh.android.R.id;
import com.yolosh.android.R.layout;
import com.yolosh.android.adapter.WelcomePageAdapter;
import com.yolosh.android.fragment.login.WelcomePageFragment;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends FragmentActivity
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    // fragment
    private WelcomePageAdapter pageAdapter;
    private PageIndicator mIndicator;

    // Google client to communicate with Google
    private static final int RC_SIGN_IN = 0;
    private boolean mSignInClicked;
    private boolean mIntentInProgress;

    private ConnectionResult mConnectionResult;
    private GoogleApiClient mGoogleApiClient;
    private SignInButton googleSignInButton;

    // facebook field
    private final String PENDING_ACTION_BUNDLE_KEY =
            "com.yolosh.android.activity:PendingAction";
    private LoginButton facebookLoginButton;
    private PendingAction pendingAction = PendingAction.NONE;
    private CallbackManager callbackManager;
    private ShareDialog shareDialog;
    private ProfileTracker profileTracker;

    private FacebookCallback<Sharer.Result> shareCallback = new FacebookCallback<Sharer.Result>() {
        @Override
        public void onCancel() {
//            Log.d("HelloFacebook", "Canceled");
        }

        @Override
        public void onError(FacebookException error) {
//            Log.d("HelloFacebook", String.format("Error: %s", error.toString()));
            String title = getString(R.string.error);
            String alertMessage = error.getMessage();
            showResult(title, alertMessage);
        }

        @Override
        public void onSuccess(Sharer.Result result) {
//            Log.d("HelloFacebook", "Success!");
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
        // init facebook login manager
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {

                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        handlePendingAction();
//                        Log.d("LOG", "do log in");
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        LoginActivity.this.finish();
                        overridePendingTransition(R.anim.slide_in_right,
                                R.anim.slide_out_left);
                    }

                    @Override
                    public void onCancel() {
//                        Log.d("LOG", "onCancel");
                        if (pendingAction != PendingAction.NONE) {
                            showAlert();
                            pendingAction = PendingAction.NONE;
                        }
                    }

                    @Override
                    public void onError(FacebookException exception) {
//                        Log.d("LOG", "onError" + exception);
                        if (pendingAction != PendingAction.NONE
                                && exception instanceof FacebookAuthorizationException) {
                            showAlert();
                            pendingAction = PendingAction.NONE;
                        }
                    }

                    private void showAlert() {
//                        Log.d("LOG", "showAlert");
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
        // init login view
        setContentView(layout.activity_login);
        // init google view
        initGoogleApiClient();

        googleSignInButton = (SignInButton) findViewById(R.id.google_sigin_button);
        googleSignInButton.setSize(SignInButton.SIZE_ICON_ONLY);
        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNetworkAvailable()) {
                    googlePlusLogin();
                }else {
                    Toast.makeText(LoginActivity.this, "No Internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        facebookLoginButton = (LoginButton) findViewById(id.facebook_login_button);
        facebookLoginButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

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

//        if ( Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
//            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//            startActivity(intent);
//            LoginActivity.this.finish();
//            Log.d("LOG", "Pass over login activity");
//        }
    }

    private void initGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API, Plus.PlusOptions.builder().build())
                .addScope(Plus.SCOPE_PLUS_LOGIN).build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Call the 'activateApp' method to log an app event for use in analytics and advertising
        // reporting.  Do so in the onResume methods of the primary Activities that an app may be
        // launched into.
//        AppEventsLogger.activateApp(this);
        if (isFacebookLoggedIn()) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            LoginActivity.this.finish();
//        } else if (mGoogleApiClient.isConnected() && Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
//            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//            startActivity(intent);
//            LoginActivity.this.finish();
        } else {
            if (!isNetworkAvailable()) {
                Toast.makeText(this, "No Internet connection", Toast.LENGTH_SHORT).show();
                LoginManager.getInstance().logOut();
            }
        }
//        mGoogleApiClient.connect();
//        if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
//            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//            startActivity(intent);
//            LoginActivity.this.finish();
//            Log.d("LOG", "Pass over login activity2");
//        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Call the 'deactivateApp' method to log an app event for use in analytics and advertising
        // reporting.  Do so in the onPause methods of the primary Activities that an app may be
        // launched into.
//        AppEventsLogger.deactivateApp(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
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
//        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode != RESULT_OK) {
                mSignInClicked = false;
                if (!mGoogleApiClient.isConnecting()) {
                    mGoogleApiClient.disconnect();
                    initGoogleApiClient();
                    Log.d("LOG", "Cancel do disconnect");
                }
            }
            mIntentInProgress = false;
            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        }
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public boolean isFacebookLoggedIn() {
        boolean enableButtons = AccessToken.getCurrentAccessToken() != null;
        Profile mProfile = Profile.getCurrentProfile();
//        Log.d("LOG", enableButtons + "  isloggin?");
        if (enableButtons && mProfile != null) {
            return true;
        } else return false;
    }

    //
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

    // google:
    @Override
    public void onConnected(Bundle bundle) {
//        signedInUser = false;
        if (mGoogleApiClient.isConnected() && Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            LoginActivity.this.finish();
//            overridePendingTransition(R.anim.slide_in_right,
//                    R.anim.slide_out_left);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }


    private void googlePlusLogin() {
        if (!mGoogleApiClient.isConnecting()) {
            mSignInClicked = true;
            mGoogleApiClient.connect();
        }
    }

    private void resolveSignInError() {
        if (mConnectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
            } catch (IntentSender.SendIntentException e) {
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (!mIntentInProgress) {
            // Store the ConnectionResult so that we can use it later when the user clicks
            // 'sign-in'.
            mConnectionResult = connectionResult;//
            if (mSignInClicked) {
                // The user has already clicked 'sign-in' so we attempt to resolve all
                // errors until the user is signed in, or they cancel.
                resolveSignInError();
            }
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

    // check network is available
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return (activeNetworkInfo != null && activeNetworkInfo.isConnected());
    }
}
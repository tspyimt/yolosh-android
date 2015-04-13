package com.yolosh.android.fragment.drawerfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.squareup.picasso.Picasso;
import com.yolosh.android.R;
import com.yolosh.android.activity.LoginActivity;

/**
 * Created by LostSoul on 4/13/2015.
 */
public class BaseNavigatorDrawerFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    protected Button buttonHome, buttonLogOut;
    protected GoogleApiClient mGoogleApiClient;
    protected ImageView imageView;
    protected TextView textViewName;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API, Plus.PlusOptions.builder().build())
                .addScope(Plus.SCOPE_PLUS_LOGIN).build();

        imageView = (ImageView) view.findViewById(R.id.id_img_user);
        textViewName = (TextView) view.findViewById(R.id.id_name_user);

        buttonHome = (Button) view.findViewById(R.id.id_btn_home);
        buttonLogOut = (Button) view.findViewById(R.id.id_btn_log_out);

        buttonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogOut();
            }
        });
    }

    public void initUser() {
        try {
            if (mGoogleApiClient.isConnected() && (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null)) {
                Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);

                textViewName.setText(currentPerson.getDisplayName().toString());
                Picasso.with(getActivity())
                        .load(currentPerson.getImage().getUrl().toString())
                        .error(R.drawable.ic_plusone_small_off_client)
                        .placeholder(R.drawable.ic_plusone_small_off_client)
                        .into(imageView);
            } else {
                boolean enableButtons = AccessToken.getCurrentAccessToken() != null;
                Profile mProfile = Profile.getCurrentProfile();
                if (enableButtons && mProfile != null) {
                    textViewName.setText(mProfile.getName());
                    Picasso.with(getActivity())
                            .load(mProfile.getProfilePictureUri(70, 70))
                            .error(R.drawable.icon).placeholder(R.drawable.icon)
                            .into(imageView);
                }
            }
        } catch (Exception e) {
            Log.d("LOG", "ERROR");
            e.printStackTrace();
        }
    }

    private void doLogOut() {
        if (mGoogleApiClient.isConnected()) {
            Log.d("LOG", "google log out");
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            mGoogleApiClient.disconnect();
            mGoogleApiClient.connect();

            Intent intent = new Intent(getActivity(), LoginActivity.class);
            ActivityCompat.finishAffinity(getActivity());
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.slide_in_left,
                    R.anim.slide_out_right);
        } else {
            Log.d("LOG", "facebook log out");
            LoginManager.getInstance().logOut();

            Intent intent = new Intent(getActivity(), LoginActivity.class);
            ActivityCompat.finishAffinity(getActivity());
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.slide_in_left,
                    R.anim.slide_out_right);
        }
    }

    @Override
    public void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        initUser();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}

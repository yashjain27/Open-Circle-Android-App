package tech.ceece.opencircle;

import android.*;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener{
    //Data fields
    private Account account;
    private DatabaseReference mDatabase;
    private FirebaseUser user;
    private Intent loginIntent;
    private Toolbar toolbar;
    private GoogleMap mMap;
    private MapView mapView;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private CameraPosition mCameraPosition;
    private LocationManager locationManager;
    private Marker myMark;
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity);
        loginIntent = new Intent(this, Login.class);

        //Toolbar
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        user = FirebaseAuth.getInstance().getCurrentUser();

        //Map
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        //Google API client
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addApi(AppIndex.API).build();
        mGoogleApiClient.connect();

        //LocationManager
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (savedInstanceState != null) {
            mLastLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            mCameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        } else {
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            mLastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }

        //DataBase
        mDatabase = FirebaseDatabase.getInstance().getReference().child(user.getUid());
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    account = dataSnapshot.getValue(Account.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
        if (mMap != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, mMap.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, mLastLocation);
            super.onSaveInstanceState(outState);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public  boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.delete:
                reauthenticationInput();
                return true;
            case R.id.logout:
                logout();
                return true;
            case R.id.information:
                info();
                return true;
        }
        return false;
    }

    /**
     * Pauses activity when back is pressed
     */
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Padding
        mMap.setPadding(0, 150, 0, 0); //left, top, right, bottom
        mMap.getUiSettings().setMapToolbarEnabled(true);

        //Marker Listener
        mMap.setOnMarkerClickListener(
                new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        info();
                        return false;
                    }
                }
        );

        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();

        //Position the camera
        if (mCameraPosition != null) {
            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(mCameraPosition));
        }else if(mLastLocation != null)
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()), 16));

    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Connection Failed! ", Toast.LENGTH_SHORT).show();
    }

    //Methods

    /**
     * Input request asking to re-enter email and password
     */
    public void reauthenticationInput(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.reautheticate_login);
        dialog.show();

        final EditText email = (EditText) dialog.findViewById(R.id.editText12);
        final EditText password = (EditText) dialog.findViewById(R.id.editText13);

        Button b1 = (Button) dialog.findViewById(R.id.button8);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthCredential credential = EmailAuthProvider.getCredential(email.getText().toString(), password.getText().toString());
                reauthentication(credential);
                dialog.dismiss();
            }
        });

        Button b2 = (Button)dialog.findViewById(R.id.button9);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    /**
     * Re-authenticates the User
     * @param credentials
     *      a AuthCredential object that contains the credentials of the User
     */
    public void reauthentication(AuthCredential credentials){
        //Check for validity of the credentials
        user.reauthenticate(credentials)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            AlertDialog.Builder alertB = new AlertDialog.Builder(UserActivity.this);
                            alertB.setMessage("Are you sure you want to delete your account?")
                                    .setCancelable(false)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            delete();
                                        }
                                    })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.cancel();
                                        }
                                    });
                            AlertDialog alert = alertB.create();
                            alert.setTitle("WARNING!");
                            alert.show();
                        }else {
                            Toast.makeText(UserActivity.this, "Invalid email/password. Please try again", Toast.LENGTH_SHORT).show();
                            reauthenticationInput();
                        }
                    }
                });
    }


    /**
     * Deletes the user
     */
    public void delete(){
        user.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            mDatabase.setValue(null);
                            Toast.makeText(UserActivity.this, "Account deleted", Toast.LENGTH_SHORT).show();
                            FirebaseAuth.getInstance().signOut();
                            finish();
                            startActivity(loginIntent);
                        }else
                            Toast.makeText(UserActivity.this, "Failed to delete", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * Logs the user out
     */
    public void logout() {
        Toast.makeText(this, "Successfully logged out!", Toast.LENGTH_SHORT).show();
        FirebaseAuth.getInstance().signOut();
        finish();
        startActivity(loginIntent);
    }

    /**
     * Displays info about the user
     */
    public void info(){
        String accountInfo = account.getEmail() + "\n";
        accountInfo += account.getUserName() + "\n";
        accountInfo += account.getFullName() + "\n";
        accountInfo += account.getPhoneNumber() + "\n";
        accountInfo += account.getAdmin() + "\n";
        accountInfo += account.getBanned() + "\n";
        Toast.makeText(this, "" + accountInfo, Toast.LENGTH_SHORT).show();
    }

    /**
     * Updates the map's UI settings based on whether the user has granted location permission.
     */
    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }
    }

    public void onUserOptions(View v){
        startActivity(new Intent(this, SellingItem.class));
        //Check if marker already exists
        if(myMark != null)
            return;

        if(mLastLocation == null)
            return;

        // Add a marker in NYC and move the camera
        myMark = mMap.addMarker(new MarkerOptions().position(new LatLng(mLastLocation.getLatitude(),
                mLastLocation.getLongitude())).title(account.getUserName())
                .draggable(true)); //.setTag(account);
    }
}
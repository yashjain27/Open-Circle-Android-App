package tech.ceece.opencircle;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserActivity extends AppCompatActivity implements OnMapReadyCallback {
    //Data fields
    private Account account;
    private DatabaseReference mDatabase;
    private FirebaseUser user;
    private Intent loginIntent;
    private Toolbar toolbar;
    private GoogleMap mMap;
    private MapView mapView;

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

        //DataBase
        mDatabase = FirebaseDatabase.getInstance().getReference().child(user.getUid());
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                account = dataSnapshot.getValue(Account.class);
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
                delete();
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

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(40, -74);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in NYC"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    //Methods

    /**
     * Deletes the user
     */
    public void delete(){
        mDatabase.setValue(null);
        user.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(UserActivity.this, "Account deleted", Toast.LENGTH_SHORT).show();
                            FirebaseAuth.getInstance().signOut();
                            finish();
                            startActivity(loginIntent);
                        }else
                            Toast.makeText(UserActivity.this, "fail", Toast.LENGTH_SHORT).show();
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
}
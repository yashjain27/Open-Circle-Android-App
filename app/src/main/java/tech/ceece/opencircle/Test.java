package tech.ceece.opencircle;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Test extends AppCompatActivity {
    //Data fields
    private Account account;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        final TextView textView = new TextView(this);

        mDatabase = FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Toast.makeText(Test.this, "DataSnapshot", Toast.LENGTH_SHORT).show();
                account = dataSnapshot.getValue(Account.class);
                String accountInfo = account.getEmail() + "\n";
                accountInfo += account.getUserName() + "\n";
                accountInfo += account.getFullName() + "\n";
                accountInfo += account.getPhoneNumber() + "\n";
                accountInfo += account.getAdmin() + "\n";
                accountInfo += account.getBanned() + "\n";
                accountInfo += account.getPassword().getPassword() + "\n";

                textView.setText(accountInfo);
                ViewGroup viewGroup = (ViewGroup) findViewById(R.id.activity_test);
                viewGroup.removeView(textView);
                viewGroup.addView(textView);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Test.this, "Database Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void onLogout(View v) {
        FirebaseAuth.getInstance().signOut();
        finish();
        startActivity(new Intent(this, Login.class));
    }

    /**
     * Pauses activity when back is pressed
     */
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }


}
package tech.ceece.opencircle;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class Login extends AppCompatActivity {
    //Data fields
    private FirebaseAuth mAuth;
    private Intent intent;
    private EditText editText;
    private EditText editText2;
    private CheckBox checkBox;
    private GoogleApiClient mGoogleApiClient;
    SharedPreferences.Editor myPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        editText = (EditText) findViewById(R.id.editText7);
        editText2 = (EditText) findViewById(R.id.editText8);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        myPrefs = getSharedPreferences("MyPrefsFile", 0).edit();

        //See if user chose to let the app remember his/her login account
        SharedPreferences prefs = getSharedPreferences("MyPrefsFile", 0);
        Boolean saved = prefs.getBoolean("saved", false);
        if(saved == true){
            editText.setText(prefs.getString("username", "No name defined"));
            editText2.setText(prefs.getString("password", "no password"));
            checkBox.setChecked(true);
        }

        //Google Sign in button
        SignInButton signInButton = (SignInButton) findViewById(R.id.google_signin);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onGoogleSignIn();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null)
            startActivity(new Intent(this, UserActivity.class));
    }

    //Sign Up button
    public void onSignUp(View v){
        intent = new Intent(this, SignUp.class);
        startActivity(new Intent(this, SignUp.class));
    }

    //Login button
    public void onLogin(View v){
        intent = new Intent(this, UserActivity.class);

        if(checkBox.isChecked()){
            myPrefs.putString("username", editText.getText().toString());
            myPrefs.putString("password", editText2.getText().toString());
            myPrefs.putBoolean("saved", true);
            myPrefs.apply();
        }else {
            myPrefs.putBoolean("saved", false);
            myPrefs.apply();
        }

        if(!editText.getText().toString().isEmpty() && !editText2.getText().toString().isEmpty()) {
            login(editText.getText().toString(), editText2.getText().toString());
        }else{
            Toast.makeText(this, "Please enter email/password", Toast.LENGTH_SHORT).show();
        }
    }

    public void login(String email, String password){
        mAuth.signInWithEmailAndPassword(editText.getText().toString(), editText2.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(Login.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    private void onGoogleSignIn() {
        Toast.makeText(this, "ghjgjg", Toast.LENGTH_SHORT).show();
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, 1);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == 1) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        intent = new Intent(this, UserActivity.class);
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Login.this, "Incorrect login information",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}

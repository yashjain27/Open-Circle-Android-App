package tech.ceece.opencircle;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.InputMismatchException;

public class SignUp extends AppCompatActivity {
    //Data field
    private EditText editText;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_signup);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void onSignUp(View v){
        editText = (EditText) findViewById(R.id.editText);
        final String userName = editText.getText().toString();
        editText = (EditText) findViewById(R.id.editText2);
        final String firstName = editText.getText().toString();
        editText =  (EditText) findViewById(R.id.editText3);
        final String lastName = editText.getText().toString();
        editText = (EditText) findViewById(R.id.editText4);
        final String email = editText.getText().toString();
        editText = (EditText) findViewById(R.id.editText5);
        final String phoneNumber = editText.getText().toString();
        editText = (EditText) findViewById(R.id.editText6);
        String password = editText.getText().toString();
        editText = (EditText) findViewById(R.id.editText9);
        String rePassword = editText.getText().toString();

        try {
            final Password pass = new Password(password);
            if(!password.equals(rePassword))
                throw new InputMismatchException();
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                addToDataBase(userName, firstName + " " + lastName, email, phoneNumber, pass);
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(SignUp.this, "Invalid email. Please use a valid email.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } catch (IllegalPasswordException e) {
            Toast.makeText(this, "Password must be 6 characters long, one lower case, one upper case, one digit, and one\n" +
                    "     * special character (~!@#$%^&*()_+)" + e.toString(), Toast.LENGTH_SHORT).show();
        } catch (InputMismatchException e) {
            Toast.makeText(this, "Passwords don't match! Try again!", Toast.LENGTH_SHORT).show();
        }
    }

    public void addToDataBase(String userName, String fullName, String email, String phoneNumber, Password pass){
        try {
            //Create account and try adding to the DataBase
            Account account = new Account(userName, fullName, email, phoneNumber, pass);
            Toast.makeText(this, "Congratulations! You have successfully signed up!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, UserActivity.class);
            intent.addFlags(IntentCompat.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("account", account);
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            mDatabase.child(user.getUid()).setValue(account);


            startActivity(intent);
            finish();
        } catch (InputMismatchException e) {
            Toast.makeText(this, "Passwords don't match! Try again!", Toast.LENGTH_SHORT).show();
        }
    }
}
